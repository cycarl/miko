package com.xana.acg.fac.presenter;

import com.xana.acg.com.presenter.BaseContract;
import com.xana.acg.fac.model.anime.Bangumi;

import java.util.SortedSet;

public interface AnimeContract {
    interface Presenter extends BaseContract.Presenter{
        void getAnimes();
    }
    interface View extends BaseContract.View<Presenter>{
        void onLoad(SortedSet<Bangumi> bangumis);
    }
}
