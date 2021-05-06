package com.xana.acg.fac.presenter;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.helper.ImageHelper;
import com.xana.acg.fac.helper.MusicHelper;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.model.music.Data;
import com.xana.acg.fac.model.music.NewMusic;
import com.xana.acg.fac.model.music.MusicList;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

public class MusicPresenter extends BasePresenter<MusicContract.View>
    implements MusicContract.Presenter{

    public MusicPresenter(MusicContract.View view) {
        super(view);
    }

    @Override
    public void getSongList(int limit) {
        MusicHelper.getSongList(limit, new DataSource.SucceedCallback<List<MusicList>>() {
            @Override
            public void success(List<MusicList> data) {
                MusicContract.View view = getView();
                if(view==null) return;
                view.onSongListLoad(data);
            }
        });
    }

    @Override
    public void getNewSongs(int limit) {
        MusicHelper.getNewSongs(limit, new DataSource.SucceedCallback<List<NewMusic>>() {
            @Override
            public void success(List<NewMusic> data) {
                MusicContract.View view = getView();
                if(view==null) return;

                view.onNewSongsLoad(data);
            }
        });
    }


    @Override
    public void getElite(int offset) {
        start();
        MusicHelper.getElites(offset, new DataSource.Callback<RespModel<List<Data>>>() {
            @Override
            public void fail(String strRes) {
                MusicContract.View view = getView();
                if(view==null) return;
                Run.onUiAsync(new Action() {
                    @Override
                    public void call() {
                        view.showMsg(strRes);
                    }
                });
            }

            @Override
            public void success(RespModel<List<Data>> data) {
                MusicContract.View view = getView();
                if(view==null) return;
                Run.onUiAsync(new Action() {
                    @Override
                    public void call() {
                        view.onEliteLoad(data);
                    }
                });
            }
        });

    }

    @Override
    public void getBanner(int size) {
        ImageHelper.get((int)(Math.random()*1500)+1, size,false,  new DataSource.Callback<List<String>>(){
            @Override
            public void fail(String msg) {
            }
            @Override
            public void success(List<String> data) {
                MusicContract.View view = getView();
                if(view==null) return;
                view.onBannerLoad(data);
            }
        });
    }
}
