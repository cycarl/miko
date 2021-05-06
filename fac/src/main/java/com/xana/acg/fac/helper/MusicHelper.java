package com.xana.acg.fac.helper;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.model.music.DailyMusics;
import com.xana.acg.fac.model.music.Data;
import com.xana.acg.fac.model.music.Music;
import com.xana.acg.fac.model.music.MusicListCat;
import com.xana.acg.fac.model.music.MusicListDetail;
import com.xana.acg.fac.model.music.NewMusic;
import com.xana.acg.fac.model.music.MusicList;
import com.xana.acg.fac.model.music.NewMusicCat;
import com.xana.acg.fac.net.NetCallBack;
import com.xana.acg.fac.net.Network;
import com.xana.acg.fac.presenter.play.MusicPlayerPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.xana.acg.com.Common.SEVER.MUSIC;

public class MusicHelper {
    public static void getSongList(int limit, DataSource.SucceedCallback<List<MusicList>> callback) {
        Network.remote(MUSIC).getSongList(limit).enqueue(new NetCallBack<RespModel<List<MusicList>>>() {
            @Override
            public void onResponse(Call<RespModel<List<MusicList>>> call, Response<RespModel<List<MusicList>>> response) {
                RespModel<List<MusicList>> body = response.body();
                if(body!=null&&body.success()){
                    callback.success(body.getResult());
                }
            }
        });
    }

    public static void getNewSongs(int limit, DataSource.SucceedCallback<List<NewMusic>> callback) {
        Network.remote(MUSIC).getNewSongs(limit).enqueue(new NetCallBack<RespModel<List<NewMusic>>>() {
            @Override
            public void onResponse(Call<RespModel<List<NewMusic>>> call, Response<RespModel<List<NewMusic>>> response) {
                RespModel<List<NewMusic>> body = response.body();
                if(body!=null&&body.success()){
                    callback.success(body.getResult());
                }
            }

        });
    }

    public static void getElites(int offset, DataSource.Callback<RespModel<List<Data>>> callback) {
        Network.remote(MUSIC).getElites("57104", offset).enqueue(new NetCallBack<RespModel<List<Data>>>(callback));
    }
    public static void getUri(String id, MusicPlayerPresenter musicPlayerPresenter) {

    }
    public static void getMusicListDetial(String id, DataSource.Callback<MusicListDetail> callback) {
        Network.remote(MUSIC).getMusicListDetial(id).enqueue(new NetCallBack<>(callback));
    }
    public static void getMusicListCat(String cat, int offset, String order, boolean refresh, DataSource.Callback<MusicListCat> callback) {
        Network.remote(MUSIC).getMusicListCat(cat, offset, order, 30).enqueue(new NetCallBack<MusicListCat>(callback, refresh));
    }

    public static void getNewMusicCat(String type, DataSource.Callback<NewMusicCat> callback) {
        Network.remote(MUSIC).getNewMusicCat(type).enqueue(new NetCallBack<NewMusicCat>(callback));
    }

    public static void getDailyMusic(DataSource.Callback<List<Music>> callback) {
        Network.remote(MUSIC).getDailyMusic().enqueue(new NetCallBack<DailyMusics>(callback){
            @Override
            public void onResponse(Call<DailyMusics> call, Response<DailyMusics> response) {
                DailyMusics body = response.body();
                if(response.isSuccessful()&&body.success()){
                    callback.success(body.getData().getDailySongs());
                }else callback.fail(response.message());
            }
        });
    }

    public static void getVds(String id, int offset, DataSource.Callback cb) {
        if(id==null) Network.remote(MUSIC).getElitesRec(offset).enqueue(new NetCallBack<>(cb));
        else Network.remote(MUSIC).getElites(id, offset).enqueue(new NetCallBack<>(cb));
    }
}
