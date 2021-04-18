package com.xana.acg;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xana.acg.com.app.Application;
import com.xana.acg.fac.utils.DBFlowExclusionStrategy;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Factory {
    private static final Factory instance;
    private final Executor executor;
    private final Gson gson;

    static {
        instance = new Factory();
    }

    private Factory() {
        // 新建一个线程池
        executor = Executors.newFixedThreadPool(4);
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                // 设置一个过滤器, 数据库级别的ModeL不进行Json转换
                .setExclusionStrategies(new DBFlowExclusionStrategy())
                .create();
    }

    /**
     * 返回全局Application
     * @return
     */
    public static Application app() {
        return Application.getInstance();

    }

    public static void runOnAsync(Runnable runnable) {
        // 异步执行
        instance.executor.execute(runnable);
    }

    public static Gson getGson() {
        return instance.gson;
    }



    /**
     * 收到账户退出的消息需要进行账户退出重新登录
     */
    private void logout() {

    }

}
