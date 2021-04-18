package com.xana.acg.mikomiko.frags;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xana.acg.com.app.PresenterFragment;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.model.anime.Detail;
import com.xana.acg.fac.model.music.Data;
import com.xana.acg.fac.presenter.play.AnimePlayerContract;
import com.xana.acg.fac.presenter.play.AnimePlayerPresenter;
import com.xana.acg.fac.utils.TextUtils;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.actis.AnimePlayerActivity;

import java.util.List;

import butterknife.BindView;

public class DetailFragment extends PresenterFragment<AnimePlayerContract.Presenter>
        implements AnimePlayerContract.View, AnimePlayerActivity.OnFragLoadListener {

    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.tv_des)
    TextView mDes;
    @BindView(R.id.rv)
    RecyclerView mRv;
    private SrcAapter srcAapter;

    private AnimePlayerActivity mAct;

    private String uri;
    private boolean first = true;
    private EpiAdapter.EpiViewHolder curEpisode;

    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        mAct = ((AnimePlayerActivity) activity());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setAdapter(srcAapter = new SrcAapter());
        mRv.setNestedScrollingEnabled(false);
        mPresenter.get(uri);
    }

    @Override
    protected AnimePlayerContract.Presenter initPresenter() {
        return new AnimePlayerPresenter(this);
    }

    @Override
    public void onLoad(Detail detail) {

        Log.e("DetailSuccess", "" + detail);

        mTitle.setText(detail.getTitle());

        // darling in the franxx
        mDes.setText(Html.fromHtml(detail.getDescription()));
        srcAapter.add(detail.getPlay_lists());
    }



    @Override
    public void onLoad(String uri) {
        this.uri = uri;
    }



    class SrcAapter extends RecyclerAdapter<Detail.Src> {

        @Override
        protected int getItemViewType(int position, Detail.Src src) {
            return R.layout.item_anime_src;
        }

        @Override
        protected RecyclerAdapter.ViewHolder<Detail.Src> onCreateViewHolder(View root, int viewType) {
            return new SrcViewHolder(root);
        }

        class SrcViewHolder extends RecyclerAdapter.ViewHolder<Detail.Src> {

            @BindView(R.id.tv_title)
            TextView mTitle;

            @BindView(R.id.rv)
            RecyclerView mRv;


            public SrcViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            @Override
            protected void onBind(Detail.Src src) {

                mTitle.setText(String.format(getString(R.string.label_sel_src), src.getName(), src.getNum()));
                mRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
                mRv.setAdapter(new EpiAdapter(src.getVideo_list(), new AdapterListener<Detail.Eposide>() {
                    @Override
                    public void onItemClick(ViewHolder holder, Detail.Eposide eposide) {
                        if(holder==curEpisode) return;

                        mAct.setUri(eposide.getInfo()+"/url");
                        EpiAdapter.EpiViewHolder clickEposide = (EpiAdapter.EpiViewHolder) holder;
                        clickEposide.mTitle.setTextColor(getResources().getColor(R.color.colorAccent));
                        clickEposide.mBack.setBackground(getResources().getDrawable(R.drawable.shape_border_rad_5_sel));
                        if(curEpisode!=null) {
                            curEpisode.mTitle.setTextColor(getResources().getColor(R.color.black));
                            curEpisode.mBack.setBackground(getResources().getDrawable(R.drawable.shape_border_rad_5));
                        }
                        curEpisode = clickEposide;
                    }

                    @Override
                    public void onItemLongClick(ViewHolder holder, Detail.Eposide eposide) {

                    }
                }));
            }
        }
    }

    class EpiAdapter extends RecyclerAdapter<Detail.Eposide> {

        public EpiAdapter(List<Detail.Eposide> eposideList, AdapterListener<Detail.Eposide> listener) {
            super(eposideList, listener);
        }

        @Override
        protected int getItemViewType(int position, Detail.Eposide eposide) {
            return position % 2 == 0 ? R.layout.item_episode_left : R.layout.item_episode_right;
        }

        @Override
        public void onBindViewHolder(ViewHolder<Detail.Eposide> holder, int position) {
            super.onBindViewHolder(holder, position);
            if(first&&position==0) {
                getListener().onItemClick(holder, mDataList.get(0));
                curEpisode = (EpiViewHolder) holder;
                first = false;
            }
        }

        @Override
        protected ViewHolder<Detail.Eposide> onCreateViewHolder(View root, int viewType) {
            return new EpiViewHolder(root);
        }

        class EpiViewHolder extends ViewHolder<Detail.Eposide> {
            @BindView(R.id.tv_title)
            TextView mTitle;
            @BindView(R.id.fl_back)
            FrameLayout mBack;

            public EpiViewHolder(View itemView) {
                super(itemView);
            }

            @Override
            protected void onBind(Detail.Eposide eposide) {
                String name = eposide.getName();
                mTitle.setText(TextUtils.isNumeric(name)? String.format(getString(R.string.label_episode), name): name);
            }
        }
    }

}