package com.xana.acg.com.app;

import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.xana.acg.com.presenter.BaseContract;


public abstract class PresenterActivity<T extends BaseContract.Presenter>
        extends ToolbarActivity
        implements BaseContract.View<T> {

    protected T mPresenter;

    protected abstract T initPresenter();

    @Override
    protected void init() {
        super.init();
        initPresenter();
    }

    @Override
    public void showError(int str) {
        Application.showToast(str);
    }

//    private ProgressDialog mProgressDialog;

    @Override
    public void showLoading() {
        // TODO 加载框
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setPresenter(T presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.destroy();
    }

    @Override
    public void showError(String msg) {
        Application.showToast(msg);
    }

    @Override
    public void showMsg(int strRes) {

    }
}

