package com.xana.acg.mikomiko.frags;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xana.acg.com.app.Activity;
import com.xana.acg.com.app.PresenterFragment;
import com.xana.acg.com.widget.RoundImageView;
import com.xana.acg.com.widget.recycler.Recycler;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.model.Game;
import com.xana.acg.fac.model.api.PageResult;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.presenter.GameContract;
import com.xana.acg.fac.presenter.GamePresenter;
import com.xana.acg.mikomiko.IndexActivity;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.actis.GameActivity;

import butterknife.BindView;
import butterknife.OnClick;


public class GameFragment extends PresenterFragment<GameContract.Presenter>
    implements GameContract.View<RespModel<PageResult<Game>>>,
        Recycler.OnMoreLoadListener,
        IndexActivity.OnRefreshListenter,
        RecyclerAdapter.AdapterListener<Game>{

    @BindView(R.id.recycler)
    Recycler mRv;

    private Adapter mAapter;

    @Override
    protected int getLayoutId() {
        return R.layout.view_recycler;
    }

    @Override
    protected GameContract.Presenter initPresenter() {
        return new GamePresenter(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx = (IndexActivity) context;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRv.setAdapter(mAapter = new Adapter(acti()));
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setListener(this);
    }
    @Override
    protected void initData() {
        super.initData();
        mCtx.refreshStart();

        mPresenter.get(1, 10, false);

        mAapter.setListener(this);
    }

    private PageResult<Game> res;
    @Override
    public void onLoad(RespModel<PageResult<Game>> resp) {
        ok(0);
        this.res = resp.getResult();
        if(resp.refresh)
            mAapter.replace(res.getContent());
        else
            mAapter.add(res.getContent());
        mCtx.refreshEnd(getString(R.string.tip_get_galgame));
    }


    @Override
    public void showMsg(String msg) {
        mCtx.refreshEnd(msg);
        ok(mAapter);
    }

    @Override
    public void onMoreLoad() {
        if(res==null || !res.hasMore()) {
            showMsg(R.string.tip_no_more);
            return;
        }
        mPresenter.get(res.getPageNum()+1, 10, false);
    }

    @Override
    protected void retry() {
        mCtx.refreshStart();
        mPresenter.get((int) (Math.random()*100), 10, true);
    }

    private IndexActivity mCtx;
    @Override
    public void onRefresh(IndexActivity ctx) {
        mPresenter.get((int) (Math.random()*100), 10, true);
    }

    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, Game game) {
        acti().navTo(GameActivity.class, "id", game.getId());
    }

    @Override
    public void onItemLongClick(RecyclerAdapter.ViewHolder holder, Game game) {
    }

    public static class Adapter extends RecyclerAdapter<Game>{
        private Activity mAct;
        public Adapter(Activity act){
            this.mAct = act;
        }

        @Override
        protected int getItemViewType(int position, Game game) {
            return R.layout.item_index_game;
        }

        @Override
        protected RecyclerAdapter.ViewHolder<Game> onCreateViewHolder(View root, int viewType) {
            return new ViewHolder(root);
        }

        class ViewHolder extends RecyclerAdapter.ViewHolder<Game>{

            @BindView(R.id.iv_img)
            RoundImageView mImg;
            @BindView(R.id.tv_title)
            TextView mTitle;

            public ViewHolder(View itemView) {
                super(itemView);
            }

            private String id;

            @Override
            protected void onBind(Game game) {
                mImg.setSrc(game.getPic());
                mTitle.setText(game.getTitle());
                id = game.getId();
            }

            @OnClick(R.id.iv_img)
            void click(View view){
                mAct.navTo(GameActivity.class, "id", id);
            }
        }
    }

}