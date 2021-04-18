package com.xana.acg.fac.presenter.search;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.helper.SearchHelper;
import com.xana.acg.fac.model.Game;
import com.xana.acg.fac.model.api.PageResult;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;


public class SearchGamePresenter extends BasePresenter<SearchContract.GameView>
    implements SearchContract.GamePresenter, DataSource.SucceedCallback<PageResult<Game>>{

    public SearchGamePresenter(SearchContract.GameView view) {
        super(view);
    }

    @Override
    public void search(String key, int page) {
        SearchHelper.searchGame(key, page, this);
    }

    @Override
    public void onDataLoaded(PageResult<Game> data) {
        SearchContract.GameView view = getView();
        if(view==null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.onLoad(data);
            }
        });
    }
}

