package com.xana.acg.fac.helper;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.fac.model.anime.Bangumi;
import com.xana.acg.fac.model.anime.Detail;
import com.xana.acg.fac.net.NetCallBack;
import com.xana.acg.fac.net.Network;

import java.util.SortedSet;

import retrofit2.Call;
import retrofit2.Response;

import static com.xana.acg.com.Common.SEVER.ANIME;

public class AnimeHelper {
    public static void getDetail(String uri, DataSource.Callback<Detail> callback) {
        Network.remote(ANIME).getAnimeDetail(uri.replace(ANIME, "")).enqueue(new NetCallBack(callback){
            @Override
            public void onResponse(Call call, Response resp) {
                if(resp.isSuccessful()){
                    callback.success((Detail) resp.body());
                }else
                    callback.fail("未找到动漫资源!");
            }
        });
    }

    public static void getAnimes(DataSource.Callback<SortedSet<Bangumi>> callback) {
        Network.remote(ANIME).getAnimes().enqueue(new NetCallBack(callback){
            @Override
            public void onResponse(Call call, Response resp) {
                if(resp.isSuccessful()){
                    callback.success((SortedSet<Bangumi>) resp.body());
                }else
                    callback.fail("未找到动漫资源!");
            }
        });
    }
}
