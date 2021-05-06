package com.xana.acg.mikomiko.views;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.xana.acg.com.utils.TextUtils;
import com.xana.acg.mikomiko.R;
import com.xana.acg.com.widget.IVideoView;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

import static com.xana.acg.com.app.Application.showToast;

public class AnimeView extends IVideoView
    implements MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnInfoListener,
        MediaPlayer.OnPreparedListener{
    public AnimeView(@NonNull Context context) {
        super(context);
    }

    public AnimeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AnimeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    VideoView vv;

    private boolean initf;
    @Override
    public void init() {
        super.init();
        vv = r.findViewById(R.id.videoView);
        initf = Vitamio.isInitialized(ctx);
    }

    @Override
    public void initData() {
        super.initData();
        if(initf){
            vv.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
//            vv.setBufferSize(1024*1024); //设置视频缓冲大小。默认1024KB，单位byte
//            vv.setHardwareDecoder(true);
            vv.requestFocus();
            vv.setOnErrorListener(this);
            vv.setOnBufferingUpdateListener(this);
            vv.setOnInfoListener(this);
            vv.setOnCompletionListener(this);
            vv.setOnPreparedListener(this);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.self_anime_view;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        ok(true);
        showToast("链接失效了~");
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mLis.next();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int p) {
        percent.setText("缓冲中:"+p+"% ");
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            //开始缓冲
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                pause();
                ok(false);
                break;
            //缓冲结束
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                start(false);
                ok(true);
                break;
            //正在缓冲
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                speed.setText(extra + "kb/s");
                break;
        }
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if(!live){
            seekBar.setMax((int) (duration()/1000));
            dura.setText(String.format("/%s", TextUtils.time(duration())));
        }else{
            dura.setText(String.format("/%s", "00:00"));
        }

        start(true);
    }
    @Override
    public void play(String uri) {
        //        uri = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8";
        super.play(uri);
        vv.setVideoURI(Uri.parse(uri));
        // 显示
    }

    @Override
    public void seekTo(long msec) {
        vv.seekTo(msec);
    }

    @Override
    public boolean isPlaying() {
        return vv.isPlaying();
    }
    @Override
    public void pause() {
        vv.pause();
    }

    @Override
    public void start(boolean rs) {
        super.start(rs);
        vv.start();
    }

    @Override
    public long current() {
        return vv.getCurrentPosition();
    }

    @Override
    public long duration() {
        return vv.getDuration();
    }

}
