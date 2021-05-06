package com.xana.acg.fac.presenter;

import com.xana.acg.com.presenter.BaseContract;

public interface IView<T> {
    void onLoad(T res);
    void onFail(String msg);
}
