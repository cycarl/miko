package com.xana.acg.mikomiko.frags;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;;
import com.xana.acg.com.app.PresenterFragment;
import com.xana.acg.com.widget.RoundImageView;
import com.xana.acg.com.widget.recycler.Recycler;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.presenter.ImageContract;
import com.xana.acg.fac.presenter.ImagePresenter;
import com.xana.acg.mikomiko.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ImageFragment extends PresenterFragment<ImageContract.Presenter>
    implements ImageContract.View, RecyclerAdapter.AdapterListener<String>, Recycler.OnMoreLoadListener {

    @BindView(R.id.rv)
    Recycler mRv;

    private boolean isH = false;

    private RecyclerAdapter<String> mAdapter;

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
        get(false);
    }

    private void get(boolean isH){
        mPresenter.get((int) (Math.random()*1500)+1, 30, isH);
    }

    @Override
    protected ImageContract.Presenter initPresenter() {
        return new ImagePresenter(this);
    }

    @Override
    public void onLoad(List<String> imgList, boolean isH) {
        Log.e("imgList", imgList.toString());
        if(isH^this.isH) {
            mAdapter.replace(imgList);
            this.isH = isH;
        }
        else
            mAdapter.add(imgList);
    }

    @OnClick(R.id.iv_trigger)
    void onFabClick(View view){
        get(!isH);
    }



    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, String s) {
        showError(s);
    }

    @Override
    public void onItemLongClick(RecyclerAdapter.ViewHolder holder, String s) {
    }

    @Override
    public void onMoreLoad() {
      get(isH);
    }

    static class Adapter extends RecyclerAdapter<String>{


        @Override
        protected int getItemViewType(int index, String s) {
            return R.layout.item_index_image;
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