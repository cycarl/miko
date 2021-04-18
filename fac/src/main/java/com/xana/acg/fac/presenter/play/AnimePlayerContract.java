package com.xana.acg.fac.presenter.play;

import com.xana.acg.com.presenter.BaseContract;
import com.xana.acg.fac.model.anime.Detail;

public interface AnimePlayerContract {
    interface Presenter extends BaseContract.Presenter{
        void get(String url);
    }
    interface View extends BaseContract.View<Presenter>{
        void onLoad(Detail detail);
    }
}
