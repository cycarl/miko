package com.xana.acg.fac.presenter;

import com.xana.acg.com.presenter.BaseContract;

import java.util.List;

public interface ImageContract {
    interface Presenter extends BaseContract.Presenter{
        // 数据库第start开始，获取size个, f是否涩图
        void getImages(int start, int size, boolean isH);
    }

    interface View extends BaseContract.View<Presenter>{
        void onLoad(List<String> imgList, boolean f);
    }
}
