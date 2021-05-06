package com.xana.acg.fac.priavte;

import android.content.Context;
import android.content.SharedPreferences;

import com.xana.acg.Factory;
import com.xana.acg.fac.R;
import com.xana.acg.fac.model.account.MusicUser;

import static android.content.Context.MODE_PRIVATE;

public class Account {
    public static final String KEY_TOEKN = "KEY_TOEKN";
    public static final String KEY_USER_ID = "KEY_USER_ID";
    public static final String KEY_COOKIE =  "KEY_COOKIE";
    public static final String KEY_NICK =  "KEY_NICK";
    public static final String KEY_AVATAR =  "KEY_AVATAR";

    private static String token;
    private static String userId;
    private static String cookie;
    private static String nick;
    private static String avatar;


    private static void save(Context ctx){
        SharedPreferences sp = ctx.getSharedPreferences(ctx.getString(R.string.app_name), MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(KEY_TOEKN, token)
                .putString(KEY_USER_ID, userId)
                .putString(KEY_COOKIE, cookie)
                .putString(KEY_NICK, nick)
                .putString(KEY_AVATAR, avatar)
                .apply();
    }

    public static void load(Context ctx){
        SharedPreferences sp = ctx.getSharedPreferences(ctx.getString(R.string.app_name), MODE_PRIVATE);
        token =  sp.getString(KEY_TOEKN, null);
        userId =  sp.getString(KEY_USER_ID, null);
        cookie =  sp.getString(KEY_COOKIE, null);
        nick = sp.getString(KEY_NICK, null);
        avatar = sp.getString(KEY_AVATAR, null);
    }

    public static String getCookie() {
        return cookie;
    }

    public static String getToken() {
        return token;
    }

    public static String getUserId() {
        return userId;
    }

    public static boolean isLogin(){
        return userId!=null&&token!=null&&cookie!=null;
    }

    public static void login(MusicUser user) {
        token = user.getToken();
        userId = user.getId();
        cookie = user.getCookie();
        nick = user.getProfile().nickname;
        avatar = user.getProfile().avatarUrl;
        save(Factory.app());
    }

    public static void logout(){
        token = null;
        userId = null;
        cookie = null;
        save(Factory.app());
    }

    public static String getNick() {
        return nick;
    }

    public static String getAvatar() {
        return avatar;
    }


    private static String access;
    public static String getAccess(){
        return access;
    }
    public static void setAccess(String access) {
        Account.access = access;
    }
}
