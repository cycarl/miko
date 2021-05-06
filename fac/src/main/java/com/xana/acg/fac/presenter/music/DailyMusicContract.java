package com.xana.acg.fac.presenter.music;

import com.xana.acg.com.presenter.BaseContract;
import com.xana.acg.fac.model.music.Music;

import java.util.List;

public interface DailyMusicContract {
    interface Presenter extends BaseContract.Presenter{
        void getDailyMusic();
    }
    interface View extends BaseContract.View<Presenter>{
        void onLoad(List<Music> musics);
    }
}
