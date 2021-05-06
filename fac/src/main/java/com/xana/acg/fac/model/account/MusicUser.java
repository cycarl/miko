package com.xana.acg.fac.model.account;

import com.xana.acg.fac.model.api.Resp;

public class MusicUser extends Resp {
    /**
     * cookie : MUSIC_U=4d1c6370e1c69fbc278ecac5aa893e278b902793aa507c3304e8ba374be21b37f8eba1d7ed3f0aca; Max-Age=1296000; Expires=Sat, 15 May 2021 07:08:30 GMT; Path=/;;__csrf=93b76e8bd1c4e18e40973f7680e3e269; Max-Age=1296010; Expires=Sat, 15 May 2021 07:08:40 GMT; Path=/;;__remember_me=true; Max-Age=1296000; Expires=Sat, 15 May 2021 07:08:30 GMT; Path=/;
     * token : 4d1c6370e1c69fbc278ecac5aa893e278b902793aa507c3304e8ba374be21b37f8eba1d7ed3f0aca
     */
    private String cookie;
    private String token;
    private Profile profile;


    public String getCookie() {
        return cookie;
    }

    public String getToken() {
        return token;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getId(){
        return getProfile().userId;
    }



    @Override
    public String toString() {
        return "MusicUser{" +
                "cookie='" + cookie + '\'' +
                ", token='" + token + '\'' +
                ", profile=" + profile +
                '}';
    }
}
