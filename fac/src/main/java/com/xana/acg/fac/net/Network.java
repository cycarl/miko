package com.xana.acg.fac.net;


import com.xana.acg.Factory;
import com.xana.acg.fac.priavte.Account;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.xana.acg.com.Common.HEADER.contentType;
import static com.xana.acg.com.Common.SEVER.ANIME;
import static com.xana.acg.com.Common.SEVER.MUSIC;
import static com.xana.acg.com.Common.SEVER.SELF;

/**
 * 网络请求封装
 */
public class Network {

    private static Network instance;
    private Retrofit retrofit;

    private Retrofit musicRetrofit;

    private Retrofit animeRetrofit;
    static {
        instance = new Network();
    }
    private Network(){}

    public static Retrofit getRetrofit(String server){
        switch (server){
            case SELF:
                if(instance.retrofit!=null)
                    return instance.retrofit;
                return getRetrofit(instance.retrofit, server);
            case MUSIC:
                if(instance.musicRetrofit!=null)
                    return instance.musicRetrofit;
                return getRetrofit(instance.musicRetrofit, server);

            case ANIME:
                if(instance.animeRetrofit!=null)
                    return instance.animeRetrofit;
                return getRetrofit(instance.animeRetrofit, server);
        }
        return null;
    }

    private static Retrofit getRetrofit(Retrofit retrofit, String server) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request req = chain.request();
                        Request.Builder builder = req.newBuilder();
//                        if(!TextUtils.isEmpty(Account.getToken())){
//                            /* 注入一个token */

                        String cookie = Account.getCookie();
                        if(Account.getCookie()!=null)
                            builder.addHeader("Cookie", cookie);

                        builder.addHeader("Content-type", contentType);

//                        }
                        Request request = builder.build();
                        return chain.proceed(request);
                    }
                }).build();
        Retrofit.Builder builder = new Retrofit.Builder();

        retrofit =  builder.baseUrl(server)
                .client(client)
                // 设置 json解析器
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .build();
        return retrofit;
    }

    /**
     * 返回一个网络请求代理
     * @return
     */
    public static Api remote(String server){
        return Network.getRetrofit(server).create(Api.class);
    }

}
