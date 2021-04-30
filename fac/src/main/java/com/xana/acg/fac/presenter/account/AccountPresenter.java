package com.xana.acg.fac.presenter.account;

import com.xana.acg.com.Common;
import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.R;
import com.xana.acg.fac.helper.AccountHelper;
import com.xana.acg.fac.model.account.MusicUser;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.regex.Pattern;


public class AccountPresenter extends BasePresenter<AccountContract.View>
        implements AccountContract.Presenter {
    public AccountPresenter(AccountContract.View view) {
        super(view);
    }

    @Override
    public void checkExist(String smart) {
        start();
        if (!check(smart, null, null))
            return;
        AccountHelper.checkExist(smart, new DataSource.SucceedCallback<Boolean>() {
            @Override
            public void onDataLoaded(Boolean data) {

                Run.onUiAsync(new Action() {
                    @Override
                    public void call() {
                        getView().switchFrag(data);
                    }
                });

            }
        });

    }

    /**
     * 前端验证
     */
    private boolean check(String smart, String pass, String nickname) {
        AccountContract.View view = getView();
        if (!Pattern.matches(Common.REGEX.SMART, smart)) {
            view.showError(R.string.data_invalid_smart);
            return false;
        } else if (pass != null && !Pattern.matches(Common.REGEX.PASS, pass)) {
            view.showError(R.string.data_invalid_pass);
            return false;
        } else if (nickname != null && nickname.trim().length() < 2) {
            view.showError(R.string.data_invalid_nick);
            return false;
        }
        return true;
    }

    @Override
    public void login(String smart, String pass) {
        if(!check(smart, pass, null))
            return;
        AccountHelper.login(smart, pass, new DataSource.SucceedCallback<MusicUser>() {
            @Override
            public void onDataLoaded(MusicUser data) {
                Run.onUiAsync(new Action() {
                    @Override
                    public void call() {
                        getView().onLoginSuccess(data);
                    }
                });
            }
        });
    }

    @Override
    public void sendCaptcha(String smart) {
        AccountHelper.sendCaptcha(smart, null);
    }

    @Override
    public void verifyCaptcha(String smart, String captcha) {
        AccountHelper.verifyCaptcha(smart, captcha, new DataSource.SucceedCallback() {
            @Override
            public void onDataLoaded(Object data) {
                getView().onLoginSuccess(null);
            }
        });

    }

    @Override
    public void checkStatus() {

    }

    @Override
    public void register(String smart, String pass, String nickname, String captcha) {

    }

}
