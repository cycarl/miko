package com.xana.acg.com.data;

import androidx.annotation.StringRes;

public interface DataSource {

    /**
     * 回调接口
     * @param <T>
     */
    interface Callback<T> extends SucceedCallback<T>, FailedCallback{

    }

    interface SucceedCallback<T>{
        void onDataLoaded(T data);
    }
    interface FailedCallback{
        void onDataNotAvailable(@StringRes int strRes);
    }

    /**
     * 销毁操作
     */
    void dispose();
}
