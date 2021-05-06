package com.xana.acg.com.presenter;

import androidx.annotation.StringRes;

public interface BaseContract {
    interface View<T extends Presenter>{
        void showMsg(String msg);
        void showMsg(@StringRes int strRes);
        void setPresenter(T presenter);
        void loading();
    }

    interface Presenter{
        void start();
        void destroy();
    }
}
