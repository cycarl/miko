package com.xana.acg.mikomiko.frags;

import android.content.Context;
import android.util.Log;
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
import com.xana.acg.fac.presenter.GameContract;
import com.xana.acg.fac.presenter.GamePresenter;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.actis.GameActivity;

import butterknife.BindView;
import butterknife.OnClick;


public class GameFragment extends PresenterFragment<GameContract.Presenter>
    implements GameContract.View<PageResult<Game>>, Recycler.OnMoreLoadListener {

    @BindView(R.id.rv)
    Recycler mRv;

    private Adapter mAapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game;
    }


    @Override
    protected GameContract.Presenter initPresenter() {
        return new GamePresenter(this);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRv.setAdapter(mAapter = new Adapter(activity()));
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setListener(this);
    }

    @Override
    protected void initData() {
        super.initData();

        mPresenter.get(1, 10);

        mAapter.setListener(new RecyclerAdapter.AdapterListener<Game>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Game game) {
                activity().navTo(GameActivity.class, "id", game.getId());
            }

            @Override
            public void onItemLongClick(RecyclerAdapter.ViewHolder holder, Game game) {

            }
        });
    }


    private PageResult<Game> res;

    @Override
    public void onLoad(PageResult<Game> res) {
        this.res = res;

        mAapter.add(res.getContent());
        Log.e("game", res.toString());
    }

    @Override
    public void onMoreLoad() {
        if(res==null || !res.hasMore()) {
            showError("我是有底线的~~");
            return;
        }

        mPresenter.get(res.getPageNum()+1, 10);
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