package com.xana.acg.mikomiko.frags.search;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xana.acg.com.app.PresenterFragment;
import com.xana.acg.com.widget.RoundImageView;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.model.anime.Anime;
import com.xana.acg.fac.presenter.search.SearchAnimePresenter;
import com.xana.acg.fac.presenter.search.SearchContract;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.actis.AnimePlayerActivity;
import com.xana.acg.mikomiko.actis.SearchActivity;

import java.util.List;

import butterknife.BindView;

public class SearchAnimeFragment extends PresenterFragment<SearchContract.AnimePresenter>
        implements SearchContract.AnimeView, SearchActivity.OnSearchListener, RecyclerAdapter.AdapterListener<Anime> {

    @BindView(R.id.rv_left)
    RecyclerView mRvLeft;

    @BindView(R.id.rv_right)
    RecyclerView mRvRight;

    private Adapter mLeftAdapter, mRightAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_anime;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRvLeft.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvLeft.setNestedScrollingEnabled(false);
        mRvRight.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvRight.setNestedScrollingEnabled(false);
        mRvLeft.setAdapter(mLeftAdapter = new Adapter(R.layout.item_anime_left));
        mRvRight.setAdapter(mRightAdapter = new Adapter(R.layout.item_anime_right));
        mLeftAdapter.setListener(this);
        mRightAdapter.setListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        search("异世界");
    }

    @Override
    protected SearchContract.AnimePresenter initPresenter() {
        return new SearchAnimePresenter(this);
    }

    @Override
    public void onLeftLoad(List<Anime> animes) {
        mLeftAdapter.replace(animes);
    }

    @Override
    public void onRightLoad(List<Anime> animes) {
        ok(0);
        mRightAdapter.replace(animes);
    }

    /**
     * 来自Activity数据
     *
     * @param key
     */
    @Override
    public void search(String key) {
        mPresenter.search(key);
    }

    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, Anime anime) {
        acti().navTo(AnimePlayerActivity.class, "uri", anime.getUrl());
    }

    @Override
    public void onItemLongClick(RecyclerAdapter.ViewHolder holder, Anime anime) {

    }

    public static class Adapter extends RecyclerAdapter<Anime> {
        private @LayoutRes
        int viewType;

        public Adapter(){}

        public Adapter(@LayoutRes int itemLayout) {
            viewType = itemLayout;
        }

        @Override
        protected int getItemViewType(int position, Anime anime) {
            return viewType;
        }

        @Override
        protected RecyclerAdapter.ViewHolder<Anime> onCreateViewHolder(View root, int viewType) {
            return new ViewHolder(root);
        }

        static class ViewHolder extends RecyclerAdapter.ViewHolder<Anime> {

            @BindView(R.id.iv_img)
            RoundImageView mImg;

            @BindView(R.id.tv_title)
            TextView mTitle;

            @BindView(R.id.tv_tag)
            TextView mTag;

            public ViewHolder(View itemView) {
                super(itemView);
            }

            @Override
            protected void onBind(Anime anime) {
                mImg.setSrc(anime.getCover_url());
                mTitle.setText(anime.getTitle());
                mTag.setText(anime.getCategory());
            }
        }
    }
}