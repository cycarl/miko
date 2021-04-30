package com.xana.acg.fac.helper;

import com.xana.acg.com.app.App;
import com.xana.acg.com.app.Application;
import com.xana.acg.com.data.DataSource;
import com.xana.acg.fac.R;
import com.xana.acg.fac.model.account.MusicUser;
import com.xana.acg.fac.model.account.RegisterStatus;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.net.Network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.xana.acg.com.Common.SEVER.MUSIC;


public class AccountHelper {
    public static void checkExist(String smart, DataSource.SucceedCallback<Boolean> callback) {
        Network.remote(MUSIC).checkExist(smart).enqueue(new Callback<RegisterStatus>() {
            @Override
            public void onResponse(Call<RegisterStatus> call, Response<RegisterStatus> response) {
                RegisterStatus body = response.body();

                if(body==null) return;
                if(body.success()){
                    callback.onDataLoaded(body.existed());
                }else {
                    Application.showToast(body.getMsg());
                }
            }

            @Override
            public void onFailure(Call<RegisterStatus> call, Throwable t) {
                Application.showToast(R.string.data_network_error);
            }
        });
    }

    public static void login(String smart, String pass, DataSource.SucceedCallback<MusicUser> callback) {
        Network.remote(MUSIC).login(smart, pass).enqueue(new Callback<MusicUser>() {
            @Override
            public void onResponse(Call<MusicUser> call, Response<MusicUser> response) {
                MusicUser body = response.body();
                if(body==null) return;
                if(body.success()){
                    callback.onDataLoaded(body);
                }else
                    Application.showToast(body.getMsg());
            }

            @Override
            public void onFailure(Call<MusicUser> call, Throwable t) {
                Application.showToast(R.string.data_network_error);
            }
        });
    }

    public static void sendCaptcha(String smart, DataSource.SucceedCallback<String> callback) {
        Network.remote(MUSIC).sendCaptcha(smart).enqueue(new Callback<RespModel>() {
            @Override
            public void onResponse(Call<RespModel> call, Response<RespModel> response) {
                RespModel body = response.body();
                if(body==null) return;
                if(!body.success())
                    Application.showToast(body.getMessage());
            }

            @Override
            public void onFailure(Call<RespModel> call, Throwable t) {
                Application.showToast(R.string.data_network_error);
            }
        });
    }

    public static void verifyCaptcha(String smart, String captcha, DataSource.SucceedCallback callback) {
        Network.remote(MUSIC).verifyCaptcha(smart, captcha).enqueue(new Callback<RespModel>() {
            @Override
            public void onResponse(Call<RespModel> call, Response<RespModel> response) {
                RespModel body = response.body();
                if(body==null){
                    Application.showToast("验证码错误或已失效");
                    return;
                }
                if(body.success())
                    callback.onDataLoaded(null);
                else Application.showToast(body.getMessage());

            }

            @Override
            public void onFailure(Call<RespModel> call, Throwable t) {
                Application.showToast(R.string.data_network_error);
            }
        });
    }
}

