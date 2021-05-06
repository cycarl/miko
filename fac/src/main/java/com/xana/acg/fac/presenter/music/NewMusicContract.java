package com.xana.acg.fac.presenter.music;

import com.xana.acg.com.presenter.BaseContract;
import com.xana.acg.fac.model.music.NewMusicCat;

public interface NewMusicContract {
    interface CatPresenter extends BaseContract.Presenter{
        void getNewMusicCat(String type);
    }
    interface CatView extends BaseContract.View<CatPresenter>{
        void onSuccess(NewMusicCat newMusicCat);
//        void onFail(String msg);
    }
}
