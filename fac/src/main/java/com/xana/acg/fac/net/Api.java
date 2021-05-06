package com.xana.acg.fac.net;

import com.xana.acg.fac.model.Comment;
import com.xana.acg.fac.model.CommentRequest;
import com.xana.acg.fac.model.Game;
import com.xana.acg.fac.model.TbUser;
import com.xana.acg.fac.model.account.MusicUser;
import com.xana.acg.fac.model.account.RegisterStatus;
import com.xana.acg.fac.model.anime.Anime;
import com.xana.acg.fac.model.anime.Bangumi;
import com.xana.acg.fac.model.anime.Detail;
import com.xana.acg.fac.model.anime.IptvUri;
import com.xana.acg.fac.model.api.PageResult;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.model.music.DailyMusics;
import com.xana.acg.fac.model.music.Data;
import com.xana.acg.fac.model.music.MusicListCat;
import com.xana.acg.fac.model.music.MusicListDetail;
import com.xana.acg.fac.model.music.MusicListMy;
import com.xana.acg.fac.model.music.NewMusic;
import com.xana.acg.fac.model.music.NewMusicCat;
import com.xana.acg.fac.model.music.VideoUri;
import com.xana.acg.fac.model.music.search.SearchAlbum;
import com.xana.acg.fac.model.music.search.SearchMV;
import com.xana.acg.fac.model.music.search.SearchMusicList;
import com.xana.acg.fac.model.music.search.SearchSingleMusic;
import com.xana.acg.fac.model.music.MusicList;
import com.xana.acg.fac.model.music.search.SearchUser;
import com.xana.acg.fac.model.music.search.SearchVideo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 网络请求的所有接口
 */
public interface Api {
    /**
     *
     */
    @GET("/img/sel")
    Call<RespModel<Map>> getImg(@Query("start") int start, @Query("size") int size, @Query("isH") boolean isH);

    @GET("/game")
    Call<RespModel<PageResult<Game>>> getGame(@Query("page") int page, @Query("size") int size);

    @GET("/game/galgame/{id}")
    Call<RespModel<Game>> getGame(@Path("id") String id);

    @GET("/personalized")
    Call<RespModel<List<MusicList>>> getSongList(@Query("limit") int limit);

    @GET("/personalized/newSong")
    Call<RespModel<List<NewMusic>>> getNewSongs(@Query("limit") int limit);

    @GET("/video/group")
    Call<RespModel<List<Data>>> getElites(@Query("id") String id, @Query("offset") int offset);
    @GET("/video/timeline/recommend")
    Call<RespModel<List<Data>>> getElitesRec(@Query("offset") int offset);


    @GET("/game/key/{key}")
    Call<RespModel<PageResult<Game>>> searchGame(@Path("key") String key, @Query("page") int page);

    @GET("/anime/search/{keyword}")
    Call<List<Anime>> searchAnime(@Path("keyword") String key);

    @GET("{uri}")
    Call<Detail> getAnimeDetail(@Path("uri") String uri);

    @GET("/cellphone/existence/check")
    Call<RegisterStatus> checkExist(@Query("phone") String smart);

    @GET("/login/cellphone")
    Call<MusicUser> login(@Query("phone") String smart, @Query("password") String pass);

    @GET("/register/cellphone")
    Call<MusicUser> register(@Query("phone") String smart, @Query("password") String pass, @Query("nickname") String nick, @Query("captcha") String capt);

    @GET("/captcha/sent")
    Call<RespModel> sendCaptcha(@Query("phone") String smart);

    @GET("/captcha/verify")
    Call<RespModel> verifyCaptcha(@Query("phone") String smart, @Query("captcha") String captcha);

    @GET("/playlist/detail")
    Call<MusicListDetail> getMusicListDetial(@Query("id") String id);

    @GET("/top/playlist")
    Call<MusicListCat> getMusicListCat(@Query("cat") String cat,@Query("offset") int offset,@Query("order") String order, @Query("limit") int limit);

    @GET("/top/song")
    Call<NewMusicCat> getNewMusicCat(@Query("type") String type);

    @GET("/recommend/songs")
    Call<DailyMusics> getDailyMusic();

    @GET("/cloudsearch")
    Call<RespModel<SearchSingleMusic>> searchSingleMusic(@Query("keywords") String key, @Query("offset") int offset);

    @GET("/cloudsearch")
    Call<RespModel<SearchMusicList>> searchMusicList(@Query("keywords") String key, @Query("offset") int offset, @Query("type") int type);

    @GET("/cloudsearch")
    Call<RespModel<SearchMV>> searchMV(@Query("keywords") String key, @Query("offset") int offset, @Query("type") int type);

    @GET("/cloudsearch")
    Call<RespModel<SearchVideo>> searchVideo(@Query("keywords") String key, @Query("offset") int offset, @Query("type") int type);

    @GET("/cloudsearch")
    Call<RespModel<SearchAlbum>> searchAlbum(@Query("keywords") String key, @Query("offset") int offset, @Query("type") int type);

    @GET("/cloudsearch")
    Call<RespModel<SearchUser>> searchUser(@Query("keywords") String key, @Query("offset") int offset, @Query("type") int type);


    @GET("/anime/bangumi/updates")
    Call<SortedSet<Bangumi>> getAnimes();

    @GET("/video/url")
    Call<VideoUri> getVideoUri(@Query("id")String id);


    @GET("/iptv/list")
    Call<SortedSet<IptvUri>> getIptv();

    @PUT("/app/login")
    Call<RespModel> login(@Body TbUser user);

    @GET("/comment/query/")
    Call<RespModel<PageResult<Comment>>> getComment(@Query("aid") String acgId, @Query("page") int page, @Query("r") int r);

    @GET("/comment/reply/")
    Call<RespModel<Set<Comment>>> getReply(@Query("cid") String commentId);

    @PUT("/comment/add")
    Call<Integer> sendCommnet(@Body CommentRequest comment);

    @GET("/app/h")
    Call<RespModel> check(@Query("pass") String pass);

    @GET("/user/playlist")
    Call<MusicListMy> getUserMusicList(@Query("uid")String uid, @Query("offset")int offset, @Query("limit")int limit);
}
