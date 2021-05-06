package com.xana.acg.fac.presenter.play;

import com.xana.acg.com.presenter.BaseContract;
import com.xana.acg.fac.model.music.MusicUri;

public interface MusicPlayerContract {
    interface Presenter extends BaseContract.Presenter{
        void getUri(String id);
    }
    interface View extends BaseContract.View<Presenter>{
        void onLoad(MusicUri songUri);
    }
}
