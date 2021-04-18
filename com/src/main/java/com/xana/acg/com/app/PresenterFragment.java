package com.xana.acg.com.app;

import android.content.Context;
import android.view.View;

import com.xana.acg.com.R;
import com.xana.acg.com.presenter.BaseContract;

import net.qiujuer.genius.ui.widget.Loading;

public abstract class PresenterFragment<T extends BaseContract.Presenter>
        extends Fragment implements BaseContract.View<T> {


    protected T mPresenter;

    protected abstract T initPresenter();

    protected Loading mLoading;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initPresenter();
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mLoading = root.findViewById(R.id.loading);
    }

    @Override
    public void showError(String msg) {
        Application.showToast(msg);
    }

    @Override
    public void showError(int strRes) {
        Application.showToast(strRes);
    }

    @Override
    public void showLoading() {
        // TODO 加载框
        if(mLoading!=null){
            mLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        if(mLoading!=null) {
            mLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMsg(int strRes) {
        Application.showToast(strRes);
    }

    @Override
    public void setPresenter(T presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null)
            mPresenter.destroy();
    }
}
