package com.xana.acg.fac.presenter.music;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.helper.MusicHelper;
import com.xana.acg.fac.model.music.Music;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

public class DailyMusicPresenter extends BasePresenter<DailyMusicContract.View>
    implements DailyMusicContract.Presenter, DataSource.Callback<List<Music>> {
    public DailyMusicPresenter(DailyMusicContract.View view) {
        super(view);
    }

    @Override
    public void success(List<Music> data) {
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                getView().onLoad(data);
            }
        });
    }

    @Override
    public void fail(String msg) {
        getView().showMsg(msg);
    }

    @Override
    public void getDailyMusic() {
        start();
        MusicHelper.getDailyMusic(this);
    }
}
