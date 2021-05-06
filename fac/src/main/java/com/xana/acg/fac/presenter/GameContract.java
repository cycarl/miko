package com.xana.acg.fac.presenter;

import com.xana.acg.com.presenter.BaseContract;
import com.xana.acg.fac.model.Game;
import com.xana.acg.fac.model.api.PageResult;

import java.util.List;

public interface GameContract {
    interface Presenter extends BaseContract.Presenter{
        void get(int page, int size, boolean refresh);
        void get(String id);
    }

    interface View<T> extends BaseContract.View<Presenter>{
        void onLoad(T res);
    }
}
