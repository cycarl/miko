package com.xana.acg.fac.net;

import com.xana.acg.Factory;
import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.utils.TextUtils;
import com.xana.acg.fac.model.api.Resp;

import java.io.IOException;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetCallBack<T extends Resp> implements Callback<T> {
    private DataSource.Callback callback;
    private boolean rf;

    public NetCallBack(){}

    public NetCallBack(DataSource.Callback callback){
        this.callback = callback;
    }

    public NetCallBack(DataSource.Callback callback, boolean rf){
       this(callback);
       this.rf = rf;
    }
    @Override
    public void onResponse(Call<T> call, Response<T> resp) {
        if(callback==null)
            return;
        Object o = resp.body();
        if(o instanceof Collection){
            if(((Collection) o).size()>0)
                callback.success(o);
            else callback.fail(null);
            return;
        }
        T body = resp.body();
        if(resp.isSuccessful()&&body.success()){
            if(body.empty()){
                callback.fail(null);
                return;
            }
            body.setRefresh(rf);
            callback.success(body);
        }else if(!resp.isSuccessful()){
            try {
                String s = resp.errorBody().string();
                if(!TextUtils.isEmpty(s)) callback.fail(Factory.getGson().fromJson(s, Resp.class).getMsg());
                else callback.fail(resp.message()+" code: "+resp.code());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else
            callback.fail(body.getMsg());
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if(callback==null) return;
        callback.fail("访问失败了！");
    }
}
