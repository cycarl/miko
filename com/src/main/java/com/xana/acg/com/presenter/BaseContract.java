package com.xana.acg.com.presenter;


import androidx.annotation.StringRes;

public interface BaseContract {
    interface View<T extends Presenter>{
        void showError(String msg);
        void showError(@StringRes int strRes);
        void showMsg(@StringRes int strRes);
        void showLoading();
        void setPresenter(T presenter);
        void hideLoading();
    }

    interface Presenter{
        void start();
        void ok();
        void destroy();
    }

    interface VerifyPresenter extends Presenter{
        // 发送验证码
        void send(String smart);
        // 验证验证码
        void verify(String smart, String captcha);
    }
}
