package com.xana.acg.fac.presenter.play;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.helper.AnimeHelper;
import com.xana.acg.fac.model.anime.Detail;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

public class AnimePlayerPresenter extends BasePresenter<AnimePlayerContract.View>
    implements AnimePlayerContract.Presenter, DataSource.Callback<Detail> {
    public AnimePlayerPresenter(AnimePlayerContract.View view) {
        super(view);
    }

    @Override
    public void get(String url) {
        AnimeHelper.getDetail(url, this);
    }

    @Override
    public void onDataLoaded(Detail data) {
        AnimePlayerContract.View view = getView();
        if(view==null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.onLoad(data);
            }
        });
    }

    @Override
    public void onDataNotAvailable(int strRes) {
        AnimePlayerContract.View view = getView();
        if(view==null) return;
        view.showError(strRes);
    }
}
