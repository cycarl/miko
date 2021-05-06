package com.xana.acg.mikomiko.actis;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xana.acg.com.app.Activity;
import com.xana.acg.mikomiko.App;
import com.xana.acg.com.app.PresenterActivity;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.model.anime.Anime;
import com.xana.acg.fac.presenter.search.AnimeSearchPresenter;
import com.xana.acg.fac.presenter.search.SearchContract;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.frags.search.SearchAnimeFragment;

import java.util.List;

import butterknife.BindView;

public class AnimeSearchActivity extends PresenterActivity<SearchContract.AnimePresenter>
        implements SearchContract.AnimeView, RecyclerAdapter.AdapterListener<Anime> {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private String keyword = "从零开始的异世界生活";
    private SearchAnimeFragment.Adapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_anime_search;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        if(bundle==null)
            return true;
        keyword = bundle.getString("key");
        return keyword!=null;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        mRecycler.setAdapter(mAdapter = new SearchAnimeFragment.Adapter() {
            @Override
            protected int getItemViewType(int p, Anime anime) {
                return p % 2 == 0 ? R.layout.item_anime_left : R.layout.item_anime_right;
            }
        });
        mToolbar.setTitle(keyword);
    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter.setListener(this);
        search(keyword);
    }

    private SearchView view;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        view = (SearchView) item.getActionView();
        if (view != null) {
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            view.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            // 添加搜索监听
            view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        App.hintKb(view);
        mPresenter.search(s);
    }

    @Override
    protected SearchContract.AnimePresenter initPresenter() {
        return new AnimeSearchPresenter(this);
    }

    @Override
    public void onLeftLoad(List<Anime> animes) {
        ok(0);
        mAdapter.replace(animes);
    }

    @Override
    public void onRightLoad(List<Anime> animes) {

    }

    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, Anime anime) {
        navTo(AnimePlayerActivity.class, "uri", anime.getUrl());
    }

    @Override
    public void onItemLongClick(RecyclerAdapter.ViewHolder holder, Anime anime) {
    }

    @Override
    public void onBackPressed() {

        View view = getWindow().peekDecorView();
        if (view != null) {
            // 隐藏虚拟键盘
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//       inputmanger.hideSoftInputFromWindow(view.getWindowToken(),0);

            if(!inputmanger.isActive() || getWindow().getCurrentFocus() == null)
                finish();
        }
        super.onBackPressed();
    }
}