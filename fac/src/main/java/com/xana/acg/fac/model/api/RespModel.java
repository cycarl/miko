package com.xana.acg.fac.model.api;

public class RespModel<T> extends Resp{


    private T result;
    private T datas;

    private int rcmdLimit;


    public int getRcmdLimit() {
        return rcmdLimit;
    }

    public T getResult() {
        return result;
    }

    public T getDatas() {
        return datas;
    }
}
