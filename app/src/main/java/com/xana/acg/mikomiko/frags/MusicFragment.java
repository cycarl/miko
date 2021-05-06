package com.xana.acg.mikomiko.frags;

import android.content.Context;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xana.acg.com.loader.GlideImageLoader;
import com.xana.acg.mikomiko.App;
import com.xana.acg.com.app.PresenterFragment;
import com.xana.acg.com.widget.PortraitView;
import com.xana.acg.com.widget.RoundImageView;
import com.xana.acg.com.widget.SquareImageView;
import com.xana.acg.mikomiko.views.VideoView;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.model.music.Data;
import com.xana.acg.fac.model.music.Musicer;
import com.xana.acg.fac.model.music.Video;
import com.xana.acg.fac.model.music.NewMusic;
import com.xana.acg.fac.model.music.MusicList;
import com.xana.acg.fac.model.music.VideoUri;
import com.xana.acg.fac.presenter.IView;
import com.xana.acg.fac.presenter.MusicContract;
import com.xana.acg.fac.presenter.MusicPresenter;
import com.xana.acg.fac.presenter.music.UriPresenter;
import com.xana.acg.com.utils.TextUtils;
import com.xana.acg.mikomiko.IndexActivity;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.actis.DailyMusicActivity;
import com.xana.acg.mikomiko.actis.MusicListActivity;
import com.xana.acg.mikomiko.actis.MusicListMallActivity;
import com.xana.acg.mikomiko.actis.NewMusicMallActivity;
import com.xana.acg.mikomiko.actis.VideoMallActivity;
import com.youth.banner.Banner;

import java.util.Collections;
import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;

import static com.xana.acg.com.Common.Avatar;
import static com.xana.acg.com.Common.DIGIT.BAN;
import static com.xana.acg.com.Common.DIGIT.OKU;

public class MusicFragment extends PresenterFragment<MusicContract.Presenter>
        implements MusicContract.View,
        IndexActivity.OnRefreshListenter,
        NestedScrollView.OnScrollChangeListener {

    @BindView(R.id.ns_page)
    NestedScrollView mPage;

    @BindView(R.id.rv_song_list)
    RecyclerView mSongList;

    @BindView(R.id.rv_new_song)
    RecyclerView mNewSong;

    @BindView(R.id.rv_elite)
    RecyclerView mElite;

    @BindView(R.id.banner)
    Banner mBanner;

    private MusicListAdapter mSongListAdapter;
    private NewMusicAdapter mNewSongAdapter;
    private DataAdapter mDataAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = (IndexActivity) context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_music;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mSongList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mSongList.setAdapter(mSongListAdapter = new MusicListAdapter());
        mSongList.setNestedScrollingEnabled(false);
        mSongListAdapter.setListener(new RecyclerAdapter.AdapterListener<MusicList>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, MusicList songList) {
                acti().navTo(MusicListActivity.class, "id", songList.getId());
            }

            @Override
            public void onItemLongClick(RecyclerAdapter.ViewHolder holder, MusicList songList) {

            }
        });
        mNewSong.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mNewSong.setAdapter(mNewSongAdapter = new NewMusicAdapter());
        mNewSong.setNestedScrollingEnabled(false);
        mElite.setLayoutManager(new LinearLayoutManager(getContext()));
        mElite.setAdapter(mDataAdapter = new DataAdapter());
        mElite.setNestedScrollingEnabled(false);
        mPage.setOnScrollChangeListener(this);
        mBanner.setImageLoader(new GlideImageLoader());
    }


    private int offset = 0;

    @Override
    protected void initData() {
        super.initData();

        mCtx.refreshStart();
        mPresenter.getSongList(30);
        mPresenter.getNewSongs(3);
        mPresenter.getElite(offset);
        mPresenter.getBanner(7);
    }


    private boolean refresh = false;

    @OnClick({R.id.iv_recom, R.id.iv_music_list, R.id.tv_music_list, R.id.tv_new_music, R.id.ll_refresh})
    void click(View view) {
        switch (view.getId()) {
            case R.id.iv_recom:
                mCtx.navTo(DailyMusicActivity.class);
                break;
            case R.id.iv_music_list:
            case R.id.tv_music_list:
                mCtx.navTo(MusicListMallActivity.class);
                break;
            case R.id.tv_new_music:
                mCtx.navTo(NewMusicMallActivity.class);
                break;

            case R.id.ll_refresh:
                mCtx.navTo(VideoMallActivity.class);
                break;
        }
    }

    @Override
    protected MusicContract.Presenter initPresenter() {
        return new MusicPresenter(this);
    }


    private List<MusicList> mSongLists;

    @Override
    public void onBannerLoad(List<String> banner) {
        mBanner.setImages(banner);
        mBanner.start();
    }
    @Override
    public void onSongListLoad(List<MusicList> songLists) {
        ok(0);
        if (songLists == null) return;
        mSongLists = songLists;

        rand(songLists, 6, 1);

        mCtx.refreshEnd(null);
    }

    private void rand(List<MusicList> arr, int len, int cur) {
        if (cur > len)
            return;
        int l = arr.size() - cur;
        int r = (int) (Math.random() * l);
        mSongListAdapter.replace(cur - 1, arr.get(r));
        Collections.swap(arr, r, l);

        rand(arr, len, cur + 1);
    }


    @Override
    public void onNewSongsLoad(List<NewMusic> songs) {
        ok(0);
        mNewSongAdapter.replace(songs);
        mCtx.refreshEnd(null);
    }


    private boolean hasMore;

    @Override
    public void onEliteLoad(RespModel<List<Data>> resp) {
        ok(0);
        hasMore = resp.hasMore();
        if (refresh) {
            mDataAdapter.replace(resp.getDatas());
        } else mDataAdapter.add(resp.getDatas());
        mCtx.refreshEnd(getString(R.string.tip_get_new));
    }

    @Override
    public void showMsg(String msg) {
        super.showMsg(msg);
        ok(mSongListAdapter);
        mCtx.refreshEnd(null);
    }

    public void onMoreLoad() {
        refresh = false;
        if (!hasMore) {
            showMsg(R.string.tip_no_more);
            return;
        }
        mPresenter.getElite(++offset);
    }

    private IndexActivity mCtx;

    @Override
    public void onRefresh(IndexActivity ctx) {
        refresh = true;
        onSongListLoad(mSongLists);
        if (mSongLists == null)
            mPresenter.getSongList(30);
        if (mNewSongAdapter.getItemCount() == 0)
            mPresenter.getNewSongs(3);
        mPresenter.getElite(offset = ++offset % 7);
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
            onMoreLoad();
        }
    }

    public static class MusicListAdapter extends RecyclerAdapter<MusicList> {
        @Override
        protected int getItemViewType(int position, MusicList songList) {
            return R.layout.item_index_music_song;
        }

        @Override
        protected RecyclerAdapter.ViewHolder<MusicList> onCreateViewHolder(View root, int viewType) {
            return new ViewHolder(root);
        }

        public static class ViewHolder extends RecyclerAdapter.ViewHolder<MusicList> {

            @BindView(R.id.iv_img)
            SquareImageView mImg;
            @BindView(R.id.tv_title)
            TextView mTitle;

            @BindView(R.id.tv_plays)
            TextView mPlays;

            public ViewHolder(View itemView) {
                super(itemView);
            }

            @Override
            protected void onBind(MusicList songList) {
                mImg.setSrc(songList.getPicUrl() == null ? songList.getCoverImgUrl() : songList.getPicUrl());
                mTitle.setText(songList.getName());
                long playCount = songList.getPlayCount();
                String playCountStr = playCount >= OKU ? playCount / OKU + "亿" : playCount >= BAN ? playCount / BAN + "万" : "" + playCount;
                mPlays.setText(playCountStr);
            }
        }
    }


    static class NewMusicAdapter extends RecyclerAdapter<NewMusic> {


        @Override
        protected int getItemViewType(int position, NewMusic newSong) {
            return R.layout.item_index_music_song_new;
        }

        @Override
        protected RecyclerAdapter.ViewHolder<NewMusic> onCreateViewHolder(View root, int viewType) {
            return new ViewHolder(root);
        }

        static class ViewHolder extends RecyclerAdapter.ViewHolder<NewMusic> {

            @BindView(R.id.iv_img)
            SquareImageView mImg;
            @BindView(R.id.tv_title)
            TextView mTitle;

            public ViewHolder(View itemView) {
                super(itemView);
            }

            @Override
            protected void onBind(NewMusic newSong) {
                mImg.setSrc(newSong.getPicUrl());
                mTitle.setText(newSong.getName());
            }
        }
    }

    public static class DataAdapter extends RecyclerAdapter<Data> {

        private VideoView videoView;

        @Override
        protected int getItemViewType(int position, Data data) {
            return R.layout.item_index_music_elite;
        }

        @Override
        protected RecyclerAdapter.ViewHolder<Data> onCreateViewHolder(View root, int viewType) {
            return new ViewHolder(root);
        }

        class ViewHolder extends RecyclerAdapter.ViewHolder<Data> implements IView<VideoUri> {
            @BindView(R.id.iv_img)
            RoundImageView mImg;

            @BindView(R.id.cover)
            FrameLayout mCover;

            @BindView(R.id.viewStub)
            ViewStub mSurf;

            @BindView(R.id.tv_title)
            TextView mTitle;

            @BindView(R.id.tv_thumb)
            TextView mThumb;
            @BindView(R.id.tv_comment)
            TextView mComment;

            @BindView(R.id.iv_avater)
            PortraitView mAvater;

            @BindView(R.id.tv_plays)
            TextView mPlays;

            @BindView(R.id.tv_dura)
            TextView mDura;

            private String id;
            private String uri;
            private VideoView mVideo;

            public ViewHolder(View itemView) {
                super(itemView);
            }

            @Override
            protected void onBind(Data data) {
                id = data.getData().getVid();

                Video<Musicer> elite = data.getData();
                Musicer ct = elite.getCreator();

                mImg.setSrc(elite.getCoverUrl());
                mTitle.setText(elite.getTitle());
                mThumb.setText(elite.getPraisedCount() + "");
                mComment.setText(elite.getCommentCount() + "");
                mAvater.setSrc(ct!=null? ct.getAvatarUrl(): Avatar);
                mPlays.setText(elite.getPlayTime() + "");
                mDura.setText(TextUtils.time(elite.getDurationms()));
            }



            @OnClick(R.id.iv_img)
            void click(View view) {
                if(videoView!=null)
                    videoView.release();
                if(mVideo==null) {
                    mSurf.inflate();
                    mVideo = itemView.findViewById(R.id.videoView);
                }
                videoView = mVideo;
                videoView.setPlayer(App.getMediaPlayer());
                if(uri==null){
                    UriPresenter<VideoUri> vp = new UriPresenter<>(this);
                    vp.getVidoeUri(id);
                }else videoView.play(uri);

            }
            @Override
            public void onLoad(VideoUri res) {
                uri = res.getUri();
                videoView.play(uri);
            }

            @Override
            public void onFail(String msg) {
                App.showToast(msg);
            }
        }
    }
}