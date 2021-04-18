package com.xana.acg.fac.presenter.search;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.helper.SearchHelper;
import com.xana.acg.fac.model.music.SearchResult;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

public class SearchPresenter extends BasePresenter<SearchContract.View>
    implements SearchContract.Presenter, DataSource.SucceedCallback<SearchResult> {
    public SearchPresenter(SearchContract.View view) {
        super(view);
    }

    private int offset;
    @Override
    public void search(String key, int offset) {
        this.offset = offset;
        SearchHelper.search(key, offset, this);
    }


    @Override
    public void onDataLoaded(SearchResult data) {
        SearchContract.View view = getView();
        if(view==null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.onLoad(data.getSongs(), offset<data.getSongCount());
            }
        });
    }
}
