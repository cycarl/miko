package com.xana.acg.fac.presenter.search;

import com.xana.acg.com.presenter.BaseContract;
import com.xana.acg.fac.model.Game;
import com.xana.acg.fac.model.anime.Anime;
import com.xana.acg.fac.model.api.PageResult;
import com.xana.acg.fac.model.api.Resp;
import com.xana.acg.fac.model.music.Music;

import java.util.List;

public interface SearchContract {
    interface Presenter extends BaseContract.Presenter{
        void search(String key, int offset, boolean refresh);
        void search(int type, String key, int offset, boolean refresh);
    }
    interface View extends BaseContract.View<Presenter>{
        void onLoad(List<Music> songs, boolean hasMore);
    }
    interface MusicView<T> extends BaseContract.View<Presenter>{
        void onLoad(T resp);
    }

    interface GamePresenter extends BaseContract.Presenter{
        void search(String key, int page, boolean refresh);
    }
    interface GameView extends BaseContract.View<GamePresenter>{
        void onLoad(PageResult<Game> res, boolean refresh);
    }

    interface AnimePresenter extends BaseContract.Presenter{
        void search(String key);
    }
    interface AnimeView extends BaseContract.View<AnimePresenter>{
        void onLeftLoad(List<Anime> animes);
        void onRightLoad(List<Anime> animes);
    }
    interface AnimView extends BaseContract.View<AnimePresenter>{
        void onLoad(List<Anime> animes);
    }
}
