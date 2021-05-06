package com.xana.acg.com.app;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xana.acg.com.R;

import java.util.List;

public abstract class TabViewPagerActivity extends ToolbarActivity
    implements TabLayoutMediator.TabConfigurationStrategy{


    protected ViewPager2 mVp2;
    protected List<Fragment> mFrags;
    protected TabLayout mTab;
    protected List<String> mTitles;

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitles();
        setFrags();
        setTab();
    }

    protected abstract void setTitles();
    protected abstract void setFrags();


    private void setTab() {

        mVp2 = findViewById(R.id.vp2);
        if(mVp2 ==null) return;
        mTab = findViewById(R.id.tab);

        if(mTab==null) return;
        FSAdapter fsAdapter = new FSAdapter(getSupportFragmentManager(), getLifecycle(), mFrags);
        mVp2.setAdapter(fsAdapter);
        new TabLayoutMediator(mTab, mVp2, this).attach();
    }

    public void onConfigureTab(@NonNull TabLayout.Tab tab, int index) {
        tab.setText(mTitles.get(index));
    }
}
