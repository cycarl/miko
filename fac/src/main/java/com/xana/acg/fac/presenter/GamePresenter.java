package com.xana.acg.fac.presenter;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.helper.GameHelper;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

public class GamePresenter<T> extends BasePresenter<GameContract.View<T>>
    implements GameContract.Presenter, DataSource.Callback<T> {

    public GamePresenter(GameContract.View<T> view) {
        super(view);
    }

    @Override
    public void success(T data) {
        GameContract.View<T> view = getView();

        if(view==null)
            return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.onLoad(data);
            }
        });

    }

    @Override
    public void fail(String strRes) {
        GameContract.View<T> view = getView();
        if(view==null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showMsg(strRes);
            }
        });

    }

    @Override
    public void get(int page, int size, boolean refresh) {
        GameHelper.get(page, size, refresh, this);
    }

    @Override
    public void get(String id) {
        GameHelper.get(id, this);
    }
}
