package com.xana.acg.fac.net;


import com.xana.acg.com.data.DataSource;
import com.xana.acg.fac.model.api.RespModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetCallBack<T extends RespModel> implements Callback<T> {
    private DataSource.SucceedCallback<Object> callback;

    public NetCallBack(DataSource.SucceedCallback<Object> callback){
       this.callback = callback;
    }
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        T body = response.body();
        if(body!=null&&body.success())  {

        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

    }
}
