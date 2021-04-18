package com.xana.acg.fac.presenter;

import android.util.Log;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.helper.GameHelper;
import com.xana.acg.fac.model.Game;
import com.xana.acg.fac.model.api.PageResult;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

public class GamePresenter<T> extends BasePresenter<GameContract.View<T>>
    implements GameContract.Presenter, DataSource.Callback<T> {

    public GamePresenter(GameContract.View<T> view) {
        super(view);
    }

    @Override
    public void onDataLoaded(T data) {
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
    public void onDataNotAvailable(int strRes) {
        GameContract.View<T> view = getView();
        if(view==null) return;

        view.showError(strRes);
    }

    @Override
    public void get(int page, int size) {
        GameHelper.get(page, size, this);
    }

    @Override
    public void get(String id) {
        GameHelper.get(id, this);
    }
}
