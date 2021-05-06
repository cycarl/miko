package com.xana.acg.com.widget;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.xana.acg.com.R;
import com.xana.acg.com.app.Activity;
import com.xana.acg.com.app.Application;
import com.xana.acg.com.utils.TextUtils;

import net.qiujuer.genius.ui.widget.SeekBar;

import butterknife.ButterKnife;

import static android.view.WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL;

public abstract class IVideoView extends FrameLayout
        implements SeekBar.OnSeekBarChangeListener, View.OnClickListener, View.OnTouchListener {

    protected Context ctx;
    protected Listener mLis;
    protected boolean live;
    protected AudioManager audioManager;
    protected Window window;

    public void setListener(Listener lis) {
        mLis = lis;
    }

    public IVideoView(@NonNull Context context) {
        this(context, null);
    }

    public IVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public IVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    protected LinearLayout panel;
    protected SeekBar seekBar;
    protected ImageView play;
    protected ImageView next;
    protected TextView cur;
    protected TextView dura;
    protected ImageView full;
    protected LinearLayout loading;
    protected TextView percent;
    protected TextView speed;
    protected ImageView pause;

    protected View r;

    public void init() {
        ctx = getContext();
        audioManager = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        window = ((Activity) ctx).getWindow();
        View v = LayoutInflater.from(ctx).inflate(getLayout(), this);
        r = v;
        panel = v.findViewById(R.id.panel);
        seekBar = v.findViewById(R.id.sb_bar);
        play = v.findViewById(R.id.iv_play);
        pause = v.findViewById(R.id.iv_pause);
        next = v.findViewById(R.id.next);
        cur = v.findViewById(R.id.tv_cur_time);
        dura = v.findViewById(R.id.tv_duration);
        full = v.findViewById(R.id.iv_full_screen);
        loading = v.findViewById(R.id.loading);
        percent = v.findViewById(R.id.buffer_percent);
        speed = v.findViewById(R.id.net_speed);
    }

    public void initData() {
        setOnClickListener(this);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        full.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    public void setLive(boolean l) {
        live = l;
    }


    protected abstract int getLayout();

    public void play(String uri) {
        ok(false);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        slipeSeekbar = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (live) return;
        slipeSeekbar = false;
        seekTo(seekBar.getProgress() * 1000);
    }

    public static int i = 0;

    public abstract void seekTo(long msec);

    public abstract boolean isPlaying();

    public abstract void pause();

    private boolean slipeSeekbar;

    /**
     * rs true restart, false resume
     *
     * @param rs
     */
    public void start(boolean rs) {
        pause.setVisibility(GONE);
        if (!rs || live) return;
        if (work != null)
            work.interrupt();
        work = new Work("" + (i++));
        work.start();
    }


    public abstract long current();

    public abstract long duration();

    @Override
    public void onClick(View v) {
        if (v == play || v == pause) {
            boolean is = isPlaying();
            play.setActivated(is);
            pause.setVisibility(is ? VISIBLE : GONE);
            if (is) {
                pause();
            } else {
                start(false);
            }
        } else if (v == this) {
            boolean f = panel.getVisibility() == GONE;
            panel.setVisibility(f ? VISIBLE : GONE);
        }
        if (mLis == null) return;
        if (v == full) {
            switchScreen();
        } else if (v == next) {
            mLis.next();
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }


    private boolean initf = true;
    // 0: 调节亮度，1: 调节音量，2:调节进度；
    private int mode = 0;
    private float startY;
    private float startX;
    /**
     * 触摸屏幕操作 TODO
     * 调节音量，亮度， 快进快退
     * @param
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                if (startX > getMeasuredWidth() / 2) {
                    mode = 1;
                    curVolum = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    maxVolum = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                }
                else mode = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                if (initf) {
                    float tan = (startY - event.getY()) / (startX - event.getX());
                    if (Math.abs(tan) < 1)
                        mode = 2;
                    initf = false;
                }
                setTouch(event);
                break;
            case MotionEvent.ACTION_UP:
                initf = true;
                if(mode==2&&!live){
                    seekTo(current()+(int)(event.getX()-startX)*14);
                }
        }
        return super.onTouchEvent(event);
    }

    private int curVolum;
    private int maxVolum;
    private int curLight;
    private int maxLight = 255;
    private void setTouch(MotionEvent e) {
        int ss = 0;
        if (mode == 0) {
            Log.e("调节亮度", "ssss");
            setLight(startY-e.getY()>0);

        } else if (mode == 1) {
            Log.e("调节音量", "ssss");
            ss = (int) (startY-e.getY())/7+curVolum;
            if(ss<1||ss>maxVolum) return;
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, ss, 0);
        } else {
            Log.e("调节进度", "ssss");

        }
    }

    private void setLight(boolean f){
        curLight = f? curLight+1:curLight-1;
        if(curLight<0||curLight>maxLight)
            return;
        float light = (float)curLight/maxLight;
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = light;
        window.setAttributes(lp);
    }


    public void ok(boolean f) {
        int fl = f ? GONE : VISIBLE;
        loading.setVisibility(fl);
        play.setActivated(!f);
    }

    protected Handler handler = new WorkHandler();
    protected Thread work;

    public class Work extends Thread {
        public Work(String threadId) {
            super(threadId);
        }

        @Override
        public void run() {
            while (current() <= duration()) {
                if (slipeSeekbar) continue;
                Message msg = handler.obtainMessage(0, (int) (current() / 1000), 0, TextUtils.time(current()));
                handler.sendMessage(msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    public void switchScreen() {
        save();
        boolean f = full.isActivated();
        full.setActivated(!f);
        if (mLis != null) {
            if (f) mLis.norScreen();
            else mLis.fullScreen();
        }
        if (live) return;
        resume();
    }

    public class WorkHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                // 更新进度条:
                case 0:
                    cur.setText((String) msg.obj);
                    seekBar.setProgress(msg.arg1);
                    if (msg.arg1 % 7 == 0) panel.setVisibility(GONE);
                    break;
            }
        }
    }

    public void resume() {
//        SharedPreferences sp = ctx.getSharedPreferences("anime", MODE_PRIVATE);
//        long cur = sp.getLong("curTime", 0);
        seekTo(curTime);
    }

    public long curTime = 0;

    public void save() {
        curTime = current();
//        SharedPreferences sp = ctx.getSharedPreferences("anime", MODE_PRIVATE);
//        SharedPreferences.Editor ed = sp.edit();
//        ed.putLong("curTime", current());
//        ed.apply();
    }

    public interface Listener {
        void fullScreen();

        void norScreen();

        void complete();

        void next();
    }
}
