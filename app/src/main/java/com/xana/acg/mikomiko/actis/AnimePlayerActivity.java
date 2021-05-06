package com.xana.acg.mikomiko.actis;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.model.anime.IptvUri;
import com.xana.acg.fac.presenter.IView;
import com.xana.acg.fac.presenter.music.UriPresenter;
import com.xana.acg.mikomiko.App;
import com.xana.acg.com.app.TabViewPagerActivity;
import com.xana.acg.com.widget.IVideoView;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.frags.SrcFragment;
import com.xana.acg.mikomiko.frags.comment.CommentFragment;
import com.xana.acg.mikomiko.views.AnimeView;

import java.util.Arrays;
import java.util.SortedSet;

import butterknife.BindView;
import butterknife.OnClick;
import top.littlefogcat.danmakulib.danmaku.Danmaku;
import top.littlefogcat.danmakulib.danmaku.DanmakuManager;

public class AnimePlayerActivity extends TabViewPagerActivity
        implements IVideoView.Listener, IView<SortedSet<IptvUri>> {

    @BindView(R.id.full)
    FrameLayout mFull;
    @BindView(R.id.player)
    FrameLayout mPlayer;
    @BindView(R.id.display)
    FrameLayout mDisplay;
    @BindView(R.id.anime)
    AnimeView anime;
    @BindView(R.id.danmaku)
    FrameLayout danmaku;
    @BindView(R.id.edit_danmaku)
    EditText eDanmaku;

    @BindView(R.id.viewStub)
    ViewStub mView;
    @BindView(R.id.fl_container)
    LinearLayout mContain;
    private OnFragListener mListener;

    @OnClick(R.id.iv_trigger)
    void click(View view) {
        Danmaku danmaku = new Danmaku();
        danmaku.text = eDanmaku.getText().toString();
        danmaku.color = "#AFC67EF1";
        danmaku.size = 42;
        dm.send(danmaku);
        eDanmaku.setText("");
        eDanmaku.clearFocus();
        App.hintKb(eDanmaku);
    }

    private DanmakuManager dm;

    @Override
    protected void setFrags() {

        mFrags = Arrays.asList(
                new SrcFragment(),
                uri==null?
                new CommentFragment():
                new CommentFragment(uri.substring(uri.lastIndexOf('/')+1)));

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
        return true;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        anime.initData();
        anime.setListener(this);
        dm = DanmakuManager.getInstance();
        dm.init(this, danmaku);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (uri != null)
            anime.resume();
    }

    String url = "https://1251316161.vod2.myqcloud.com/29fe1275vodbj1251316161/5c1534725285890816229305794/EsvrHBrCk7kA.mp4";
    String url1 = "https://1251316161.vod2.myqcloud.com/007a649dvodcq1251316161/91a28fd65285890814081538994/N0P7oujHVwYA.mp4";
    String url2 = "http://112.74.191.65:3001/anime/6b313038307c3537393633/0/0/url";
    String url3 = "http://112.74.191.65:3001/proxy/anime/6b313038307c3537393633/0/0";

    @Override
    protected void initData() {
        super.initData();
        if(uri==null){
            setIptv();
        }else {
            mListener = (OnFragListener) mFrags.get(mVp2.getCurrentItem());
            mListener.onLoad(uri);
        }
    }

    UriPresenter<SortedSet<IptvUri>> uriPre;
    RecyclerView mRecycler;
    SrcAapter srcAapter;
    private void setIptv() {
        anime.setLive(true);
        mContain.removeAllViews();
        mContain.setVisibility(View.GONE);
        mView.inflate();
        mRecycler = findViewById(R.id.recycler);
        uriPre = new UriPresenter<>(this);
        uriPre.getIptvUri();
        mRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        mRecycler.setAdapter(srcAapter = new SrcAapter());
        srcAapter.setListener(new RecyclerAdapter.AdapterListenerImpl<IptvUri>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, IptvUri iptvUri) {
                SrcHolder hd = (SrcHolder) holder;
                SrcHolder cur = SrcHolder.cur;
                if(cur!=null)
                    cur.mTitle.setActivated(false);
                hd.mTitle.setActivated(true);
                cur = hd;
                setUri(iptvUri.getUrl());
            }
        });
    }

    public void setUri(String uri) {
//        uri = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8";
        anime.play(uri);
    }

    @Override
    public void onBackPressed() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            anime.switchScreen();
            return;
        }
        super.onBackPressed();
    }

    public void fullScreen() {
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

    @Override
    public void norScreen() {
        ViewGroup parent = (ViewGroup) mPlayer.getParent();
        parent.removeAllViews();
        parent.setVisibility(View.GONE);
        mDisplay.removeAllViews();
        mDisplay.addView(mPlayer);
        mDisplay.setVisibility(View.VISIBLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void complete() {
        mListener.next();
    }

    @Override
    public void next() {
        mListener.next();
    }


    // iptv loaded
    @Override
    public void onLoad(SortedSet<IptvUri> res) {
        Log.e("iptv", res.toString());
        srcAapter.add(res);
        setUri(res.first().getUrl());
    }
    // iptv failed
    @Override
    public void onFail(String msg) {

    }

    public interface OnFragListener {
        void onLoad(String uri);

        void next();
    }

    @Override
    protected void onPause() {
        super.onPause();
        anime.save();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(uriPre!=null)
            uriPre.destory();
        uriPre = null;
    }

    public interface MediaPlayerControl {
        void start();

        void pause();

        long getDuration();

        long getCurrentPosition();

        void seekTo(long msec);

        boolean isPlaying();

        int getBufferPercentage();
    }

    static class SrcAapter extends RecyclerAdapter<IptvUri> {
        @Override
        protected int getItemViewType(int p, IptvUri iptvUri) {
            return p % 2 == 0 ? R.layout.item_episode_left : R.layout.item_episode_right;
        }
        @Override
        protected ViewHolder<IptvUri> onCreateViewHolder(View root, int viewType) {
            return new AnimePlayerActivity.SrcHolder(root);
        }
    }
    static class SrcHolder extends RecyclerAdapter.ViewHolder<IptvUri> {

        public static SrcHolder cur;

        @BindView(R.id.tv_title)
        TextView mTitle;
        public SrcHolder(View itemView) {
            super(itemView);
        }
        @Override
        protected void onBind(IptvUri iptvUri) {
            mTitle.setText(iptvUri.getName());
        }
    }
}

