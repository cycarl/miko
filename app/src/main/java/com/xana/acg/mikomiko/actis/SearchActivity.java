package com.xana.acg.mikomiko.actis;

import android.app.SearchManager;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xana.acg.com.app.Fragment;
import com.xana.acg.com.app.ToolbarActivity;
import com.xana.acg.fac.utils.TextUtils;
import com.xana.acg.mikomiko.R;

import com.xana.acg.mikomiko.adaps.FSAdapter;
import com.xana.acg.mikomiko.frags.search.SearchAnimeFragment;
import com.xana.acg.mikomiko.frags.search.SearchGameFragment;
import com.xana.acg.mikomiko.frags.search.SearchMusicFragment;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class SearchActivity extends ToolbarActivity {

    @BindView(R.id.vp2)
    ViewPager2 mVp2;
    @BindView(R.id.tab)
    TabLayout mTab;

    private OnSearchListener mListener;
    private List<String> mTitle = Arrays.asList("音乐", "游戏", "动漫");
    private List<Integer> mIcon = Arrays.asList(R.drawable.ic_main_my_);
    private List<Fragment> mFragments = Arrays.asList(
            new SearchMusicFragment(),
            new SearchGameFragment(),
            new SearchAnimeFragment()
    );

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTab();

        mVp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
               search(key);
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
                    key = s;
                    if(TextUtils.isEmpty(s)){
                        search("从零开始的异世界生活");
                        return true;
                    }
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }
    private String key;

    private void search(String key) {
        mListener = (OnSearchListener) mFragments.get(mVp2.getCurrentItem());
        mListener.search(key);
    }

    private void setTab() {
        FSAdapter fsAdapter = new FSAdapter(getSupportFragmentManager(), getLifecycle(), mFragments);
        mVp2.setAdapter(fsAdapter);
        //TabLayout和Viewpager2进行关联
        new TabLayoutMediator(mTab, mVp2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int index) {
                tab.setText(mTitle.get(index));
            }
        }).attach();
    }

    public interface OnSearchListener{
        void search(String key);
    }
}