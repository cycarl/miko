package com.xana.acg.fac.presenter.search;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.helper.SearchHelper;
import com.xana.acg.fac.model.api.Resp;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

public class MusicSearchPresenter<T extends Resp>
        extends BasePresenter<SearchContract.MusicView<T>>
    implements SearchContract.Presenter, DataSource.Callback<T> {
    public MusicSearchPresenter(SearchContract.MusicView<T> view) {
        super(view);
    }

    @Override
    public void search(String key, int offset, boolean refresh) {
        start();
        SearchHelper.searchMusic(key, offset, refresh, this);
    }

    @Override
    public void search(int type, String key, int offset, boolean refresh) {
        start();
        SearchHelper.searchMusic(type, key, offset, refresh, this);
    }

    @Override
    public void success(T data) {
        SearchContract.MusicView<T> view = getView();
        if(view==null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.onLoad(data);
            }
        });
    }

    @Override
    public void fail(String msg) {
        SearchContract.MusicView<T> view = getView();
        if(view==null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showMsg(msg);
            }
        });
    }
}
