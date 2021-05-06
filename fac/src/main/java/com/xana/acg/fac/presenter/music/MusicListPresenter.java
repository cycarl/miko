package com.xana.acg.fac.presenter.music;
import com.xana.acg.com.app.Application;
import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.helper.MusicHelper;
import com.xana.acg.fac.model.music.MusicListDetail;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

public class MusicListPresenter extends BasePresenter<MusicListContract.View>
    implements MusicListContract.Presenter, DataSource.Callback<MusicListDetail> {

    public MusicListPresenter(MusicListContract.View view) {
        super(view);
    }

    @Override
    public void success(MusicListDetail data) {
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                getView().onSuccess(data);
            }
        });
    }

    @Override
    public void fail(String strRes) {
        getView().showMsg(strRes);
    }

    @Override
    public void getDetial(String id) {
        start();
        MusicHelper.getMusicListDetial(id, this);
    }
}
