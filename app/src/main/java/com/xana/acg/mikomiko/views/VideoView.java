package com.xana.acg.mikomiko.views;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.xana.acg.com.R;
import com.xana.acg.com.utils.TextUtils;
import com.xana.acg.com.widget.IVideoView;
import com.xana.acg.com.widget.Rounder;

import java.io.IOException;
import static android.media.MediaPlayer.MEDIA_INFO_BUFFERING_END;
import static android.media.MediaPlayer.MEDIA_INFO_BUFFERING_START;

public class VideoView extends IVideoView
        implements SurfaceHolder.Callback,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnInfoListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {

    private MediaPlayer mp;
    protected SurfaceView surf;
    protected SurfaceHolder sh;

    public VideoView(@NonNull Context context) {
        super(context);
    }
    public VideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public VideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public VideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int getLayout() {
        return R.layout.self_video_view;
    }

    @Override
    public void init() {
        super.init();
        surf = r.findViewById(R.id.surfv);
        sh = getHolder();
        initData();
    }

    public void initData() {
        super.initData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            surf.setOutlineProvider(new Rounder(20f));
            surf.setClipToOutline(true);
        }
        sh.addCallback(this);
        sh.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void setPlayer(MediaPlayer m){
        mp = m;
        mp.reset();
        mp.setOnPreparedListener(this);
        mp.setOnBufferingUpdateListener(this);
        mp.setOnErrorListener(this);
        mp.setOnInfoListener(this);
    }

    @Override
    public boolean isPlaying(){
        return mp.isPlaying();
    }

    @Override
    public void pause() {
        mp.pause();
    }

    @Override
    public void start(boolean rs) {
        super.start(rs);
        mp.start();
    }
    public SurfaceHolder getHolder() {
        return surf.getHolder();
    }

    public void play(String uri) {

        try {
            mp.setDataSource(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.setDisplay(sh);
        mp.setOnPreparedListener(this);
        mp.setOnCompletionListener(this);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.prepareAsync();
        mp.setOnPreparedListener(this);
        setVisibility(VISIBLE);
    }

    @Override
    public void seekTo(long msec) {
        mp.seekTo((int) msec);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        mp.setDisplay(holder);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    }
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        mp.reset();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        seekBar.setMax((int) (duration()/1000));
        dura.setText(String.format("/%s", TextUtils.time(duration())));
        ok(true);
        start(true);
    }

    @Override
    public long current(){
        return mp.getCurrentPosition();
    }

    @Override
    public long duration() {
        return mp.getDuration();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int p) {
        percent.setText("缓冲中:"+p+"% ");
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what){
            case MEDIA_INFO_BUFFERING_START:
                ok(false);
                start(false);
                break;

            case MEDIA_INFO_BUFFERING_END:
                ok(true);
                pause();
                break;

            case 703:
                speed.setText(extra + "kb/s");
                break;
        }
        return true;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return true;
    }


    public void seekTo(int msec){
        mp.seekTo(msec);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        play.setActivated(true);
        pause.setVisibility(VISIBLE);
    }
    public void release(){
        if(work!=null) work.interrupt();
        work=null;
        handler.removeMessages(0);
        setVisibility(GONE);
    }
}
