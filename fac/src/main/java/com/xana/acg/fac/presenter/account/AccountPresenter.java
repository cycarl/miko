package com.xana.acg.fac.presenter.account;

import com.xana.acg.com.Common;
import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.R;
import com.xana.acg.fac.helper.AccountHelper;
import com.xana.acg.fac.model.TbUser;
import com.xana.acg.fac.model.account.MusicUser;
import com.xana.acg.fac.model.account.RegisterStatus;
import com.xana.acg.fac.net.NetCallBack;
import com.xana.acg.fac.net.Network;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import static com.xana.acg.com.Common.SEVER.SELF;
import static com.xana.acg.com.app.Application.showToast;


public class AccountPresenter extends BasePresenter<AccountContract.View>
        implements AccountContract.Presenter {
    public AccountPresenter(AccountContract.View view) {
        super(view);
    }

    @Override
    public void checkExist(String smart) {
        if (!check(smart, null, null)) return;
        start();
        AccountHelper.checkExist(smart, new DataSource.Callback<RegisterStatus>() {
            @Override
            public void fail(String msg) {
                getView().showMsg(msg);
            }

            @Override
            public void success(RegisterStatus sta) {
                getView().checkExistOk(smart, sta.getNickname());
            }
        });
    }

    /**
     * 前端验证
     */
    public static boolean check(String smart, String pass, String nickname) {
        if (smart != null &&!smart.matches(Common.REGEX.SMART)) {
            showToast(R.string.data_invalid_smart);
            return false;
        } else if (pass != null && !pass.matches(Common.REGEX.PASS)) {
            showToast(R.string.data_invalid_pass);
            return false;
        } else if (nickname != null && nickname.trim().length() < 2) {
            showToast(R.string.data_invalid_nick);
            return false;
        }
        return true;
    }

    @Override
    public void login(String smart, String pass) {
        if (!check(smart, pass, null)) return;
        start();
        AccountHelper.login(smart, pass, new LoginCallback(pass));
    }

    @Override
    public void sendCaptcha(String smart) {
        AccountHelper.sendCaptcha(smart, new DataSource.Callback() {
            @Override
            public void fail(String msg) {
                getView().showMsg(msg);
            }

            @Override
            public void success(Object data) {
            }
        });
    }

    @Override
    public void verifyCaptcha(String smart, String captcha) {
        AccountHelper.verifyCaptcha(smart, captcha, new DataSource.Callback() {
            @Override
            public void fail(String msg) {
                getView().showMsg(msg);
            }

            @Override
            public void success(Object data) {
                getView().verifyOk(captcha);
            }
        });
    }

    @Override
    public void checkStatus() {

    }

    @Override
    public void register(String smart, String pass, String nickname, String captcha) {
        if (!check(smart, pass, nickname)) return;
        start();
        AccountHelper.register(smart, pass, nickname, captcha, new LoginCallback(pass));
    }


    private class LoginCallback implements DataSource.Callback<MusicUser> {

        String p;

        public LoginCallback(String pass) {
            p = pass;
        }

        @Override
        public void success(MusicUser d) {
            TbUser user = new TbUser(d.getId(), d.getProfile().nickname, p, d.getProfile().avatarUrl);
            Network.remote(SELF).login(user).enqueue(new NetCallBack<>());
            getView().loginOk(d);
        }

        @Override
        public void fail(String msg) {
            getView().showMsg(msg);
        }
    }
}
