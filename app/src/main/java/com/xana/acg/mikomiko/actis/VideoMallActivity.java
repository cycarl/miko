package com.xana.acg.mikomiko.actis;

import com.xana.acg.com.app.TabViewPagerActivity;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.frags.music.MusicListFragment;
import com.xana.acg.mikomiko.frags.music.VideoFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VideoMallActivity extends TabViewPagerActivity {
    private List<String> ids = Arrays.asList(null, "57104", "60100", "58101", "2100", "2103", "58100", "1101", "1000", "1105");
    @Override
    protected void setTitles() {
        mTitles =  Arrays.asList("推荐", "ACG音乐", "翻唱", "听BGM", "生活", "游戏", "现场", "舞蹈", "MV", "最佳饭制");
    }

    @Override
    protected void setFrags() {
        mFrags = new ArrayList<>();
        for (String id : ids) {
            mFrags.add(new VideoFragment(id));
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_music_list_mall;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mToolbar.setTitle("视频广场");
    }
}
