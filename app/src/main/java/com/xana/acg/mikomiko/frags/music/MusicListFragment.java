package com.xana.acg.mikomiko.frags.music;

import android.view.View;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xana.acg.com.app.PresenterFragment;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.model.music.MusicList;
import com.xana.acg.fac.model.music.MusicListCat;
import com.xana.acg.fac.presenter.music.MusicListCatPresenter;
import com.xana.acg.fac.presenter.music.MusicListContract;
import com.xana.acg.mikomiko.App;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.actis.MusicListActivity;
import com.xana.acg.mikomiko.frags.MusicFragment;

import butterknife.BindView;

public class MusicListFragment extends PresenterFragment<MusicListContract.CatPresenter>
    implements MusicListContract.CatView,
        NestedScrollView.OnScrollChangeListener,
        RecyclerAdapter.AdapterListener<MusicList> {

    private String cat;
    public MusicListFragment(String cat){
        this.cat = cat;
        if(this.cat.equals("推荐")) this.cat = "";
    }

    @BindView(R.id.ns_page)
    NestedScrollView mPage;

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private MusicFragment.MusicListAdapter mAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_music_list;
    }

    @Override
    protected MusicListContract.CatPresenter initPresenter() {
        return new MusicListCatPresenter(this);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRecycler.setNestedScrollingEnabled(false);
        mRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecycler.setAdapter(mAdapter = new MusicFragment.MusicListAdapter());
        mAdapter.setListener(this);
        mPage.setOnScrollChangeListener(this);
    }

    private int offset = 0;

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getMusicLists(cat, offset, "hot", false);
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if(scrollY==v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight()){
            if(!hasMore) {
                showMsg(R.string.tip_no_more);
                return;
            }
            mPresenter.getMusicLists(cat, offset+=30, "hot", false);
        }
    }

    private boolean hasMore = true;


    @Override
    public void onSuccess(MusicListCat musicListCat) {
        hasMore = musicListCat.hasMore();
        if(musicListCat.isRefresh()){
            mAdapter.replace(musicListCat.getPlaylists());
        }else {
            mAdapter.add(musicListCat.getPlaylists());
        }
        ok(0);
    }

    @Override
    public void showMsg(String msg) {
       ok(mAdapter);
       App.showToast(msg);
    }

    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, MusicList musicList) {
        acti().navTo(MusicListActivity.class, "id", musicList.getId());
    }

    @Override
    public void onItemLongClick(RecyclerAdapter.ViewHolder holder, MusicList musicList) {

    }
}