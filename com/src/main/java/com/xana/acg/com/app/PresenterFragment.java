package com.xana.acg.com.app;
import android.content.Context;
import com.xana.acg.com.presenter.BaseContract;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;

import static com.xana.acg.com.app.Application.showToast;


public abstract class PresenterFragment<T extends BaseContract.Presenter>
        extends Fragment implements BaseContract.View<T> {

    protected T mPresenter;
    protected abstract T initPresenter();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initPresenter();
    }

    @Override
    public void showMsg(String msg) {
        // 获取数据空
        if(msg==null){
            ok(3);
            return;
        }
        showToast(msg);
        ok(2);
    }
    @Override
    public void showMsg(int strRes) {
        showToast(strRes);
    }

    public void ok(RecyclerAdapter adap) {
        if(adap.getItemCount()==0) ok(2);
        else ok(0);
    }

    @Override
    public void setPresenter(T presenter) {
        mPresenter = presenter;
    }

    @Override
    public void loading() {
        ok(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null)
            mPresenter.destroy();
        mPresenter = null;
    }

    @Override
    public void empty(boolean ept) {

    }
}
