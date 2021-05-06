package com.xana.acg.mikomiko.actis;
import com.xana.acg.com.app.TabViewPagerActivity;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.frags.music.MusicListFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class MusicListMallActivity extends TabViewPagerActivity {

    @Override
    protected void setTitles() {
        mTitles =  Arrays.asList("推荐", "ACG", "古风", "华语", "日语", "摇滚", "民谣", "电子");
    }
    @Override
    protected void setFrags() {
        mFrags = new ArrayList<>();
        for (String cat : mTitles) {
            mFrags.add(new MusicListFragment(cat));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_music_list_mall;
    }
}