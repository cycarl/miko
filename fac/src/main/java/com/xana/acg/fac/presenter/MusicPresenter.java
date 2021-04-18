package com.xana.acg.fac.presenter;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BaseContract;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.helper.MusicHelper;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.model.music.Data;
import com.xana.acg.fac.model.music.NewSong;
import com.xana.acg.fac.model.music.SongList;

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
        MusicHelper.getSongList(limit, new DataSource.SucceedCallback<List<SongList>>() {
            @Override
            public void onDataLoaded(List<SongList> data) {
                MusicContract.View view = getView();
                if(view==null) return;

                view.onSongListLoad(data);
            }
        });
    }

    @Override
    public void getNewSongs(int limit) {
        MusicHelper.getNewSongs(limit, new DataSource.SucceedCallback<List<NewSong>>() {
            @Override
            public void onDataLoaded(List<NewSong> data) {
                MusicContract.View view = getView();
                if(view==null) return;

                view.onNewSongsLoad(data);
            }
        });
    }


    @Override
    public void getElite(int offset) {
        MusicHelper.getElites(offset, new DataSource.SucceedCallback<RespModel<List<Data>>>() {
            @Override
            public void onDataLoaded(RespModel<List<Data>> data) {
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
}
