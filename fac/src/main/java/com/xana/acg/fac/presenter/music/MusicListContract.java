package com.xana.acg.fac.presenter.music;

import com.xana.acg.com.presenter.BaseContract;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.model.music.Data;
import com.xana.acg.fac.model.music.MusicListCat;
import com.xana.acg.fac.model.music.MusicListDetail;

import java.util.List;

public interface MusicListContract {
    interface Presenter extends BaseContract.Presenter{
        void getDetial(String id);
    }
    interface View extends BaseContract.View<Presenter>{
        void onSuccess(MusicListDetail data);
        void onFail(String msg);
    }

    interface CatPresenter extends BaseContract.Presenter{
        void getMusicLists(String cat, int offset, String order, boolean refresh);
    }
    interface CatView extends BaseContract.View<CatPresenter>{
        void onSuccess(MusicListCat musicListCat);
    }
    interface VdPresenter extends BaseContract.Presenter{
        void getVideos(String id, int offset);
    }
    interface VdView extends BaseContract.View<VdPresenter>{
        void onLoad(RespModel<List<Data>> resp);
    }
}
