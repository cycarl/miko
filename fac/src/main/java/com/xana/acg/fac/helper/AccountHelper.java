package com.xana.acg.fac.helper;

import com.xana.acg.com.app.Application;
import com.xana.acg.com.data.DataSource;
import com.xana.acg.fac.R;
import com.xana.acg.fac.model.account.MusicUser;
import com.xana.acg.fac.model.account.RegisterStatus;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.net.NetCallBack;
import com.xana.acg.fac.net.Network;
import com.xana.acg.fac.presenter.account.AccountPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.xana.acg.com.Common.SEVER.MUSIC;


public class AccountHelper {
    public static void checkExist(String smart, DataSource.Callback<RegisterStatus> callback) {
        Network.remote(MUSIC).checkExist(smart).enqueue(new NetCallBack<RegisterStatus>(callback));
    }

    public static void login(String smart, String pass, DataSource.Callback<MusicUser> callback) {
        Network.remote(MUSIC).login(smart, pass).enqueue(new NetCallBack<>(callback));
    }

    public static void sendCaptcha(String smart, DataSource.Callback callback) {
        Network.remote(MUSIC).sendCaptcha(smart).enqueue(new NetCallBack<RespModel>(callback));
    }

    public static void verifyCaptcha(String smart, String captcha, DataSource.Callback callback) {
        Network.remote(MUSIC).verifyCaptcha(smart, captcha).enqueue(new NetCallBack<RespModel>(callback));
    }

    public static void register(String smart, String pass, String nickname, String captcha, DataSource.Callback<MusicUser> cb) {
        Network.remote(MUSIC).register(smart, pass, nickname, captcha).enqueue(new NetCallBack<>(cb));
    }
}

