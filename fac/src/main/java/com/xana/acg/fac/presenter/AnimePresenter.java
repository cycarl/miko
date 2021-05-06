package com.xana.acg.fac.presenter;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.helper.AnimeHelper;
import com.xana.acg.fac.model.anime.Bangumi;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.SortedSet;

public class AnimePresenter extends BasePresenter<AnimeContract.View>
    implements AnimeContract.Presenter, DataSource.Callback<SortedSet<Bangumi>> {
    public AnimePresenter(AnimeContract.View view) {
        super(view);
    }

    @Override
    public void getAnimes() {
        start();
        AnimeHelper.getAnimes(this);
    }

    @Override
    public void success(SortedSet<Bangumi> data) {
        AnimeContract.View view = getView();
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
        AnimeContract.View view = getView();
        if(view==null) return;
        view.showMsg(msg);
    }
}
