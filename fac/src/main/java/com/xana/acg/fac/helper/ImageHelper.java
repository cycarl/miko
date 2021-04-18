package com.xana.acg.fac.helper;

import com.xana.acg.com.app.Application;
import com.xana.acg.com.data.DataSource;
import com.xana.acg.fac.R;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.net.Network;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.xana.acg.com.Common.SEVER.MY;


public class ImageHelper {

    public static void get(int start, int size, boolean isH, final DataSource.SucceedCallback<List<String>> callback) {
        Network.remote(MY).getImg(start, size, isH).enqueue(new Callback<RespModel<Map>>() {
            @Override
            public void onResponse(Call<RespModel<Map>> call, Response<RespModel<Map>> response) {
                RespModel<Map> body = response.body();
                if(body!=null&&body.success()){
                    callback.onDataLoaded((List<String>) body.getResult().get("imgList"));
                }
            }

            @Override
            public void onFailure(Call<RespModel<Map>> call, Throwable t) {
                Application.showToast(R.string.data_request_fail);
            }
        });
    }
}
