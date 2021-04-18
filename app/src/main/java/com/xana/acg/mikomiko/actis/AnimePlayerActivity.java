package com.xana.acg.mikomiko.actis;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xana.acg.com.app.Fragment;
import com.xana.acg.com.app.ToolbarActivity;
import com.xana.acg.fac.model.anime.Detail;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.adaps.FSAdapter;
import com.xana.acg.mikomiko.frags.BlankFragment;
import com.xana.acg.mikomiko.frags.DetailFragment;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.utils.Log;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class AnimePlayerActivity extends ToolbarActivity {

    @BindView(R.id.vp2)
    ViewPager2 mViewPager;

    @BindView(R.id.tab)
    TabLayout mTab;

    @BindView(R.id.vitamio)
    VideoView videoView;

    @BindView(R.id.buffer_percent)
    TextView percentTv;
    @BindView(R.id.net_speed)
    TextView netSpeedTv;

    private List<String> mTitle = Arrays.asList("简介", "评论");

    private List<Fragment> mFragments = Arrays.asList(
            new DetailFragment(),
            new BlankFragment()
    );
    private OnFragLoadListener mListener;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_anime_player;
    }

    private String uri;

    @Override
    protected boolean initArgs(Bundle bundle) {
        uri = bundle.getString("uri");
        return uri != null;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTab();
        mListener = (OnFragLoadListener) mFragments.get(mViewPager.getCurrentItem());
        mListener.onLoad(uri);
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
            videoView = findViewById(R.id.vitamio);
            videoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
            MediaController controller = new MediaController(this);
            videoView.setMediaController(controller);
            videoView.setBufferSize(10240); //设置视频缓冲大小。默认1024KB，单位byte
            videoView.requestFocus();

            videoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    percentTv.setText("已缓冲：" + percent + "%");
                }
            });
            videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    switch (what) {
                        //开始缓冲
                        case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                            percentTv.setVisibility(View.VISIBLE);
                            netSpeedTv.setVisibility(View.VISIBLE);
                            mp.pause();
                            break;
                        //缓冲结束
                        case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                            percentTv.setVisibility(View.GONE);
                            netSpeedTv.setVisibility(View.GONE);
                            mp.start(); //缓冲结束再播放
                            break;
                        //正在缓冲
                        case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                            netSpeedTv.setText("当前网速:" + extra + "kb/s");
                            break;
                    }
                    return true;
                }
            });
        }
    }

    private void setTab() {
        FSAdapter fsAdapter = new FSAdapter(getSupportFragmentManager(), getLifecycle(), mFragments);
        mViewPager.setAdapter(fsAdapter);
        new TabLayoutMediator(mTab, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int index) {
                tab.setText(mTitle.get(index));
            }
        }).attach();
    }


    private String animeUri;

    public void setUri(String uri) {
        animeUri = uri;
        Log.e("MyUri", uri);
        videoView.setVideoURI(Uri.parse(animeUri));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                // optional need Vitamio 4.0
                mediaPlayer.setPlaybackSpeed(1.0f);
                //mediaPlayer.setLooping(true);
            }
        });
    }

    public interface OnFragLoadListener {
        void onLoad(String uri);
    }

}

