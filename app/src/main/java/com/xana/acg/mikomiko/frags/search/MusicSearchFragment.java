package com.xana.acg.mikomiko.frags.search;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.xana.acg.Factory;
import com.xana.acg.com.app.Activity;
import com.xana.acg.com.app.PresenterFragment;
import com.xana.acg.com.widget.PortraitView;
import com.xana.acg.com.widget.RoundImageView;
import com.xana.acg.com.widget.recycler.Recycler;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.model.account.Profile;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.model.music.Album;
import com.xana.acg.fac.model.music.MV;
import com.xana.acg.fac.model.music.MusicList;
import com.xana.acg.fac.model.music.Video;
import com.xana.acg.fac.model.music.search.ISearch;
import com.xana.acg.fac.presenter.search.MusicSearchPresenter;
import com.xana.acg.fac.presenter.search.SearchContract;
import com.xana.acg.com.utils.TextUtils;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.actis.MusicListActivity;
import com.xana.acg.mikomiko.actis.MusicPlayerActivity;
import com.xana.acg.mikomiko.actis.MusicSearchActivity;
import com.xana.acg.mikomiko.frags.MusicFragment;

import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class MusicSearchFragment<T extends RespModel<ISearch>> extends PresenterFragment<SearchContract.Presenter>
        implements SearchContract.MusicView<T>,
        Recycler.OnMoreLoadListener,
        MusicSearchActivity.OnSearchListener, RecyclerAdapter.AdapterListener {

    private Activity mCtx;

    @BindView(R.id.recycler)
    Recycler mRecycler;

    private String keyword;
    private int type;

    private Adapter mAdapter;

    private int offset = 0;
    private int count = 0;
    private int itemLayout = 0;


    public MusicSearchFragment(int type) {
        this.type = type;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = (Activity) context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_recycler;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRecycler.setListener(this);
        mRecycler.setLayoutManager(type == 1000 ? new GridLayoutManager(mCtx, 3) : new LinearLayoutManager(mCtx));
        mRecycler.setAdapter(mAdapter = new Adapter());
        mAdapter.setListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onMoreLoad() {
        // TODO
        if (offset >= count) {
            showMsg(R.string.tip_no_more);
            return;
        }
        mPresenter.search(type, keyword, offset += mAdapter.getItemCount(), false);
    }

    @Override
    public void onSearch(String key) {
        if (key.equals(keyword) && mAdapter.getItemCount() > 0) {
            return;
        }
        mPresenter.search(type, keyword = key, offset = 0, true);
    }

    @Override
    public void onLoad(T resp) {
        ok(0);
        ISearch res = resp.getResult();
        count = res.getCount();

        if (resp.refresh)
            mAdapter.replace(res.getDatas());
        else mAdapter.add(res.getDatas());
    }

    @Override
    public void showMsg(String msg) {
        super.showMsg(msg);
        ok(mAdapter);
    }

    @Override
    protected SearchContract.Presenter initPresenter() {
        return new MusicSearchPresenter<>(this);
    }

    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, Object o) {
        switch (type) {
            case 1:
                mCtx.navTo(MusicPlayerActivity.class, "music", Factory.getGson().toJson(o));
                break;
            case 1000:
                mCtx.navTo(MusicListActivity.class, "id", ((MusicList) o).getId());
                break;

        }
    }

    @Override
    public void onItemLongClick(RecyclerAdapter.ViewHolder holder, Object o) {

    }

    class Adapter extends RecyclerAdapter {
        @Override
        protected int getItemViewType(int position, Object song) {

            switch (type) {
                case 1:
                case 10:
                    return R.layout.item_search_music;
                case 1000:
                    return R.layout.item_index_music_song;
                case 1004:
                case 1014:
                    return R.layout.item_search_mv;
                case 1002:
                    return R.layout.item_search_user;
            }

            return 0;
        }

        @Override
        protected RecyclerAdapter.ViewHolder onCreateViewHolder(View root, int viewType) {
            switch (type) {
                case 1:
                    return new SingleMusicViewHolder(root);
                case 1000:
                    return new MusicListViewHolder(root);
                case 1004:
                    return new MvViewHolder(root);
                case 1014:
                    return new VideoViewHolder(root);
                case 10:
                    return new AlbumViewHolder(root);
                case 1002:
                    return new UserViewHolder(root);
            }
            return null;
        }
    }
}


class MusicListViewHolder extends MusicFragment.MusicListAdapter.ViewHolder {
    public MusicListViewHolder(View itemView) {
        super(itemView);
    }
}

class SingleMusicViewHolder extends SearchMusicFragment.ViewHolder {
    public SingleMusicViewHolder(View itemView) {
        super(itemView);
    }
}

class MvViewHolder extends RecyclerAdapter.ViewHolder {
    @BindView(R.id.iv_img)
    RoundImageView mImg;

    @BindView(R.id.tv_plays)
    TextView mPlays;

    @BindView(R.id.tv_dura)
    TextView mDura;

    @BindView(R.id.tv_title)
    TextView mTitle;

    @BindView(R.id.tv_artist)
    TextView mArtist;

    public MvViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void onBind(Object o) {
        MV mv = (MV) o;
        mImg.setSrc(mv.getCover());
        mDura.setText(DateFormat.format("mm:ss", new Date(mv.getDuration())));
        mPlays.setText(TextUtils.getCountStr(mv.getPlayCount()));
        mTitle.setText(mv.getName());
        mArtist.setText(mv.getArtistName());
    }
}

class VideoViewHolder extends MvViewHolder {

    public VideoViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void onBind(Object o) {
        Video<List<Video.Creator>> video = (Video<List<Video.Creator>>) o;
        mImg.setSrc(video.getCoverUrl());
        mDura.setText(DateFormat.format("mm:ss", new Date(video.getDurationms())));
        mPlays.setText(TextUtils.getCountStr(video.getPlayTime()));
        mTitle.setText(video.getTitle());
        mArtist.setText(video.getCreator().get(0).getUserName());
    }
}

class AlbumViewHolder extends RecyclerAdapter.ViewHolder<Album> {

    @BindView(R.id.iv_img)
    RoundImageView mImg;
    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.tv_creater)
    TextView mCreater;
    @BindView(R.id.tv_tip)
    TextView mTip;

    public AlbumViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void onBind(Album album) {
        mImg.setSrc(album.getPicUrl());
        mTitle.setText(album.getName());
        mCreater.setText(album.getArtists().get(0).getName());
        mTip.setText(album.getCompany());
    }
}

class UserViewHolder extends RecyclerAdapter.ViewHolder<Profile> {
    @BindView(R.id.pv_up_avater)
    PortraitView mAvater;
    @BindView(R.id.tv_nickname)
    TextView mNickname;
    @BindView(R.id.iv_sex)
    ImageView mSex;
    @BindView(R.id.tv_sign)
    TextView mSign;

    public UserViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void onBind(Profile profile) {
        mAvater.setSrc(profile.avatarUrl);
        mNickname.setText(profile.nickname);
        if (profile.gender == 0)
            mSex.setVisibility(View.GONE);
        else if (profile.gender == 1)
            mSex.setActivated(true);
        else mSex.setActivated(false);

        if (TextUtils.isEmpty(profile.signature))
            mSign.setVisibility(View.GONE);
        else {
            mSign.setVisibility(View.VISIBLE);
            mSign.setText(profile.signature);
        }
    }
}