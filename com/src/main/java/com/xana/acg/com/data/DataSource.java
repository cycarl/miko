package com.xana.acg.com.data;

public interface DataSource {

    /**
     * 回调接口
     * @param <T>
     */
    interface Callback<T> extends SucceedCallback<T>, FailedCallback{

    }

    interface SucceedCallback<T>{
        void success(T data);
    }
    interface FailedCallback{
        void fail(String msg);
    }

    /**
     * 销毁操作
     */
    void dispose();
}
