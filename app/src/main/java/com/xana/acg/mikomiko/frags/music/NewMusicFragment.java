package com.xana.acg.mikomiko.frags.music;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xana.acg.com.app.Fragment;
import com.xana.acg.com.app.PresenterFragment;
import com.xana.acg.com.widget.RoundImageView;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.model.music.NewMusicCat;
import com.xana.acg.fac.presenter.music.NewMusicContract;
import com.xana.acg.fac.presenter.music.NewMusicPresenter;
import com.xana.acg.mikomiko.R;

import butterknife.BindView;

public class NewMusicFragment extends PresenterFragment<NewMusicContract.CatPresenter>
    implements NewMusicContract.CatView, RecyclerAdapter.AdapterListener<NewMusicCat.Data> {

    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.tv_title_alpha)
    TextView mAlpha;

    @BindView(R.id.tv_total_music)
    TextView mMusics;

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private Adapter mAdapter;

    private String title;
    private String alpha;
    private String type;
    public NewMusicFragment(String title, String alpha, String type){
        this.title = title;
        this.alpha = alpha;
        this.type = type;
    }

    @Override
    protected NewMusicContract.CatPresenter initPresenter() {
        return new NewMusicPresenter(this);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mTitle.setText(title);
        mAlpha.setText(alpha);
        mRecycler.setNestedScrollingEnabled(false);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter = new Adapter());
        mAdapter.setListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getNewMusicCat(type);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_music;
    }

    @Override
    public void onSuccess(NewMusicCat newMusicCat) {
        mMusics.setText(String.format(getString(R.string.label_total_music), newMusicCat.getData().size()));
        mAdapter.add(newMusicCat.getData());
        ok(0);
    }



    private ViewHolder cur;
    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, NewMusicCat.Data data) {
        if(holder==cur) return;
        if(cur!=null) {
            cur.mImg.setVisibility(View.VISIBLE);
            cur.mPlaying.setVisibility(View.GONE);
        }
        cur = (ViewHolder) holder;
        cur.mImg.setVisibility(View.GONE);
        cur.mPlaying.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemLongClick(RecyclerAdapter.ViewHolder holder, NewMusicCat.Data data) {
    }

    public static class Adapter extends RecyclerAdapter<NewMusicCat.Data>{

        @Override
        protected int getItemViewType(int position, NewMusicCat.Data data) {
            return R.layout.item_new_music;
        }

        @Override
        protected ViewHolder<NewMusicCat.Data> onCreateViewHolder(View root, int viewType) {
            return new NewMusicFragment.ViewHolder(root);
        }
    }

    static class ViewHolder extends RecyclerAdapter.ViewHolder<NewMusicCat.Data> {

        @BindView(R.id.iv_img)
        RoundImageView mImg;

        @BindView(R.id.iv_playing)
        ImageView mPlaying;

        @BindView(R.id.tv_title)
        TextView mTitle;

        @BindView(R.id.tv_title_des)
        TextView mTitleDes;


        @BindView(R.id.tv_creater)
        TextView mCreater;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(NewMusicCat.Data data) {
            mImg.setSrc(data.getAlbum().getPicUrl());
            mTitle.setText(data.getName());
            mTitleDes.setText(data.getAlias().size() > 0 ? "(" + data.getAlias().get(0) + ")" : "");
            mCreater.setText((data.getArtists().size()>0?data.getArtists().get(0).getName():"") +" - "+data.getName());
        }
    }
}