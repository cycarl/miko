package com.xana.acg.mikomiko.actis;

import android.app.SearchManager;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.SearchView;

import com.google.android.material.tabs.TabLayout;
import com.xana.acg.mikomiko.App;
import com.xana.acg.com.app.TabViewPagerActivity;
import com.xana.acg.mikomiko.R;

import com.xana.acg.mikomiko.frags.search.SearchAnimeFragment;
import com.xana.acg.mikomiko.frags.search.SearchGameFragment;
import com.xana.acg.mikomiko.frags.search.SearchMusicFragment;

import java.util.Arrays;

public class SearchActivity extends TabViewPagerActivity {


    private OnSearchListener mListener;
    private String key = "异世界";

    @Override
    protected void setTitles() {
        mTitles = Arrays.asList( "动漫", "音乐", "游戏");
    }

    @Override
    protected void setFrags() {
        mFrags = Arrays.asList(
                new SearchAnimeFragment(),
                new SearchMusicFragment(),
                new SearchGameFragment()

        );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                search(key);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        if(searchView!=null){
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            // 添加搜索监听
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // 当点击了提交按钮的时候

                    key = query;
                    search(query);
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

    private void search(String key) {
        mListener = (OnSearchListener) mFrags.get(mVp2.getCurrentItem());
        mListener.search(key);
        App.hintKb(mToolbar);
    }
    public interface OnSearchListener{
        void search(String key);
    }
}