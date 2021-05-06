package com.xana.acg.fac.presenter.account;

import com.xana.acg.com.app.Fragment;
import com.xana.acg.com.presenter.BaseContract;
import com.xana.acg.fac.model.account.MusicUser;
import com.xana.acg.fac.model.music.Data;

public interface AccountContract {
    interface Presenter extends BaseContract.Presenter{
        void checkExist(String smart);
        void login(String smart, String pass);
        void sendCaptcha(String smart);
        void verifyCaptcha(String smart, String captcha);
        // 登录状态
        void checkStatus();
        // 注册或更改密码密码
        void register(String smart, String pass, String nickname, String captcha);
    }

    interface View extends BaseContract.View<Presenter>{
        void checkExistOk (String smart, String nick);
        void loginOk(MusicUser extra);
        void verifyOk(String code);
    }
}
