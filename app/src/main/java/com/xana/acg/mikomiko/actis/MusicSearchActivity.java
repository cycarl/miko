package com.xana.acg.mikomiko.actis;

import android.app.SearchManager;
import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;

import com.google.android.material.tabs.TabLayout;
import com.xana.acg.mikomiko.App;
import com.xana.acg.com.app.TabViewPagerActivity;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.frags.search.MusicSearchFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class MusicSearchActivity extends TabViewPagerActivity {


    private String key = "从零开始的异世界生活";
    private OnSearchListener mListener;
    @Override
    protected void setTitles() {
        mTitles = Arrays.asList("综合", "单曲", "歌单", "MV", "视频", "专辑", "用户");
    }
    private final int[] mType = {0, 1, 1000, 1004, 1014, 10, 1002};

    @Override
    protected void setFrags() {
        mFrags = new ArrayList<>();
        for (int type : mType) {
            mFrags.add(new MusicSearchFragment<>(type));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_music_search;
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        if(searchView!=null){
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            // 添加搜索监听
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    // 当点击了提交按钮的时候
                    key = s;
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

    private void search(String key){
        mListener = (OnSearchListener) mFrags.get(mVp2.getCurrentItem());
        mListener.onSearch(key);
        App.hintKb(mVp2);
    }

    public interface OnSearchListener{
        void onSearch(String key);
    }
}