package com.xana.acg.fac.presenter.play;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.helper.MusicHelper;
import com.xana.acg.fac.model.music.MusicUri;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

public class MusicPlayerPresenter extends BasePresenter<MusicPlayerContract.View>
    implements MusicPlayerContract.Presenter, DataSource.SucceedCallback<MusicUri> {

    public MusicPlayerPresenter(MusicPlayerContract.View view) {
        super(view);
    }

    @Override
    public void getUri(String id) {
        start();
        MusicHelper.getUri(id, this);
    }

    @Override
    public void success(MusicUri data) {
        MusicPlayerContract.View view = getView();
        if(view==null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.onLoad(data);
            }
        });
    }
}
