package com.xana.acg.com.app;

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
    public void showMsg(int str) {
        Application.showToast(str);
    }

//    private ProgressDialog mProgressDialog;

    @Override
    public void setPresenter(T presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.destroy();
        mPresenter = null;
    }

    @Override
    public void showMsg(String msg) {
        Application.showToast(msg);
        ok(2);
    }

    @Override
    public void loading() {
        ok(1);
    }
}

