package com.xana.acg.mikomiko.frags;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;;
import com.xana.acg.com.app.PresenterFragment;
import com.xana.acg.com.widget.RoundImageView;
import com.xana.acg.com.widget.recycler.Recycler;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.presenter.ImageContract;
import com.xana.acg.fac.presenter.ImagePresenter;
import com.xana.acg.fac.priavte.Account;
import com.xana.acg.mikomiko.App;
import com.xana.acg.mikomiko.IndexActivity;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.actis.ImageShowActivity;
import com.xana.acg.mikomiko.frags.dialog.SelfDialogFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ImageFragment extends PresenterFragment<ImageContract.Presenter>
        implements ImageContract.View, RecyclerAdapter.AdapterListener<String>,
        Recycler.OnMoreLoadListener, IndexActivity.OnRefreshListenter,
        SelfDialogFragment.OnHLister {

    @BindView(R.id.recycler)
    Recycler mRv;
    private boolean isH = false;
    private RecyclerAdapter<String> mAdapter;
    private IndexActivity ctx;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = (IndexActivity) context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setAdapter(mAdapter = new Adapter());
        mAdapter.setListener(this);
        mRv.setListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        ctx.refreshStart();
        get(false);
    }

    public void get(boolean isH) {
        mPresenter.getImages((int) (Math.random() * 1500) + 1, 30, isH);
    }

    @Override
    protected ImageContract.Presenter initPresenter() {
        return new ImagePresenter(this);
    }

    @Override
    public void onLoad(List<String> imgList, boolean isH) {
        ok(0);
        if (isH ^ this.isH||isRefresh) {
            mAdapter.replace(imgList);
            this.isH = isH;
        } else
            mAdapter.add(imgList);
        isRefresh = false;
        ctx.refreshEnd(getString(R.string.tip_get_img));
    }

    @Override
    protected void retry() {
        onRefresh(null);
    }

    @Override
    public void showMsg(String msg) {
        ctx.refreshEnd(msg);
        ok(mAdapter);
    }

    @OnClick(R.id.iv_trigger)
    void onFabClick(View view) {
        if(!isH&&Account.getAccess()==null){
            new SelfDialogFragment(this).show(getChildFragmentManager(), "dialog");
        }else {
            ok();
        }
    }

    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, String uri) {
        ctx.navTo(ImageShowActivity.class, "uri", uri);
    }
    @Override
    public void onItemLongClick(RecyclerAdapter.ViewHolder holder, String s) {
        App.showToast(R.string.label_downloading);
        App.download(s, s.substring(s.lastIndexOf('/', s.length()-2)+1, s.length()-2));
    }
    @Override
    public void onMoreLoad() {
        get(isH);
    }

    private boolean isRefresh;
    @Override
    public void onRefresh(IndexActivity ctx) {
        isRefresh = true;
        get(isH);
    }

    @Override
    public void ok() {
        ctx.refreshStart();
        get(!isH);
    }

    static class Adapter extends RecyclerAdapter<String> {
        @Override
        protected int getItemViewType(int index, String s) {
            return R.layout.item_index_image;
        }
        @Override
        protected RecyclerAdapter.ViewHolder<String> onCreateViewHolder(View root, int viewType) {
            return new ViewHolder(root);
        }
        class ViewHolder extends RecyclerAdapter.ViewHolder<String> {
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