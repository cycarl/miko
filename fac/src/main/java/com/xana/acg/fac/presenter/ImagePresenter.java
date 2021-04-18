package com.xana.acg.fac.presenter;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.helper.ImageHelper;


import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

public class ImagePresenter extends BasePresenter<ImageContract.View>
    implements ImageContract.Presenter, DataSource.SucceedCallback<List<String>> {

    public ImagePresenter(ImageContract.View view) {
        super(view);
    }

    @Override
    public void onDataLoaded(List<String> data) {

        ImageContract.View view = getView();
        if(view!=null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.onLoad(data, isH);
                }
            });
        }
    }

    private boolean isH;

    @Override
    public void get(int start, int size, boolean isH) {
        this.isH = isH;
        ImageHelper.get(start, size, isH, this);
    }
}
