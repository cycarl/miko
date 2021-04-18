package com.xana.acg.com;

public interface Common {

    interface KEY{
        String SMART = "SMART";
        String PASS = "PASS";
        String NICK = "NICK";
        String CAPTCHA = "CAPTCHA";
    }


    interface API{
//        String BASE_URL = "http://10.0.2.2:3000";
        String BASE_URL = "https://music.cn.utools.club";

        // 验证码
        String SEND_CODE = "/captcha/sent";
        String VERIFY_CODE = "/captcha/verify";

        // 登录注册
        String LOGIN = "/login/cellphone";
        String RIGISTER = "/register/cellphone";
        // 是否注册
        String VERIFY_SMART = "/cellphone/existence/check";

        interface FIND{
            // 推荐歌单
            String RECOM_SONG_LIST = "/personalized?limit=6";
        }
    }

    interface SEVER{
        String MY = "https://mikomiko.cn.utools.club";
        String MUSIC = "http://60.205.204.182:3000";
        String ANIME = "http://60.205.204.182:6001";
    }

    interface REGEX{
        String SMART = "^1[34578][0-9]{9}$";
        String PASS = "^.{4,32}$";
        String NICK = "^.{2,10}$";
    }
    interface ACCOUNT{
        String COOKIE1 = "";
        String COOKIE = "__csrf=b3d3c73b79170797015bdb484d98036e; Max-Age=1296010; Expires=Mon, 26 Apr 2021 14:41:06 GMT; Path=/;;__remember_me=true; Max-Age=1296000; Expires=Mon, 26 Apr 2021 14:40:56 GMT; Path=/;;MUSIC_U=4d1c6370e1c69fbc278ecac5aa893e276b0299929432e696bb2687abd2be7b4ce0235a7f03fa9bf1; Max-Age=1296000; Expires=Mon, 26 Apr 2021 14:40:56 GMT; Path=/;";
    }

    interface URI{
        String MUSIC = "https://music.163.com/song/media/outer/url?id=%s.mp3";
    }


}
