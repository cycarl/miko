package com.xana.acg.mikomiko.frags;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.xana.acg.com.app.Fragment;
import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.utils.TextUtils;
import com.xana.acg.com.widget.RoundImageView;
import com.xana.acg.com.widget.recycler.Recycler;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.model.music.MusicList;
import com.xana.acg.fac.model.music.MusicListCat;
import com.xana.acg.fac.model.music.MusicListMy;
import com.xana.acg.fac.net.NetCallBack;
import com.xana.acg.fac.net.Network;
import com.xana.acg.fac.priavte.Account;
import com.xana.acg.mikomiko.App;
import com.xana.acg.mikomiko.IndexActivity;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.actis.MusicListActivity;
import com.xana.acg.mikomiko.actis.local.DownloadActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.xana.acg.com.Common.SEVER.MUSIC;


public class MyFragment extends Fragment
    implements IndexActivity.OnRefreshListenter, NestedScrollView.OnScrollChangeListener {

    @BindView(R.id.recycler)
    Recycler mRecycler;

    @BindView(R.id.ns_page)
    NestedScrollView mPage;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    private IndexActivity mCtx;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = (IndexActivity) context;
    }

    @Override
    public void onRefresh(IndexActivity ctx) {
        get(offset=0, true);
        ctx.refreshEnd(null);
    }

    @Override
    protected void retry() {
        mCtx.refreshStart();
        // 18794806493
        // 1521838002
        onRefresh(mCtx);
    }

    private Adapter mAdapter;

    @OnClick(R.id.download_manager)
    void click(){
        mCtx.navTo(DownloadActivity.class);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRecycler.setLayoutManager(new LinearLayoutManager(mCtx));
        mRecycler.setAdapter(mAdapter = new Adapter());
        mRecycler.setNestedScrollingEnabled(false);
        mPage.setOnScrollChangeListener(this);
        mAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<MusicList>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, MusicList musicList) {
                mCtx.navTo(MusicListActivity.class, "id", musicList.getId());
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        get(0, true);
    }
    private int offset;
    private MusicListMy m;
    public void get(int offset, boolean ref){
        ok(1);
        Network.remote(MUSIC).getUserMusicList(Account.getUserId(), offset, 10).enqueue(new NetCallBack<>(call, ref));
    }
    private DataSource.Callback<MusicListMy> call = new DataSource.Callback<MusicListMy>() {
        @Override
        public void fail(String msg) {
            ok(0);
            mCtx.refreshEnd(msg);
        }

        @Override
        public void success(MusicListMy data) {
            ok(0);
            m = data;
            if(data.isRefresh()){
                mAdapter.replace(m.getPlaylist());
            }else mAdapter.add(data.getPlaylist());
        }
    };

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
            if(m.hasMore()){
                get(offset+=m.getPlaylist().size(), false);
            }else{
                App.showToast(R.string.tip_no_more);
            }
        }
    }

    static class Adapter extends RecyclerAdapter<MusicList>{
        @Override
        protected int getItemViewType(int position, MusicList musicList) {
            return R.layout.item_search_music;
        }
        @Override
        protected RecyclerAdapter.ViewHolder<MusicList> onCreateViewHolder(View root, int viewType) {
            return new ViewHolder(root);
        }

        static class ViewHolder extends RecyclerAdapter.ViewHolder<MusicList>{
            @BindView(R.id.iv_img)
            RoundImageView mImg;
            @BindView(R.id.tv_title)
            TextView mTitle;
            @BindView(R.id.tv_creater)
            TextView mCreator;
            @BindView(R.id.tv_tip)
            TextView mTip;

            public ViewHolder(View itemView) {
                super(itemView);
            }

            @Override
            protected void onBind(MusicList musicList) {
                mImg.setSrc(musicList.getCoverImgUrl());
                mTitle.setText(musicList.getName());
                mCreator.setText(String.format("%d首 by %s", musicList.getTrackCount(), musicList.getCreator().getNickname()));
                String des = musicList.getDescription();
                if(TextUtils.isEmpty(des))
                    mTip.setVisibility(View.GONE);
                else mTip.setText(des);
            }
        }
    }
}