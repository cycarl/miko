package com.xana.acg.fac.model;

public class TbUser {
    private String id;
    private String nickname;
    private String password;
    private String avatarUrl;

    public TbUser(String id, String nickname, String password, String avatarUrl) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.avatarUrl = avatarUrl;
    }

    public boolean equals(TbUser obj) {
        if(obj==null)
            return false;
        return  id.equals(obj.id)&&
                nickname.equals(obj.nickname)&&
                password.equals(obj.password)&&
                avatarUrl.equals(obj.avatarUrl);
    }
}