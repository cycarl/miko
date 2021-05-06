package com.xana.acg.mikomiko.actis;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xana.acg.com.app.Fragment;
import com.xana.acg.com.app.PresenterActivity;
import com.xana.acg.com.app.ToolbarActivity;
import com.xana.acg.com.widget.RoundImageView;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.model.Game;
import com.xana.acg.fac.presenter.GameContract;
import com.xana.acg.fac.presenter.GamePresenter;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.frags.comment.CommentFragment;

import butterknife.BindView;

public class GameActivity extends PresenterActivity<GameContract.Presenter>
    implements GameContract.View<Game>, RecyclerAdapter.AdapterListener<String>{

    @BindView(R.id.rv_images)
    RecyclerView mImages;

    @BindView(R.id.tv_des)
    TextView mDes;

    @BindView(R.id.tv_info)
    TextView mInfo;

    @BindView(R.id.tv_tag)
    TextView mTag;

    @BindView(R.id.tv_title)
    TextView mTitle;
    // 游戏id
    private String id;

    private Adapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        String id = bundle.getString("id");
        return (this.id = id)!=null;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mImages.setLayoutManager(new LinearLayoutManager(this));
        mImages.setNestedScrollingEnabled(false);
        mImages.setAdapter(mAdapter = new Adapter());
        mAdapter.setListener(this);
        setCommentArea();
    }

    private void setCommentArea() {
        getSupportFragmentManager()
                .beginTransaction()
                .add( R.id.container, new CommentFragment(id))
                .commit();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.get(id);
    }

    @Override
    protected GameContract.Presenter initPresenter() {
        return new GamePresenter<>(this);
    }


    @Override
    public void onLoad(Game res) {
        mTitle.setText(res.getTitle());
        mAdapter.add(res.getImgList());
        mTag.setText(res.getTags());
        mDes.setText(Html.fromHtml(res.getDes1()));
        mInfo.setText(Html.fromHtml(res.getDes2()));
    }

    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, String s) {
        navTo(ImageShowActivity.class, "uri", s);
    }

    @Override
    public void onItemLongClick(RecyclerAdapter.ViewHolder holder, String s) {

    }


    static class Adapter extends RecyclerAdapter<String>{

        @Override
        protected int getItemViewType(int position, String s) {
            return R.layout.item_image;
        }

        @Override
        protected RecyclerAdapter.ViewHolder<String> onCreateViewHolder(View root, int viewType) {
            return new ViewHolder(root);
        }


        class ViewHolder extends RecyclerAdapter.ViewHolder<String>{

            @BindView(R.id.iv_img)
            RoundImageView mImg;

            public ViewHolder(View itemView) {
                super(itemView);
            }

            @Override
            protected void onBind(String s) {
                mImg.setSrc(s);
            }
        }
    }
}