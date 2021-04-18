package com.xana.acg.mikomiko.frags;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xana.acg.com.app.PresenterFragment;
import com.xana.acg.com.widget.PortraitView;
import com.xana.acg.com.widget.RoundImageView;
import com.xana.acg.com.widget.SquareImageView;
import com.xana.acg.com.widget.recycler.Recycler;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.model.music.Data;
import com.xana.acg.fac.model.music.Elite;
import com.xana.acg.fac.model.music.NewSong;
import com.xana.acg.fac.model.music.SongList;
import com.xana.acg.fac.presenter.MusicContract;
import com.xana.acg.fac.presenter.MusicPresenter;
import com.xana.acg.mikomiko.NoScrollGridLayoutManager;
import com.xana.acg.mikomiko.R;

import java.util.Date;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.OnClick;

public class MusicFragment extends PresenterFragment<MusicContract.Presenter>
    implements MusicContract.View, Recycler.OnMoreLoadListener{

    @BindView(R.id.rv_song_list)
    RecyclerView mSongList;

    @BindView(R.id.rv_new_song)
    RecyclerView mNewSong;

    @BindView(R.id.rv_elite)
    Recycler mElite;


    private SongListAdapter mSongListAdapter;
    private NewSongAdapter mNewSongAdapter;
    private DataAdapter mDataAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_music;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mSongList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mSongList.setAdapter(mSongListAdapter = new SongListAdapter());
        mSongList.setNestedScrollingEnabled(false);
        mNewSong.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mNewSong.setAdapter(mNewSongAdapter = new NewSongAdapter());
        mNewSong.setNestedScrollingEnabled(false);
        mElite.setLayoutManager(new LinearLayoutManager(getContext()));
        mElite.setAdapter(mDataAdapter = new DataAdapter());
        mElite.setNestedScrollingEnabled(false);
        mElite.setListener(this);
    }


    private int offset = 0;
    @Override
    protected void initData() {
        super.initData();
        mPresenter.getSongList(6);
        mPresenter.getNewSongs(6);
        mPresenter.getElite(offset);
    }


    private boolean refresh = false;

    @OnClick({R.id.tv_get})
    void click(View view){
        switch (view.getId()){
            case R.id.tv_get:
                mPresenter.getElite(offset);
                refresh = true;
                break;
        }
    }


    @Override
    protected MusicContract.Presenter initPresenter() {
        return new MusicPresenter(this);
    }

    @Override
    public void onSongListLoad(List<SongList> songLists) {
        Log.e("歌单", songLists+"");
        mSongListAdapter.replace(songLists);
    }


    @Override
    public void onNewSongsLoad(List<NewSong> songs) {
        mNewSongAdapter.replace(songs);
    }


    private boolean hasMore;
    @Override
    public void onEliteLoad(RespModel<List<Data>> resp) {
        hasMore = resp.hasMore();
        if(!refresh)
            mDataAdapter.add(resp.getDatas());
        else mDataAdapter.replace(resp.getDatas());
    }

    @Override
    public void onMoreLoad() {
        refresh = false;
        if(!hasMore) return;
        Log.e("hasMore", hasMore+"");
        mPresenter.getElite(++offset);
    }


    static class SongListAdapter extends RecyclerAdapter<SongList>{
        @Override
        protected int getItemViewType(int position, SongList songList) {
            return R.layout.item_index_music_song;
        }

        @Override
        protected RecyclerAdapter.ViewHolder<SongList> onCreateViewHolder(View root, int viewType) {
            return new ViewHolder(root);
        }

        class ViewHolder extends RecyclerAdapter.ViewHolder<SongList>{

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
            protected void onBind(SongList songList) {
                mImg.setSrc(songList.getPicUrl());
                mTitle.setText(songList.getName());
                int playCount = songList.getPlayCount();
                mPlays.setText(playCount>=10000?playCount/10000+"万": playCount+"");
            }
        }
    }


    static class NewSongAdapter extends RecyclerAdapter<NewSong>{


        @Override
        protected int getItemViewType(int position, NewSong newSong) {
            return R.layout.item_index_music_song_new;
        }

        @Override
        protected RecyclerAdapter.ViewHolder<NewSong> onCreateViewHolder(View root, int viewType) {
            return new ViewHolder(root);
        }

        class ViewHolder extends RecyclerAdapter.ViewHolder<NewSong>{

            @BindView(R.id.iv_img)
            SquareImageView mImg;
            @BindView(R.id.tv_title)
            TextView mTitle;

            public ViewHolder(View itemView) {
                super(itemView);
            }

            @Override
            protected void onBind(NewSong newSong) {
                mImg.setSrc(newSong.getPicUrl());
                mTitle.setText(newSong.getName());
            }
        }
    }

    static class DataAdapter extends RecyclerAdapter<Data>{

        @Override
        protected int getItemViewType(int position, Data data) {
            return R.layout.item_index_music_elite;
        }

        @Override
        protected RecyclerAdapter.ViewHolder<Data> onCreateViewHolder(View root, int viewType) {
            return new ViewHolder(root);
        }

        class ViewHolder extends RecyclerAdapter.ViewHolder<Data>{
            @BindView(R.id.iv_img)
            RoundImageView mImg;
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

            public ViewHolder(View itemView) {
                super(itemView);
            }

            @Override
            protected void onBind(Data data) {
                Elite elite = data.getData();
                mImg.setSrc(elite.getCoverUrl());
                mTitle.setText(elite.getTitle());
                mThumb.setText(elite.getPraisedCount()+"");
                mComment.setText(elite.getCommentCount()+"");
                mAvater.setSrc(elite.getCreator().getAvatarUrl());
                mPlays.setText(elite.getPlayTime()+"");
                mDura.setText(DateFormat.format("mm:ss", new Date(elite.getDurationms())));
            }
        }
    }
}