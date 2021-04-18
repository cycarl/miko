package com.xana.acg.mikomiko.frags.search;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.xana.acg.com.app.PresenterFragment;
import com.xana.acg.com.widget.recycler.Recycler;
import com.xana.acg.fac.model.Game;
import com.xana.acg.fac.model.api.PageResult;
import com.xana.acg.fac.presenter.search.SearchContract;
import com.xana.acg.fac.presenter.search.SearchGamePresenter;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.actis.SearchActivity;
import com.xana.acg.mikomiko.frags.GameFragment;

import butterknife.BindView;

public class SearchGameFragment extends PresenterFragment<SearchContract.GamePresenter>
implements SearchContract.GameView, Recycler.OnMoreLoadListener, SearchActivity.OnSearchListener {

    @BindView(R.id.rv)
    Recycler mRv;

    private int page = 0;
    private GameFragment.Adapter mAdapter;
    private boolean hasMore;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_game;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setAdapter(mAdapter = new GameFragment.Adapter(activity()));
        mRv.setListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onMoreLoad() {
        if(!hasMore){
            showError("我是有底线的~~");
            return;
        }
        isSearch = false;
        mPresenter.search(key, ++page);
    }

    @Override
    protected SearchContract.GamePresenter initPresenter() {
        return new SearchGamePresenter(this);
    }


    @Override
    public void onLoad(PageResult<Game> res) {
        Log.e("gameRes", res.toString());
        hasMore = res.hasMore();
        if(isSearch)
            mAdapter.replace(res.getContent());
        else mAdapter.add(res.getContent());
    }

    private String key;
    private boolean isSearch;

    @Override
    public void search(String key) {
        this.key = key;
        isSearch = true;
        mPresenter.search(key, page);
    }
}