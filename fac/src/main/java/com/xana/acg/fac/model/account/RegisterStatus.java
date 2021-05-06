package com.xana.acg.fac.model.account;

import com.xana.acg.fac.model.api.RespModel;

public class RegisterStatus  extends RespModel {


    /**
     * exist : 1
     * code : 200
     * nickname : 某***姬
     * hasPassword : true
     */
    private int exist;
    private String nickname;
    private boolean hasPassword;

    public boolean existed(){
        return exist>0;
    }

    public int getExist() {
        return exist;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isHasPassword() {
        return hasPassword;
    }
}

