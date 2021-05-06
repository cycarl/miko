package com.xana.acg.mikomiko.actis;
import com.google.android.material.tabs.TabLayout;
import com.xana.acg.com.app.TabViewPagerActivity;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.frags.music.NewMusicFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NewMusicMallActivity extends TabViewPagerActivity {

    private final List<String> mType = Arrays.asList("Recommend", "Mandarin Music", "Japanese Music", "Korean Music", "Western Music", "0", "7", "8", "16", "96");
    @Override
    protected void initWidget() {
        super.initWidget();
        mToolbar.setTitle("最新音乐");
        mTab.setTabMode(TabLayout.MODE_FIXED);
    }
    @Override
    protected void setTitles() {
        mTitles = Arrays.asList("推荐", "华语", "日本", "韩国", "欧美");
    }

    @Override
    protected void setFrags() {
        mFrags = new ArrayList<>();
        for (int i = 0; i < mTitles.size(); ++i) {
            mFrags.add(new NewMusicFragment(mTitles.get(i), mType.get(i), mType.get(i+mTitles.size())));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_music_list_mall;
    }
}