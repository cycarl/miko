package com.xana.acg.fac.presenter.music;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.helper.MusicHelper;
import com.xana.acg.fac.model.music.NewMusicCat;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

public class NewMusicPresenter extends BasePresenter<NewMusicContract.CatView>
    implements NewMusicContract.CatPresenter, DataSource.Callback<NewMusicCat> {
    public NewMusicPresenter(NewMusicContract.CatView view) {
        super(view);
    }

    @Override
    public void getNewMusicCat(String type) {
        start();
        MusicHelper.getNewMusicCat(type, this);
    }

    @Override
    public void success(NewMusicCat data) {
        NewMusicContract.CatView view = getView();
        if(view==null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.onSuccess(data);
            }
        });
    }

    @Override
    public void fail(String msg) {
        getView().showMsg(msg);
    }
}
