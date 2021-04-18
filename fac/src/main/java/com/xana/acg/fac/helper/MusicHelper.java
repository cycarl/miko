package com.xana.acg.fac.helper;

import com.xana.acg.com.app.Application;
import com.xana.acg.com.data.DataSource;
import com.xana.acg.fac.R;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.model.music.Data;
import com.xana.acg.fac.model.music.NewSong;
import com.xana.acg.fac.model.music.SongList;
import com.xana.acg.fac.net.Network;
import com.xana.acg.fac.presenter.play.MusicPlayerPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.xana.acg.com.Common.SEVER.MUSIC;

public class MusicHelper {
    public static void getSongList(int limit, DataSource.SucceedCallback<List<SongList>> callback) {
        Network.remote(MUSIC).getSongList(limit).enqueue(new Callback<RespModel<List<SongList>>>() {
            @Override
            public void onResponse(Call<RespModel<List<SongList>>> call, Response<RespModel<List<SongList>>> response) {
                RespModel<List<SongList>> body = response.body();
                if(body!=null&&body.success()){
                    callback.onDataLoaded(body.getResult());
                }
            }

            @Override
            public void onFailure(Call<RespModel<List<SongList>>> call, Throwable t) {
                Application.showToast(R.string.data_request_fail);
            }
        });
    }


    public static void getNewSongs(int limit, DataSource.SucceedCallback<List<NewSong>> callback) {
        Network.remote(MUSIC).getNewSongs(limit).enqueue(new Callback<RespModel<List<NewSong>>>() {
            @Override
            public void onResponse(Call<RespModel<List<NewSong>>> call, Response<RespModel<List<NewSong>>> response) {
                RespModel<List<NewSong>> body = response.body();
                if(body!=null&&body.success()){
                    callback.onDataLoaded(body.getResult());
                }
            }
            @Override
            public void onFailure(Call<RespModel<List<NewSong>>> call, Throwable t) {
                Application.showToast(R.string.data_request_fail);
            }

        });
    }

    public static void getElites(int offset, DataSource.SucceedCallback<RespModel<List<Data>>> callback) {
        Network.remote(MUSIC).getElites(57104, offset).enqueue(new Callback<RespModel<List<Data>>>() {
            @Override
            public void onResponse(Call<RespModel<List<Data>>> call, Response<RespModel<List<Data>>> response) {
                RespModel<List<Data>> body = response.body();
                if(body!=null&&body.success()){
                    callback.onDataLoaded(body);
                }
            }

            @Override
            public void onFailure(Call<RespModel<List<Data>>> call, Throwable t) {

            }
        });
    }

    public static void getUri(String id, MusicPlayerPresenter musicPlayerPresenter) {

    }
}
