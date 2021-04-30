package com.xana.acg.fac.model.account;

import com.xana.acg.fac.model.api.RespModel;

public class MusicUser extends RespModel {
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

    public class Profile{
        /**
         * birthday : 962899200000
         * backgroundUrl : http://p2.music.126.net/_f8R60U9mZ42sSNvdPn2sQ==/109951162868126486.jpg
         * detailDescription :
         * gender : 2
         * city : 430100
         * signature : 私さ、その言葉を聞けれた時、もう救われたんだよ!
         * followeds : 0
         * description :
         * remarkName : null
         * eventCount : 0
         * playlistBeSubscribedCount : 0
         * accountStatus : 0
         * avatarImgId : 109951164967592020
         * defaultAvatar : false
         * avatarImgIdStr : 109951164967592010
         * backgroundImgIdStr : 109951162868126486
         * province : 430000
         * nickname : 某傲娇的只眼姬
         * expertTags : null
         * djStatus : 0
         * avatarUrl : https://p3.music.126.net/j3tKK6qA5PSGEjrzBAVXPQ==/109951164967592010.jpg
         * authStatus : 0
         * follows : 3
         * vipType : 0
         * userId : 1521838002
         * followed : false
         * mutual : false
         * avatarImgId_str : 109951164967592010
         * authority : 0
         * backgroundImgId : 109951162868126480
         * userType : 0
         * experts : {}
         * avatarDetail : null
         * playlistCount : 1
         */
        public long birthday;
        public String backgroundUrl;
        public String detailDescription;
        public int gender;
        public String signature;
        public String description;
        public String nickname;
        public String avatarUrl;
        public int vipType;
        public String userId;

        @Override
        public String toString() {
            return "Profile{" +
                    "birthday=" + birthday +
                    ", backgroundUrl='" + backgroundUrl + '\'' +
                    ", detailDescription='" + detailDescription + '\'' +
                    ", gender=" + gender +
                    ", signature='" + signature + '\'' +
                    ", description='" + description + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", avatarUrl='" + avatarUrl + '\'' +
                    ", vipType=" + vipType +
                    ", userId='" + userId + '\'' +
                    '}';
        }
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
