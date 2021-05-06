package com.xana.acg.mikomiko.frags;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xana.acg.com.app.PresenterFragment;
import com.xana.acg.com.widget.RoundImageView;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.model.anime.Bangumi;
import com.xana.acg.fac.presenter.AnimeContract;
import com.xana.acg.fac.presenter.AnimePresenter;
import com.xana.acg.mikomiko.IndexActivity;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.actis.AnimePlayerActivity;
import com.xana.acg.mikomiko.actis.AnimeSearchActivity;

import java.util.SortedSet;

import butterknife.BindView;
import butterknife.OnClick;

public class AnimeFragment extends PresenterFragment<AnimeContract.Presenter>
    implements AnimeContract.View, IndexActivity.OnRefreshListenter, RecyclerAdapter.AdapterListener<Bangumi.Update> {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private IndexActivity mCtx;
    private Adapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = (IndexActivity) context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_anime;
    }

    @OnClick(R.id.iv_trigger)
    void click(View view){
        mCtx.navTo(AnimePlayerActivity.class, "uri", null);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRecycler.setLayoutManager(new GridLayoutManager(mCtx, 2));
        mRecycler.setAdapter(mAdapter = new Adapter());
        mAdapter.setListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mCtx.refreshStart();
        mPresenter.getAnimes();
    }
    @Override
    protected AnimeContract.Presenter initPresenter() {
        return new AnimePresenter(this);
    }

    @Override
    public void onLoad(SortedSet<Bangumi> bangumis) {
        ok(0);
        mCtx.refreshEnd(getString(R.string.tip_get_anime));
        new Thread(){
            @Override
            public void run(){
                for (Bangumi bangumi : bangumis) {
                    mCtx.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.add(bangumi.getUpdates());
                        }
                    });
                }
            }
        }.start();
    }

    @Override
    public void showMsg(String msg) {
        mCtx.refreshEnd(msg);
        ok(mAdapter);
    }

    @Override
    public void onRefresh(IndexActivity ctx) {
        mCtx.refreshEnd(null);
    }

    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, Bangumi.Update update) {
        mCtx.navTo(AnimeSearchActivity.class, "key", update.getTitle());
    }

    @Override
    public void onItemLongClick(RecyclerAdapter.ViewHolder holder, Bangumi.Update update) {

    }

    static class Adapter extends RecyclerAdapter<Bangumi.Update>{

        @Override
        protected int getItemViewType(int position, Bangumi.Update update) {
            return R.layout.item_index_anime;
        }

        @Override
        protected ViewHolder<Bangumi.Update> onCreateViewHolder(View root, int viewType) {
            return new AnimeFragment.ViewHolder(root);
        }
    }

    static class ViewHolder extends RecyclerAdapter.ViewHolder<Bangumi.Update> {

        @BindView(R.id.iv_img)
        RoundImageView mImg;
        @BindView(R.id.tv_title)
        TextView mTitle;
        @BindView(R.id.tv_update)
        TextView mUpdate;
        @BindView(R.id.tv_time)
        TextView mTime;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Bangumi.Update update) {
            mImg.setSrc(update.getCover_url());
            mTitle.setText(update.getTitle());
            String upt = update.getUpdate_to();
            mUpdate.setText(upt.contains("全")? upt: "更新至 "+upt);
            mTime.setText(update.getUpdate_time());
        }
    }
}