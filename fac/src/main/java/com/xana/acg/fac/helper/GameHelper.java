package com.xana.acg.fac.helper;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.fac.model.Game;
import com.xana.acg.fac.model.api.PageResult;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.net.Network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.xana.acg.com.Common.SEVER.SELF;

public class GameHelper {
    public static void get(int page, int size, boolean refresh, DataSource.Callback callback) {
        Network.remote(SELF).getGame(page, size).enqueue(new Callback<RespModel<PageResult<Game>>>() {
            @Override
            public void onResponse(Call<RespModel<PageResult<Game>>> call, Response<RespModel<PageResult<Game>>> response) {
                RespModel<PageResult<Game>> body = response.body();
                body.refresh = refresh;
                if(body!=null&&body.success()){
                    callback.success(body);
                }
            }

            @Override
            public void onFailure(Call<RespModel<PageResult<Game>>> call, Throwable t) {
                callback.fail("访问失败");
            }
        });
    }

    public static void get(String id, DataSource.Callback callback) {
        Network.remote(SELF).getGame(id).enqueue(new Callback<RespModel<Game>>() {
            @Override
            public void onResponse(Call<RespModel<Game>> call, Response<RespModel<Game>> response) {
                RespModel<Game> body = response.body();
                if(body!=null&&body.success()){
                    callback.success(body.getResult());
                }
            }

            @Override
            public void onFailure(Call<RespModel<Game>> call, Throwable t) {
                callback.fail("访问失败");
            }
        });
    }
}
