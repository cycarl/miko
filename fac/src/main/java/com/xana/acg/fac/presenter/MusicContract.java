package com.xana.acg.fac.presenter;

import com.xana.acg.com.presenter.BaseContract;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.model.music.Data;
import com.xana.acg.fac.model.music.Elite;
import com.xana.acg.fac.model.music.NewSong;
import com.xana.acg.fac.model.music.SongList;

import java.util.List;

public interface MusicContract {
    interface Presenter extends BaseContract.Presenter{
        void getSongList(int limit);
        void getNewSongs(int limit);
        void getElite(int offset);
    }

    interface View extends BaseContract.View<Presenter>{
        void onSongListLoad(List<SongList> songLists);
        void onNewSongsLoad(List<NewSong> songs);
        void onEliteLoad(RespModel<List<Data>> elites);
    }
}
