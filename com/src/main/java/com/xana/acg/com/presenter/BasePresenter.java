package com.xana.acg.com.presenter;

public class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter {
    private T mView;
    public BasePresenter(T view){
        setView(view);
    }

    protected void setView(T view){
        mView = view;
        mView.setPresenter(this);
    }
    protected final T getView(){
        return mView;
    }

    @Override
    public void start() {
        mView.loading();
    }
    @Override
    public void destroy() {
        if(mView!=null){
            mView.setPresenter(null);
        }
        mView = null;
    }
}
