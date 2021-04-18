package com.xana.acg.fac.net;

import com.xana.acg.fac.model.Game;
import com.xana.acg.fac.model.anime.Anime;
import com.xana.acg.fac.model.anime.Detail;
import com.xana.acg.fac.model.api.PageResult;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.model.music.Data;
import com.xana.acg.fac.model.music.NewSong;
import com.xana.acg.fac.model.music.SearchResult;
import com.xana.acg.fac.model.music.SongList;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 网络请求的所有接口
 */
public interface RemoteService {
    /**
     *
     */
    @GET("/img/sel")
    Call<RespModel<Map>> getImg(@Query("start") int start, @Query("start") int size, @Query("isH") boolean isH);


    @GET("/game")
    Call<RespModel<PageResult<Game>>> getGame(@Query("page") int page, @Query("size") int size);

    @GET("/game/galgame/{id}")
    Call<RespModel<Game>> getGame(@Path("id") String id);

    @GET("/personalized")
    Call<RespModel<List<SongList>>> getSongList(@Query("limit") int limit);

    @GET("/personalized/newSong")
    Call<RespModel<List<NewSong>>> getNewSongs(@Query("limit") int limit);

    @GET("/video/group")
    Call<RespModel<List<Data>>> getElites(@Query("id") int id, @Query("offset") int offset);

    @GET("/cloudsearch")
    Call<RespModel<SearchResult>> searchMusic(@Query("keywords") String key, @Query("offset") int offset);

    @GET("/game/key/{key}")
    Call<RespModel<PageResult<Game>>> searchGame(@Path("key") String key, @Query("page") int page);

    @GET("/anime/search/{keyword}")
    Call<List<Anime>> searchAnime(@Path("keyword") String key);

    @GET("{uri}")
    Call<Detail> getAnimeDetail(@Path("uri") String uri);
}
