package com.xana.acg.fac.presenter.music;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.helper.MusicHelper;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.model.music.Data;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

public class VideoPresenter extends BasePresenter<MusicListContract.VdView>
    implements MusicListContract.VdPresenter, DataSource.Callback<RespModel<List<Data>>> {
    public VideoPresenter(MusicListContract.VdView view) {
        super(view);
    }

    @Override
    public void getVideos(String id, int offset) {
        start();
        MusicHelper.getVds(id, offset, this);
    }

    @Override
    public void success(RespModel<List<Data>> data) {
        MusicListContract.VdView view = getView();
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
        MusicListContract.VdView view = getView();
        if(view==null) return;
        view.showMsg(msg);
    }
}
