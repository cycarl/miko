package com.xana.acg.fac.presenter.music;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.helper.MusicHelper;
import com.xana.acg.fac.model.music.MusicListCat;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

public class MusicListCatPresenter extends BasePresenter<MusicListContract.CatView>
    implements MusicListContract.CatPresenter, DataSource.Callback<MusicListCat> {

    public MusicListCatPresenter(MusicListContract.CatView view) {
        super(view);
    }

    @Override
    public void getMusicLists(String cat, int offset, String order, boolean refresh) {
        start();
        MusicHelper.getMusicListCat(cat, offset, order, refresh,this);
    }

    @Override
    public void success(MusicListCat data) {
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                getView().onSuccess(data);
            }
        });

    }

    @Override
    public void fail(String msg) {
        getView().showMsg(msg);
    }
}
