package com.xana.acg.mikomiko.frags.search;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xana.acg.Factory;
import com.xana.acg.com.app.PresenterFragment;
import com.xana.acg.com.widget.RoundImageView;
import com.xana.acg.com.widget.recycler.Recycler;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.model.music.Music;
import com.xana.acg.fac.presenter.search.SearchContract;
import com.xana.acg.fac.presenter.search.SearchPresenter;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.actis.MusicPlayerActivity;
import com.xana.acg.mikomiko.actis.SearchActivity;

import java.util.List;

import butterknife.BindView;

public class SearchMusicFragment extends PresenterFragment<SearchContract.Presenter>
        implements SearchContract.View, SearchActivity.OnSearchListener, Recycler.OnMoreLoadListener {

    @BindView(R.id.recycler)
    Recycler mRv;

    private int offset = 0;
    private Adapter mAdapter;

    private String type;

    public SearchMusicFragment(){}


    public SearchMusicFragment(String type){
        this.type = type;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setAdapter(mAdapter = new Adapter());
        mRv.setListener(this);
        mAdapter.setListener(new RecyclerAdapter.AdapterListener<Music>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Music song) {
                acti().navTo(MusicPlayerActivity.class, "music", Factory.getGson().toJson(song));
            }

            @Override
            public void onItemLongClick(RecyclerAdapter.ViewHolder holder, Music song) {

            }
        });
    }

    private String key;
    private boolean isSearch = true;

    @Override
    public void search(String key) {
        this.key = key;
        isSearch = true;
        mPresenter.search(key, offset, true);
    }
    @Override
    public void onMoreLoad() {
        if (!hasMore) return;
        isSearch = false;
        mPresenter.search(key, offset += size, false);
    }

    @Override
    protected SearchContract.Presenter initPresenter() {
        return new SearchPresenter(this);
    }

    private boolean hasMore;
    private int size;

    @Override
    public void onLoad(List<Music> songs, boolean hasMore) {
        ok(0);
        size = songs.size();
        this.hasMore = hasMore;
        if(isSearch)
            mAdapter.replace(songs);
        else mAdapter.add(songs);
    }

    static class Adapter extends RecyclerAdapter<Music> {
        @Override
        protected int getItemViewType(int position, Music song) {
            return R.layout.item_search_music;
        }

        @Override
        protected RecyclerAdapter.ViewHolder<Music> onCreateViewHolder(View root, int viewType) {
            return new SearchMusicFragment.ViewHolder(root);
        }

    }

    public static class ViewHolder extends RecyclerAdapter.ViewHolder<Music> {

        @BindView(R.id.iv_img)
        RoundImageView mImg;
        @BindView(R.id.tv_title)
        TextView mTitle;
        @BindView(R.id.tv_creater)
        TextView mCreater;
        @BindView(R.id.tv_tip)
        TextView mTip;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Music song) {
            mImg.setSrc(song.getAl().getPicUrl());
            mTitle.setText(song.getName());
            mCreater.setText((song.getAr().size() > 0 ? song.getAr().get(0).getName() : "艾米莉亚") + "-" + song.getAl().getName());
            mTip.setText(song.getAlia().size() > 0 ? song.getAlia().get(0) : "艾米莉亚");
        }
    }
}