package com.xana.acg.fac.helper;

import android.util.Log;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.fac.R;
import com.xana.acg.fac.model.Game;
import com.xana.acg.fac.model.api.PageResult;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.net.Network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.xana.acg.com.Common.SEVER.MY;

public class GameHelper {
    public static void get(int page, int size, DataSource.Callback callback) {
        Network.remote(MY).getGame(page, size).enqueue(new Callback<RespModel<PageResult<Game>>>() {
            @Override
            public void onResponse(Call<RespModel<PageResult<Game>>> call, Response<RespModel<PageResult<Game>>> response) {
                RespModel<PageResult<Game>> body = response.body();
                if(body!=null&&body.success()){
                    callback.onDataLoaded(body.getResult());
                }
            }

            @Override
            public void onFailure(Call<RespModel<PageResult<Game>>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_request_fail);
            }
        });
    }

    public static void get(String id, DataSource.Callback callback) {
        Network.remote(MY).getGame(id).enqueue(new Callback<RespModel<Game>>() {
            @Override
            public void onResponse(Call<RespModel<Game>> call, Response<RespModel<Game>> response) {
                RespModel<Game> body = response.body();
                if(body!=null&&body.success()){
                    callback.onDataLoaded(body.getResult());
                }
            }

            @Override
            public void onFailure(Call<RespModel<Game>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_request_fail);
            }
        });
    }
}
