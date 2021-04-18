package com.xana.acg.mikomiko;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xana.acg.com.app.Activity;
import com.xana.acg.com.app.Fragment;
import com.xana.acg.mikomiko.actis.SearchActivity;
import com.xana.acg.mikomiko.adaps.FSAdapter;
import com.xana.acg.mikomiko.frags.BlankFragment;
import com.xana.acg.mikomiko.frags.GameFragment;
import com.xana.acg.mikomiko.frags.ImageFragment;
import com.xana.acg.mikomiko.frags.MusicFragment;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class IndexActivity extends Activity {

    @BindView(R.id.vp2)
    ViewPager2 mViewPager;
    @BindView(R.id.tab)
    TabLayout mTab;

    @BindView(R.id.drawer)
    DrawerLayout mDrawer;

    private List<String> mTitle = Arrays.asList("我的", "音乐", "游戏", "动漫", "美图");
    private List<Integer> mIcon = Arrays.asList(R.drawable.ic_main_my_);
    private List<Fragment> mFragments = Arrays.asList(
            new BlankFragment(),
            new MusicFragment(),
            new GameFragment(),
            new BlankFragment(),
            new ImageFragment()
    );

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTab();
    }

    private void setTab() {
        FSAdapter fsAdapter = new FSAdapter(getSupportFragmentManager(), getLifecycle(), mFragments);
        mViewPager.setAdapter(fsAdapter);

        //TabLayout和Viewpager2进行关联
        new TabLayoutMediator(mTab, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int index) {
                tab.setText(mTitle.get(index));
            }
        }).attach();
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.iv_menu, R.id.iv_search})
    void click(View view) {
        switch (view.getId()) {
            case R.id.iv_menu:
                mDrawer.openDrawer(Gravity.START);
                break;
            case R.id.iv_search:
                navTo(SearchActivity.class);
        }
    }
}