package com.xana.acg.fac.presenter.search;

import com.xana.acg.com.presenter.BaseContract;
import com.xana.acg.fac.model.Game;
import com.xana.acg.fac.model.anime.Anime;
import com.xana.acg.fac.model.api.PageResult;
import com.xana.acg.fac.model.music.SearchResult;

import java.util.List;

public interface SearchContract {
    interface Presenter extends BaseContract.Presenter{
        void search(String key, int offset);
    }
    interface View extends BaseContract.View<Presenter>{
        void onLoad(List<SearchResult.Song> songs, boolean hasMore);
    }

    interface GamePresenter extends BaseContract.Presenter{
        void search(String key, int page);
    }
    interface GameView extends BaseContract.View<GamePresenter>{
        void onLoad(PageResult<Game> res);
    }

    interface AnimePresenter extends BaseContract.Presenter{
        void search(String key);
    }
    interface AnimeView extends BaseContract.View<AnimePresenter>{
        void onLeftLoad(List<Anime> animes);
        void onRightLoad(List<Anime> animes);
    }
}
