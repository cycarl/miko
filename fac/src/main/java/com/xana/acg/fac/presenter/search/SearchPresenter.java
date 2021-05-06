package com.xana.acg.fac.presenter.search;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.helper.SearchHelper;
import com.xana.acg.fac.model.music.search.SearchSingleMusic;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

public class SearchPresenter extends BasePresenter<SearchContract.View>
    implements SearchContract.Presenter, DataSource.Callback<SearchSingleMusic> {
    public SearchPresenter(SearchContract.View view) {
        super(view);
    }

    private int offset;
    @Override
    public void search(String key, int offset, boolean refresh) {
        start();
        this.offset = offset;
        SearchHelper.search(key, offset, this);
    }

    @Override
    public void search(int type, String key, int offset, boolean refresh) {
    }


    @Override
    public void success(SearchSingleMusic data) {
        SearchContract.View view = getView();
        if(view==null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.onLoad(data.getDatas(), offset<data.getCount());
            }
        });
    }

    @Override
    public void fail(String strRes) {
    }
}
