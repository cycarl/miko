package com.xana.acg.fac.model.api;

public class RespModel<T> {

    public interface CODE{
        int Sucess = 200;
        int Not_found = 404;
        int Error = 500;
        int Forbidden = 403;
        int Unavailable = 503;
        int No_Login = 301;
    }


    private int code;
    private T result;

    private boolean hasmore;
    private T datas;
    private String msg;

    private int rcmdLimit;


    public int getRcmdLimit() {
        return rcmdLimit;
    }

    public RespModel() {
    }

    public RespModel(int code, T rsp) {
        this.code = code;
        this.result = rsp;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void setDatas(T datas) {
        this.datas = datas;
    }

    public T getDatas() {
        return datas;
    }

    public String getMsg() {
        return msg;
    }

    public boolean success(){
        return code==CODE.Sucess;
    }
    public boolean hasMore(){
        return hasmore;
    }

    @Override
    public String toString() {
        return "RespModel{" +
                "code=" + code +
                ", result=" + result +
                ", hasmore=" + hasmore +
                ", datas=" + datas +
                ", msg='" + msg + '\'' +
                ", rcmdLimit=" + rcmdLimit +
                '}';
    }
}
