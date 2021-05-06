package com.xana.acg.mikomiko.actis;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xana.acg.Factory;
import com.xana.acg.com.app.PresenterActivity;
import com.xana.acg.com.widget.PortraitView;
import com.xana.acg.com.widget.RoundImageView;
import com.xana.acg.com.widget.SquareImageView;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.model.music.Music;
import com.xana.acg.fac.model.music.MusicListDetail;
import com.xana.acg.fac.presenter.music.MusicListContract;
import com.xana.acg.fac.presenter.music.MusicListPresenter;
import com.xana.acg.com.utils.TextUtils;
import com.xana.acg.mikomiko.R;

import butterknife.BindView;

public class MusicListActivity extends PresenterActivity<MusicListContract.Presenter>
        implements MusicListContract.View, RecyclerAdapter.AdapterListener<Music> {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @BindView(R.id.siv_cover)
    SquareImageView mCover;

    @BindView(R.id.tv_title)
    TextView mTitle;

    @BindView(R.id.pv_creator_avatar)
    PortraitView mAvatar;

    @BindView(R.id.tv_creater_nickname)
    TextView mNickname;

    @BindView(R.id.tv_des)
    TextView mDes;

    @BindView(R.id.tv_comment)
    TextView mComment;

    @BindView(R.id.tv_share)
    TextView mShare;

    @BindView(R.id.tv_total_music)
    TextView mMusics;

    @BindView(R.id.tv_favor)
    TextView mFavor;

    /*
    歌单id
     */
    private String id;

    private Adapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_music_list;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        id = bundle.getString("id");
        return id != null;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter = new Adapter());
        mAdapter.setListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getDetial(id);
    }

    @Override
    protected MusicListContract.Presenter initPresenter() {
        return new MusicListPresenter(this);
    }

    @Override
    public void onSuccess(MusicListDetail data) {
        MusicListDetail.PlayList playlist = data.getPlaylist();
        mCover.setSrc(playlist.getCoverImgUrl());
        mTitle.setText(playlist.getName());
        mAvatar.setSrc(playlist.getCreator().getAvatarUrl());
        mNickname.setText(playlist.getCreator().getNickname());
        mDes.setText(playlist.getDescription());
        mComment.setText(TextUtils.getCountStr(playlist.getCommentCount()));
        mShare.setText(TextUtils.getCountStr(playlist.getShareCount()));
        mMusics.setText(String.format(getString(R.string.label_total_music), playlist.getTrackCount()));
        mFavor.setText(String.format(getString(R.string.label_favor_op_and_data), TextUtils.getCountStr(playlist.getSubscribedCount())));

        mAdapter.replace(data.getPlaylist().getTracks());
        ok(0);
    }

    @Override
    public void onFail(String msg) {

    }


    private ViewHolder curHolder;

    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, Music music) {
        if (curHolder == holder) return;
        if (curHolder != null) {
            curHolder.mNumber.setVisibility(View.VISIBLE);
            curHolder.mPlaying.setVisibility(View.GONE);
        }
        curHolder = (ViewHolder) holder;
        curHolder.mNumber.setVisibility(View.GONE);
        curHolder.mPlaying.setVisibility(View.VISIBLE);
        navTo(MusicPlayerActivity.class, "music", Factory.getGson().toJson(music));
    }

    @Override
    public void onItemLongClick(RecyclerAdapter.ViewHolder holder, Music music) {

    }

    public static class Adapter extends RecyclerAdapter<Music> {

        private int viewType = R.layout.item_music_list;

        public Adapter(){}
        public Adapter(int item){
            viewType = item;
        }
        @Override
        protected int getItemViewType(int position, Music music) {
            return viewType;
        }

        @Override
        protected RecyclerAdapter.ViewHolder<Music> onCreateViewHolder(View root, int viewType) {
            return new MusicListActivity.ViewHolder(root);
        }


    }
    public static class ViewHolder extends RecyclerAdapter.ViewHolder<Music> {

        @Nullable
        @BindView(R.id.tv_number)
        public TextView mNumber;

        @Nullable
        @BindView(R.id.iv_img)
        public RoundImageView mImg;

        @BindView(R.id.iv_playing)
        ImageView mPlaying;

        @BindView(R.id.tv_title)
        TextView mTitle;

        @BindView(R.id.tv_title_des)
        TextView mTitleDes;


        @BindView(R.id.tv_creater)
        TextView mCreater;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Music music) {
            if(mNumber!=null)
                mNumber.setText(String.valueOf(getLayoutPosition() + 1));
            if(mImg!=null)
                mImg.setSrc(music.getAl().getPicUrl());

            mTitle.setText(music.getName());
            mTitleDes.setText(music.getAlia().size() > 0 ? "(" + music.getAlia().get(0) + ")" : "");
            mCreater.setText((music.getAr().size() > 0 ? music.getAr().get(0).getName() : "") + " - " + music.getAl().getName());
        }
    }


}