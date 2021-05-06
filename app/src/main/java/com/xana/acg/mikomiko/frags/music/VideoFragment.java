package com.xana.acg.mikomiko.frags.music;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xana.acg.com.app.Fragment;
import com.xana.acg.com.app.PresenterFragment;
import com.xana.acg.com.widget.recycler.Recycler;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.model.music.Data;
import com.xana.acg.fac.presenter.music.MusicListContract;
import com.xana.acg.fac.presenter.music.VideoPresenter;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.frags.MusicFragment;

import java.util.List;

import butterknife.BindView;

public class VideoFragment extends PresenterFragment<MusicListContract.VdPresenter>
    implements MusicListContract.VdView, Recycler.OnMoreLoadListener, RecyclerAdapter.AdapterListener<Data> {

    @BindView(R.id.recycler)
    Recycler mRecycler;

    private MusicFragment.DataAdapter mAdapter;

    private String id;
    private int offset;
    private boolean hasMore = true;

    public VideoFragment(String ids){
        id = ids;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_recycler;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setListener(this);
        mRecycler.setAdapter(mAdapter = new MusicFragment.DataAdapter());
        mAdapter.setListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getVideos(id, offset);
    }

    @Override
    protected MusicListContract.VdPresenter initPresenter() {
        return new VideoPresenter(this);
    }


    @Override
    public void onLoad(RespModel<List<Data>> resp) {
        hasMore = resp.hasMore();
        mAdapter.add(resp.getDatas());
    }

    @Override
    public void onMoreLoad() {
        mPresenter.getVideos(id, ++offset);
    }

    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, Data data) {

    }

    @Override
    public void onItemLongClick(RecyclerAdapter.ViewHolder holder, Data data) {

    }
}
