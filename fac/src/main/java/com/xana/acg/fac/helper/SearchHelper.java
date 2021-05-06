package com.xana.acg.fac.helper;

import com.xana.acg.com.app.Application;
import com.xana.acg.com.data.DataSource;
import com.xana.acg.fac.R;
import com.xana.acg.fac.model.Game;
import com.xana.acg.fac.model.anime.Anime;
import com.xana.acg.fac.model.api.PageResult;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.model.music.search.SearchSingleMusic;
import com.xana.acg.fac.net.Api;
import com.xana.acg.fac.net.NetCallBack;
import com.xana.acg.fac.net.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.xana.acg.com.Common.SEVER.ANIME;
import static com.xana.acg.com.Common.SEVER.MUSIC;
import static com.xana.acg.com.Common.SEVER.SELF;

public class SearchHelper {

    /**
     * 搜索音乐
     * @param key
     * @param offset
     * @param callback
     */
    public static void search(String key, int offset, DataSource.SucceedCallback<SearchSingleMusic> callback) {
        Network.remote(MUSIC).searchSingleMusic(key, offset).enqueue(new Callback<RespModel<SearchSingleMusic>>() {
            @Override
            public void onResponse(Call<RespModel<SearchSingleMusic>> call, Response<RespModel<SearchSingleMusic>> response) {
                RespModel<SearchSingleMusic> body = response.body();
                if(body!=null&&body.success()){
                    callback.success(body.getResult());
                }
            }
            @Override
            public void onFailure(Call<RespModel<SearchSingleMusic>> call, Throwable t) {
                Application.showToast(R.string.data_request_fail);
            }
        });

    }


    /**
     * 搜索游戏
     * @param key
     * @param page
     * @param callback
     */
    public static void searchGame(String key, int page, boolean rf, DataSource.Callback<RespModel<PageResult<Game>>> callback) {
        Network.remote(SELF).searchGame(key, page).enqueue(new NetCallBack<>(callback, rf));
    }

    public static void searchAnime(String key, DataSource.Callback<List<Anime>> callback) {
        Network.remote(ANIME).searchAnime(key).enqueue(new Callback<List<Anime>>() {
            @Override
            public void onResponse(Call<List<Anime>> call, Response<List<Anime>> response) {
                List<Anime> body = response.body();
                if(body==null||body.size()==0) {
                    return;
                }
                callback.success(body);
            }

            @Override
            public void onFailure(Call<List<Anime>> call, Throwable t) {
            }
        });
    }

    public static void searchMusic(String key, int offset, boolean refresh, DataSource.Callback callback) {
        Network.remote(MUSIC).searchSingleMusic(key, offset).enqueue(new NetCallBack<>(callback, refresh));
    }
    public static void searchMusic(int type, String key, int offset, boolean refresh, DataSource.Callback callback) {
        Api r = Network.remote(MUSIC);
        NetCallBack call = new NetCallBack<>(callback, refresh);

        switch (type){
            case 1:
                r.searchSingleMusic(key, offset).enqueue(call);
                break;
            case 1000:
                r.searchMusicList(key, offset, type).enqueue(call);
                break;
            case 1004:
                r.searchMV(key, offset, type).enqueue(call);
                break;
            case 1014:
                r.searchVideo(key, offset, type).enqueue(call);
                break;
            case 10:
                r.searchAlbum(key, offset, type).enqueue(call);
                break;
            case 1002:
                r.searchUser(key, offset, type).enqueue(call);
                break;
        }
    }
}
