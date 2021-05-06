package com.xana.acg.mikomiko.actis;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xana.acg.mikomiko.App;
import com.xana.acg.com.app.TabViewPagerActivity;
import com.xana.acg.com.utils.TextUtils;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.frags.SrcFragment;
import com.xana.acg.mikomiko.frags.MyFragment;

import net.qiujuer.genius.ui.widget.Loading;
import net.qiujuer.genius.ui.widget.SeekBar;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;


public class AnimePlayerActivity0 extends TabViewPagerActivity
        implements SeekBar.OnSeekBarChangeListener{

    @BindView(R.id.vitamio)
    VideoView videoView;

    @BindView(R.id.buffer_percent)
    TextView percentTv;

    @BindView(R.id.net_speed)
    TextView netSpeedTv;

    @BindView(R.id.iv_play)
    ImageView mPlay;

    @BindView(R.id.sb_bar)
    SeekBar mSeekbar;
    @BindView(R.id.tv_cur_time)

    TextView mCurtime;
    @BindView(R.id.tv_duration)
    TextView mDuration;

    @BindView(R.id.full)
    FrameLayout mFull;

    @BindView(R.id.player)
    FrameLayout mPlayer;

    @BindView(R.id.display)
    FrameLayout mDisplay;

    @BindView(R.id.iv_full_screen)
    ImageView mFullscreen;


    @BindView(R.id.panel)
    LinearLayout mPanel;

    @BindView(R.id.loading)
    Loading mLoading;

    private OnFragListener mListener;
    private Thread task;

    @Override
    protected void setFrags() {
        mFrags = Arrays.asList(
                new SrcFragment(),
                new MyFragment()
        );
    }

    @Override
    protected void setTitles() {
        mTitles = Arrays.asList("简介", "评论");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_anime_player;
    }

    private String uri;

    @Override
    protected boolean initArgs(Bundle bundle) {
        App.getMediaPlayer().pause();

        uri = bundle.getString("uri");
        return uri != null;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mSeekbar.setOnSeekBarChangeListener(this);
        setPlayer();
    }


    String url = "https://1251316161.vod2.myqcloud.com/29fe1275vodbj1251316161/5c1534725285890816229305794/EsvrHBrCk7kA.mp4";
    String url1 = "https://1251316161.vod2.myqcloud.com/007a649dvodcq1251316161/91a28fd65285890814081538994/N0P7oujHVwYA.mp4";
    String url2 = "http://60.205.204.182:6001/anime/6b313038307c3537393633/0/0/url";
    String url3 = "http://60.205.204.182:6001/proxy/anime/6b313038307c3537393633/0/0";

    private void setPlayer() {
        //显示缓冲百分比的TextView
        //显示下载网速的TextView
        //初始化加载库文件
        if (Vitamio.isInitialized(this)) {
            videoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
//            videoView.setBufferSize(1024); //设置视频缓冲大小。默认1024KB，单位byte
            videoView.setHardwareDecoder(true);
            videoView.requestFocus();
            videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mLoading.setVisibility(View.GONE);
                    showToast("视频链接失效了~");
                    return true;
                }
            });
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mListener.next();
                }
            });

            videoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    percentTv.setText("缓冲中:" + percent + "% ");
                }
            });
            videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    switch (what) {
                        //开始缓冲
                        case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                            mLoading.setVisibility(View.VISIBLE);
                            percentTv.setVisibility(View.VISIBLE);
                            netSpeedTv.setVisibility(View.VISIBLE);
                            mp.pause();
                            break;
                        //缓冲结束
                        case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                            mLoading.setVisibility(View.GONE);
                            percentTv.setVisibility(View.GONE);
                            netSpeedTv.setVisibility(View.GONE);
                            mp.start(); //缓冲结束再播放
                            break;
                        //正在缓冲
                        case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                            netSpeedTv.setText(extra + "kb/s");
                            break;
                    }
                    return true;
                }
            });
        }
    }

    public boolean initFlag = true;

    @Override
    protected void initData() {
        super.initData();
        mListener = (OnFragListener) mFrags.get(mVp2.getCurrentItem());
        mListener.onLoad(uri);
    }

    public void setUri(String uri) {
//        uri = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8";
        videoView.setVideoURI(Uri.parse(uri));
        mLoading.setVisibility(View.VISIBLE);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                if(!initFlag){
                    load();
                }else initFlag = false;

                videoView.seekTo(cur);
                mLoading.setVisibility(View.GONE);
                // optional need Vitamio 4.0
//                mediaPlayer.setPlaybackSpeed(1.0f);
                //mediaPlayer.setLooping(true);
                long dura = videoView.getDuration();
                mSeekbar.setMax((int) (dura / 1000));
                mDuration.setText(String.format("/%s", TextUtils.time(dura)));
                if (task != null)
                    task.interrupt();
                (task = new Task()).start();
                videoView.start();
                mPlay.setActivated(false);
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int p = seekBar.getProgress();
        videoView.seekTo(p * 1000);
    }

    @OnClick({R.id.iv_play, R.id.iv_full_screen, R.id.display, R.id.full, R.id.next})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_play:
                if (videoView.isPlaying())
                    videoView.pause();
                else videoView.start();
                mPlay.setActivated(!videoView.isPlaying());
                break;
            case R.id.iv_full_screen:
                save();
                fullScreen();
                break;
            case R.id.full:
            case R.id.display:
                if (mToolbar.getVisibility() == View.VISIBLE) {
                    mToolbar.setVisibility(View.GONE);
                    mPanel.setVisibility(View.GONE);
                } else {
                    mToolbar.setVisibility(View.VISIBLE);
                    mPanel.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.next:
                mListener.next();
        }
    }



    @Override
    public void onBackPressed() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            save();
            normalScreen();
            return;
        }
        super.onBackPressed();
    }

    private void switchScreen() {

    }

    private void fullScreen() {
        ViewGroup parent = (ViewGroup) mPlayer.getParent();
        parent.removeAllViews();
        parent.setVisibility(View.GONE);

        int mHideFlags =
                View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        mFull.setSystemUiVisibility(mHideFlags);
        mFull.removeAllViews();
        mFull.addView(mPlayer);

        mFull.setVisibility(View.VISIBLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private void normalScreen() {
        ViewGroup parent = (ViewGroup) mPlayer.getParent();
        parent.removeAllViews();
        parent.setVisibility(View.GONE);

        mDisplay.removeAllViews();
        mDisplay.addView(mPlayer);
        mDisplay.setVisibility(View.VISIBLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    class Task extends Thread {
        @Override
        public void run() {
            while ((cur = videoView.getCurrentPosition()) < videoView.getDuration()) {
                try {
                    long finalCur = cur;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCurtime.setText(TextUtils.time(finalCur<0?0:finalCur));
                            mSeekbar.setProgress((int) (finalCur / 1000));
                        }
                    });
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }


    public interface OnFragListener {
        void onLoad(String uri);

        void next();
    }

    public interface MediaPlayerControl {
        void start();

        void pause();

        long getDuration();

        long getCurrentPosition();

        void seekTo(long pos);

        boolean isPlaying();

        int getBufferPercentage();
    }



    private long cur = 0;

    @Override
    protected void onPause() {
        super.onPause();
        save();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        mFullscreen.setVisibility(mFullscreen.getVisibility()==View.GONE? View.VISIBLE:View.GONE);
        super.onConfigurationChanged(newConfig);
    }

    private void save(){
        cur = videoView.getCurrentPosition();
        SharedPreferences sp = getSharedPreferences("anime", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putLong("curTime", (int) cur);
        ed.apply();
    }

    private void load(){
        SharedPreferences sp = getSharedPreferences("anime", MODE_PRIVATE);
        cur = sp.getLong("curTime", 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getMediaPlayer().start();
        if (task != null) task.interrupt();
    }


}

