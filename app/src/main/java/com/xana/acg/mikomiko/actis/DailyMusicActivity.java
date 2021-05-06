package com.xana.acg.mikomiko.actis;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xana.acg.Factory;
import com.xana.acg.mikomiko.App;
import com.xana.acg.com.app.PresenterActivity;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.model.music.Music;
import com.xana.acg.fac.presenter.music.DailyMusicContract;
import com.xana.acg.fac.presenter.music.DailyMusicPresenter;
import com.xana.acg.com.utils.TextUtils;
import com.xana.acg.mikomiko.R;

import java.util.List;


import butterknife.BindView;

public class DailyMusicActivity extends PresenterActivity<DailyMusicContract.Presenter>
     implements DailyMusicContract.View, RecyclerAdapter.AdapterListener<Music> {

    @BindView(R.id.tv_date)
    TextView mDate;

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @BindView(R.id.tv_total_music)
    TextView mMusics;

    private MusicListActivity.Adapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_daily_music;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mDate.setText(TextUtils.dayForWeek(App.getDate()));
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter = new MusicListActivity.Adapter(R.layout.item_new_music));
        mAdapter.setListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getDailyMusic();
    }

    @Override
    protected DailyMusicContract.Presenter initPresenter() {
        return new DailyMusicPresenter(this);
    }

    @Override
    public void onLoad(List<Music> musics) {
        mAdapter.add(musics);
        mMusics.setText(String.format(getString(R.string.label_total_music), musics.size()));
        ok(0);
    }

    private MusicListActivity.ViewHolder curHolder;

    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, Music music) {
        if (curHolder == holder) return;
        if (curHolder != null) {
            curHolder.mImg.setVisibility(View.VISIBLE);
            curHolder.mPlaying.setVisibility(View.GONE);
        }
        curHolder = (MusicListActivity.ViewHolder) holder;
        curHolder.mImg.setVisibility(View.GONE);
        curHolder.mPlaying.setVisibility(View.VISIBLE);
        navTo(MusicPlayerActivity.class, "music", Factory.getGson().toJson(music));
    }

    @Override
    public void onItemLongClick(RecyclerAdapter.ViewHolder holder, Music music) {

    }
}