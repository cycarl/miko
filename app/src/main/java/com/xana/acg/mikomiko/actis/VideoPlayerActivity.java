package com.xana.acg.mikomiko.actis;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.xana.acg.com.app.Activity;
import com.xana.acg.mikomiko.R;

import java.io.IOException;

import butterknife.BindView;


public class VideoPlayerActivity extends Activity
        implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, SurfaceHolder.Callback {
    @BindView(R.id.sv)
    SurfaceView mSv;

    private SurfaceHolder sh;
    private MediaPlayer mediaPlayer;
    private int width;
    private int height;


    @Override
    protected int getLayoutId() {
        return R.layout.page;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        this.sh = mSv.getHolder();
        sh.addCallback(this);
        sh.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    private void play() throws IOException {
        mediaPlayer = new MediaPlayer();
        String src = "http://60.205.204.182:6001/anime/6b313038307c3539393531/0/0/url";
        String src1 = "https://1251316161.vod2.myqcloud.com/5f6ddb64vodsh1251316161/5e8fb66d5285890814932760236/hFaTX5rTVmEA.mp4";
        mediaPlayer.setDataSource(src);
        mediaPlayer.setDisplay(sh);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.prepareAsync();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        this.width = this.mediaPlayer.getVideoWidth();
        this.height = this.mediaPlayer.getVideoHeight();

        Log.e("text", "mediaPlayer");

        this.sh.setFixedSize(this.width, this.height);
        this.mediaPlayer.start();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        try {
            this.play();
        } catch (Exception e) {
            Log.e("mplayer", ">>>error");
        }
        Log.e("mplayer", ">>>surface created");
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}