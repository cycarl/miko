package com.xana.acg.fac.presenter.music;


import com.xana.acg.com.data.DataSource;
import com.xana.acg.fac.net.NetCallBack;
import com.xana.acg.fac.net.Network;
import com.xana.acg.fac.presenter.IView;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import static com.xana.acg.com.Common.SEVER.ANIME;
import static com.xana.acg.com.Common.SEVER.MUSIC;

public class UriPresenter<T> implements DataSource.Callback<T> {

    private IView<T> view;

    public UriPresenter(IView<T> v){
        view = v;
    }

    public void getVidoeUri(String id){
        Network.remote(MUSIC).getVideoUri(id).enqueue(new NetCallBack(this));
    }

    public void getIptvUri(){
        Network.remote(ANIME).getIptv().enqueue(new NetCallBack(this));
    }

    @Override
    public void success(T data) {
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.onLoad(data);
            }
        });
    }

    @Override
    public void fail(String msg) {
        view.onFail(msg);
    }

    public void destory(){
        view=null;
    }
}
