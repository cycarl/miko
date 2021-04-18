package com.xana.acg.fac.presenter.search;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.helper.SearchHelper;
import com.xana.acg.fac.model.anime.Anime;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.ArrayList;
import java.util.List;

public class SearchAnimePresenter extends BasePresenter<SearchContract.AnimeView>
    implements SearchContract.AnimePresenter, DataSource.Callback<List<Anime>> {
    public SearchAnimePresenter(SearchContract.AnimeView view) {
        super(view);
    }

    @Override
    public void search(String key) {
        start();
        SearchHelper.searchAnime(key, this);
    }


    @Override
    public void onDataNotAvailable(int strRes) {
        SearchContract.AnimeView view = getView();
        if(view==null) return;
        view.showError(strRes);
    }

    @Override
    public void onDataLoaded(List<Anime> animes) {
        SearchContract.AnimeView view = getView();
        if(view==null) return;
        List<Anime> left = new ArrayList<>();
        List<Anime> right = new ArrayList<>();
        boolean f = true;
        for (Anime anime : animes) {
            if(f) left.add(anime);
            else right.add(anime);
            f = !f;
        }
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                ok();
                view.onLeftLoad(left);
                view.onRightLoad(right);
            }
        });
    }
 }
