package com.xana.acg.fac.presenter;

import com.xana.acg.com.presenter.BaseContract;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.model.music.Data;
import com.xana.acg.fac.model.music.NewMusic;
import com.xana.acg.fac.model.music.MusicList;

import java.util.List;

public interface MusicContract {
    interface Presenter extends BaseContract.Presenter{
        void getSongList(int limit);
        void getNewSongs(int limit);
        void getElite(int offset);

        void getBanner(int i);

    }

    interface View extends BaseContract.View<Presenter>{
        void onSongListLoad(List<MusicList> songLists);
        void onNewSongsLoad(List<NewMusic> songs);
        void onEliteLoad(RespModel<List<Data>> elites);
        void onBannerLoad(List<String> banner);
    }
}
