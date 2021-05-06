package com.xana.acg.fac.model.api;

public class Resp {
    private int code;
    private String msg;
    private String message = "";
    private boolean more;

    private boolean hasmore;
    public boolean refresh;

    public boolean success(){
        return code==CODE.Sucess;
    }
    public boolean hasMore(){return more||hasmore;}

    public String getMsg() {
        return msg==null?message:msg;
    }

    public String getMessage() {
        return message;
    }


    public boolean empty(){
        return false;
    }

    public <T extends Resp> T setRefresh(boolean refresh){
        this.refresh = refresh;
        return (T) this;
    }

    public boolean isRefresh() {
        return refresh;
    }
}
interface CODE{
    int Sucess = 200;
    int Not_found = 404;
    int Error = 500;
    int Forbidden = 403;
    int Unavailable = 503;
    int No_Login = 301;
}