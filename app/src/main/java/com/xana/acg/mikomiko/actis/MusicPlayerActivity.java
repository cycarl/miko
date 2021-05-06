package com.xana.acg.mikomiko.actis;

import android.animation.ObjectAnimator;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xana.acg.Factory;
import com.xana.acg.mikomiko.App;
import com.xana.acg.com.app.PresenterActivity;
import com.xana.acg.com.widget.PortraitView;
import com.xana.acg.fac.model.music.Music;
import com.xana.acg.fac.model.music.MusicUri;
import com.xana.acg.fac.presenter.play.MusicPlayerContract;
import com.xana.acg.fac.presenter.play.MusicPlayerPresenter;
import com.xana.acg.com.utils.TextUtils;
import com.xana.acg.mikomiko.R;

import net.qiujuer.genius.ui.widget.SeekBar;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

import static com.xana.acg.com.Common.URI.MUSIC;

public class MusicPlayerActivity extends PresenterActivity<MusicPlayerContract.Presenter>
        implements MusicPlayerContract.View {

    private MediaPlayer mp;

    @BindView(R.id.sb_bar)
    SeekBar mBar;

    @BindView(R.id.iv_cur_time)
    TextView mCurTime;

    @BindView(R.id.iv_duration)
    TextView mDuration;

    @BindView(R.id.pv_disk)
    PortraitView mDisk;

    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.tv_tip)
    TextView mTip;

    @BindView(R.id.tv_creater)
    TextView mCreator;

    private boolean isPlaying = true;
    private boolean isFavor = false;

    private Music music;
    private String uri;


    private Thread task;

    private float maxTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_music_player;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        String json = bundle.getString("music");
        if (json == null || json.length() == 0)
            return false;
        music = Factory.getGson().fromJson(json, Music.class);
        return music != null;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mp = App.getMediaPlayer();
        mp.reset();
    }

    @Override
    protected void initData() {
        super.initData();
        uri = String.format(MUSIC, music.getId());
        mDisk.setSrc(music.getAl().getPicUrl());

        mTitle.setText(music.getName());
        mTip.setText(music.getAlia().size() > 0 ? music.getAlia().get(0) : "");
        StringBuilder creaters = new StringBuilder();
        for (Music.Ar ar : music.getAr()) {
            creaters.append(ar.getName()).append(" ");
        }
        mCreator.setText(creaters.toString());

        if (!setPlayer()) return;
        mBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress()*1000);
            }
        });
    }

    private class TaskHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mCurTime.setText(TextUtils.time(msg.arg1));
                    mBar.setProgress(msg.arg1/1000);
                    break;
            }
        }
    }
    private TaskHandler handler = new TaskHandler();

    private class TaskTime extends Thread {
        @Override
        public void run() {
            while (mp !=null&&(mp.getCurrentPosition()) < mp.getDuration()) {
                try {
                    if (!mp.isPlaying()) continue;
                    Message msg = handler.obtainMessage(0, mp.getCurrentPosition(), 0);
                    handler.sendMessage(msg);
                    Thread.sleep(1000);
                } catch (InterruptedException|NullPointerException e) {
                    e.printStackTrace();
                    return;
                }
            }
            if (anime != null)
                anime.pause();
        }
    }


    private ObjectAnimator anime;

    private boolean setPlayer() {
        try {
            mp.setDataSource(uri);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.prepareAsync();
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    disable = false;
                    prepare();
                    MusicPlayerActivity.this.mp.start();
                }
            });
            return true;

        } catch (IOException e) {
            showToast("异常");
            e.printStackTrace();
        }
        return false;
    }

    private void prepare() {
        maxTime = mp.getDuration() / 1000f;
        mBar.setMax((int) maxTime);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDuration.setText(TextUtils.time((long) (maxTime*1000)));
            }
        });

        anime = ObjectAnimator
                .ofFloat(mDisk, "rotation", 0.0f, 360.0f)
                .setDuration(12000);
        anime.setInterpolator(new LinearInterpolator());
        anime.setRepeatCount(-1);
//                    anime.setRepeatMode(ObjectAnimator.RESTART);
        anime.start();
        (task = new TaskTime()).start();
    }

    private boolean disable = true;

    @OnClick({R.id.iv_favor, R.id.iv_dload, R.id.iv_extra, R.id.iv_comment,
            R.id.iv_detail, R.id.iv_mode, R.id.iv_front, R.id.iv_play,
            R.id.iv_next, R.id.iv_list})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.iv_favor:
                toggle(v);
                break;
            case R.id.iv_dload:
                break;

            case R.id.iv_extra:
                break;
            case R.id.iv_comment:
                break;
            case R.id.iv_detail:
                break;
            case R.id.iv_mode:
                break;
            case R.id.iv_front:
                break;
            case R.id.iv_play:
                toggle(v);
                break;
            case R.id.iv_next:
                break;

            case R.id.iv_list:
                break;
        }
    }

    private void toggle(View view) {
        if (disable) return;
        ImageView v = (ImageView) view;
        switch (v.getId()) {
            case R.id.iv_play:
                v.setActivated(isPlaying);
                if (isPlaying) {
                    mp.pause();
                    anime.pause();
                } else {
                    mp.start();
                    anime.resume();
                }
                isPlaying = !isPlaying;
                break;

            case R.id.iv_favor:
                v.setImageResource(isFavor ? R.drawable.ic_favor_s : R.drawable.ic_favor);
                isFavor = !isFavor;
                break;
        }
    }

    @Override
    protected MusicPlayerContract.Presenter initPresenter() {
        return new MusicPlayerPresenter(this);
    }

    @Override
    public void onLoad(MusicUri musicUri) {
        // TODO
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (anime != null)
            anime.end();
        if(task!=null) task.interrupt();
        anime = null;
    }
}