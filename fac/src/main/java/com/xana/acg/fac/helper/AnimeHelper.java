package com.xana.acg.fac.helper;

import android.util.Log;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.fac.R;
import com.xana.acg.fac.model.anime.Detail;
import com.xana.acg.fac.net.Network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.xana.acg.com.Common.SEVER.ANIME;

public class AnimeHelper {
    public static void getDetail(String uri, DataSource.Callback<Detail> callback) {
        Network.remote(ANIME).getAnimeDetail(uri.replace(ANIME, "")).enqueue(new Callback<Detail>() {
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {
                Detail body = response.body();
                if(body==null){
                    callback.onDataNotAvailable(R.string.data_request_fail);
                    return;
                }
                callback.onDataLoaded(body);
            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_request_fail);

            }
        });
    }
}
