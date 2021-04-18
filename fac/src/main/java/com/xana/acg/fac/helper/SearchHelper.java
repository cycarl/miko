package com.xana.acg.fac.helper;

import com.xana.acg.com.app.Application;
import com.xana.acg.com.data.DataSource;
import com.xana.acg.fac.R;
import com.xana.acg.fac.model.Game;
import com.xana.acg.fac.model.anime.Anime;
import com.xana.acg.fac.model.api.PageResult;
import com.xana.acg.fac.model.api.Res;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.model.music.SearchResult;
import com.xana.acg.fac.net.Network;
import com.xana.acg.fac.presenter.search.SearchAnimePresenter;
import com.xana.acg.fac.presenter.search.SearchGamePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.xana.acg.com.Common.SEVER.ANIME;
import static com.xana.acg.com.Common.SEVER.MUSIC;
import static com.xana.acg.com.Common.SEVER.MY;

public class SearchHelper {

    /**
     * 搜索音乐
     * @param key
     * @param offset
     * @param callback
     */
    public static void search(String key, int offset, DataSource.SucceedCallback<SearchResult> callback) {
        Network.remote(MUSIC).searchMusic(key, offset).enqueue(new Callback<RespModel<SearchResult>>() {
            @Override
            public void onResponse(Call<RespModel<SearchResult>> call, Response<RespModel<SearchResult>> response) {
                RespModel<SearchResult> body = response.body();
                if(body!=null&&body.success()){
                    callback.onDataLoaded(body.getResult());
                }
            }
            @Override
            public void onFailure(Call<RespModel<SearchResult>> call, Throwable t) {
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
    public static void searchGame(String key, int page, DataSource.SucceedCallback<PageResult<Game>> callback) {
        Network.remote(MY).searchGame(key, page).enqueue(new Callback<RespModel<PageResult<Game>>>() {
            @Override
            public void onResponse(Call<RespModel<PageResult<Game>>> call, Response<RespModel<PageResult<Game>>> response) {
                RespModel<PageResult<Game>> body = response.body();
                if(body!=null && body.success()){
                    callback.onDataLoaded(body.getResult());
                }
            }

            @Override
            public void onFailure(Call<RespModel<PageResult<Game>>> call, Throwable t) {
                Application.showToast(R.string.data_request_fail);
            }
        });
    }

    public static void searchAnime(String key, DataSource.Callback<List<Anime>> callback) {
        Network.remote(ANIME).searchAnime(key).enqueue(new Callback<List<Anime>>() {
            @Override
            public void onResponse(Call<List<Anime>> call, Response<List<Anime>> response) {
                List<Anime> body = response.body();
                if(body==null||body.size()==0) {
                    callback.onDataNotAvailable(R.string.data_request_empty);
                    return;
                }
                callback.onDataLoaded(body);
            }

            @Override
            public void onFailure(Call<List<Anime>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_request_fail);
            }
        });


    }
}
