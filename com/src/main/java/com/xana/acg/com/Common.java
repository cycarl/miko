package com.xana.acg.com;

public interface Common {

    String Avatar = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=963674467,2285766581&fm=26&gp=0.jpg";

    interface KEY{
        String SMART = "SMART";
        String PASS = "PASS";
        String NICK = "NICK";
        String CAPTCHA = "CAPTCHA";
    }
    interface DIGIT{
        int BAN = 10000;
        int HYAKUBAN = 1000000;
        long OKU = 100000000;

        long HYAKUBAN_MIN = 6000000;
    }

    interface SEVER{
        String SELF = "http://112.74.191.65:3003";
        String ANIME = "http://112.74.191.65:3001";
        String MUSIC = "http://112.74.191.65:3000";
    }

    interface REGEX{
        String SMART = "^1[34578][0-9]{9}$";
        String PASS = "^.{6,32}$";
        String NICK = "^.{2,10}$";
    }
    interface ACCOUNT{
        String COOKIE1 = "";
        String COOKIE = "MUSIC_U=4d1c6370e1c69fbc278ecac5aa893e2705ae21ae437b1f8a041d9fc2eb68d760f8eba1d7ed3f0aca";
        String token = "4d1c6370e1c69fbc278ecac5aa893e2705ae21ae437b1f8a929c2edab0e76fc9f8eba1d7ed3f0aca";
    }

    interface HEADER{
        String cookie = "Hm_lvt_9f472f02c404ee99590fa4402c1af609=1618035099; Hm_lpvt_9f472f02c404ee99590fa4402c1af609=1618035268; JSESSIONID=9C2696871863513B61F19DE4FA65A919; __csrf=f8490d355f36b5a229031964b4d7c30b; MUSIC_U=4d1c6370e1c69fbc278ecac5aa893e2705ae21ae437b1f8ab682f88a61899d8df8eba1d7ed3f0aca; __remember_me=true; NMTID=00OE8UaJMBQalRdaEezutk908VcyyIAAAF5NvE7Ew";
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36";
        String date = "Tue, 04 May 2021 11:06:58 GMT";
        String eTag = "W/\"80b-aoqJuV0sNeaP3Up1k1mTdAV9o7Q\"";
        String contentType = "application/json;charset=utf-8";
    }

    interface URI{
        String MUSIC = "https://music.163.com/song/media/outer/url?id=%s.mp3";
    }
}
