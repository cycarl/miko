package com.xana.acg.mikomiko.frags.search;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xana.acg.Factory;
import com.xana.acg.com.app.PresenterFragment;
import com.xana.acg.com.widget.RoundImageView;
import com.xana.acg.com.widget.recycler.Recycler;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.model.music.SearchResult;
import com.xana.acg.fac.presenter.search.SearchContract;
import com.xana.acg.fac.presenter.search.SearchPresenter;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.actis.MusicPlayerActivity;
import com.xana.acg.mikomiko.actis.SearchActivity;

import java.util.List;

import butterknife.BindView;

public class SearchMusicFragment extends PresenterFragment<SearchContract.Presenter>
        implements SearchContract.View, SearchActivity.OnSearchListener, Recycler.OnMoreLoadListener {

    @BindView(R.id.rv)
    Recycler mRv;

    private int offset = 0;
    private Adapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_music;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setAdapter(mAdapter = new Adapter());
        mRv.setListener(this);
        mAdapter.setListener(new RecyclerAdapter.AdapterListener<SearchResult.Song>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, SearchResult.Song song) {
                activity().navTo(MusicPlayerActivity.class, "song", Factory.getGson().toJson(song));
            }

            @Override
            public void onItemLongClick(RecyclerAdapter.ViewHolder holder, SearchResult.Song song) {

            }
        });
    }

    private String key;
    private boolean isSearch = true;

    @Override
    public void search(String key) {
        this.key = key;
        isSearch = true;
        mPresenter.search(key, offset);
    }
    @Override
    public void onMoreLoad() {
        if (!hasMore) return;
        isSearch = false;
        mPresenter.search(key, offset += size);
    }

    @Override
    protected SearchContract.Presenter initPresenter() {
        return new SearchPresenter(this);
    }

    private boolean hasMore;
    private int size;

    @Override
    public void onLoad(List<SearchResult.Song> songs, boolean hasMore) {
        size = songs.size();
        this.hasMore = hasMore;
        if(isSearch)
            mAdapter.replace(songs);
        else mAdapter.add(songs);
    }

    static class Adapter extends RecyclerAdapter<SearchResult.Song> {
        @Override
        protected int getItemViewType(int position, SearchResult.Song song) {
            return R.layout.item_search_music;
        }

        @Override
        protected RecyclerAdapter.ViewHolder<SearchResult.Song> onCreateViewHolder(View root, int viewType) {
            return new ViewHolder(root);
        }

        class ViewHolder extends RecyclerAdapter.ViewHolder<SearchResult.Song> {

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
            protected void onBind(SearchResult.Song song) {
                mImg.setSrc(song.getAl().getPicUrl());
                mTitle.setText(song.getName());
                mCreater.setText((song.getAr().size() > 0 ? song.getAr().get(0).getName() : "艾米莉亚") + "-" + song.getAl().getName());
                mTip.setText(song.getAlia().size() > 0 ? song.getAlia().get(0) : "艾米莉亚");
            }
        }
    }
}