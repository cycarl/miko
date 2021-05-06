package com.xana.acg.mikomiko.actis;

import android.app.SearchManager;
import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.xana.acg.mikomiko.App;
import com.xana.acg.com.app.PresenterActivity;
import com.xana.acg.com.widget.recycler.Recycler;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.model.Game;
import com.xana.acg.fac.model.api.PageResult;
import com.xana.acg.fac.presenter.search.SearchContract;
import com.xana.acg.fac.presenter.search.SearchGamePresenter;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.frags.GameFragment;

import java.util.List;

import butterknife.BindView;

public class GameSearchActivity extends PresenterActivity<SearchContract.GamePresenter>
    implements SearchContract.GameView,
        RecyclerAdapter.AdapterListener<Game>,
        Recycler.OnMoreLoadListener{


    private String keyword = "美少女";
    private int page = 1;
    private boolean hasMore;

    @BindView(R.id.recycler)
    Recycler mRecycler;

    private GameFragment.Adapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_search;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter = new GameFragment.Adapter(this));
        mRecycler.setListener(this);
        mAdapter.setListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.search(keyword, 1, true);
    }

    @Override
    protected SearchContract.GamePresenter initPresenter() {
        return new SearchGamePresenter(this);
    }

    @Override
    public void onLoad(PageResult<Game> res, boolean rf) {
        ok(0);
        List<Game> games = res.getContent();
        if(rf) mAdapter.replace(games);
        else mAdapter.add(games);
        hasMore = res.hasMore();
    }


    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, Game game) {
        navTo(GameActivity.class, "id", game.getId());
    }

    @Override
    public void onItemLongClick(RecyclerAdapter.ViewHolder holder, Game game) {
    }

    @Override
    public void onMoreLoad() {
        if(!hasMore) {
            showMsg(R.string.tip_no_more);
            return;
        }
        mPresenter.search(keyword, ++page, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        if (searchView != null) {
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            // 添加搜索监听
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    // 当点击了提交按钮的时候
                    keyword = s;
                    search(s);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    // 当文件改变时候，不会即使搜索，只在为null的情况下进行搜索
                    return true;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void search(String s) {
        mPresenter.search(keyword=s, page = 1, true);
        App.hintKb(mToolbar);
    }
}