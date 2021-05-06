package com.xana.acg.fac.presenter.search;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.helper.SearchHelper;
import com.xana.acg.fac.model.Game;
import com.xana.acg.fac.model.api.PageResult;
import com.xana.acg.fac.model.api.RespModel;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;


public class SearchGamePresenter extends BasePresenter<SearchContract.GameView>
    implements SearchContract.GamePresenter, DataSource.Callback<RespModel<PageResult<Game>>>{

    public SearchGamePresenter(SearchContract.GameView view) {
        super(view);
    }

    @Override
    public void search(String key, int page, boolean rf) {
        start();
        SearchHelper.searchGame(key, page, rf, this);
    }

    @Override
    public void success(RespModel<PageResult<Game>> data) {
        SearchContract.GameView view = getView();
        if(view==null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.onLoad(data.getResult(), data.refresh);
            }
        });
    }

    @Override
    public void fail(String msg) {
        SearchContract.GameView view = getView();
        if(view==null) return;
        view.showMsg(msg);
    }
}

