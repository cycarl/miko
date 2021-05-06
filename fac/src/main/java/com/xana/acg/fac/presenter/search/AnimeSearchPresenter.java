package com.xana.acg.fac.presenter.search;

import com.xana.acg.fac.model.anime.Anime;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

public class AnimeSearchPresenter extends SearchAnimePresenter{

    public AnimeSearchPresenter(SearchContract.AnimeView view) {
        super(view);
    }

    @Override
    public void success(List<Anime> animes) {
        SearchContract.AnimeView view = getView();
        if(view==null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.onLeftLoad(animes);
            }
        });
    }
}
