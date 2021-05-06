package com.xana.acg.com.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.xana.acg.com.R;
import com.xana.acg.com.widget.BlurTransformation;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Application extends android.app.Application {
    private static Application instance;

    private final List<Activity> activityList = new LinkedList<>();
    private MediaPlayer mediaPlayer;
    private Date date;

    public static void setImage(View view, String uri){
        Glide.with(view.getContext())
                .load(uri)
                .bitmapTransform(new BlurTransformation(view.getContext()))
                .into(new ViewTarget<View, GlideDrawable>(view) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        view.setBackground(resource.getCurrent());
                    }
                });

    }

    public static synchronized MediaPlayer getMediaPlayer(){
        if(instance.mediaPlayer==null)
            instance.mediaPlayer = new MediaPlayer();
        return instance.mediaPlayer;
    }

    public static synchronized Date getDate(){
        if(instance.date==null)
            instance.date = new Date();
        return instance.date;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                activityList.add(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                activityList.remove(activity);
            }
        });
    }

    public void finishAll(){
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
        showLogin(this);

    }
    protected void showLogin(Context context){

    }

    /**
     * 外部获取单例
     * @return Application
     */
    public static Application getInstance() {
        return instance;
    }

    /**
     * 获取缓存文件夹地址
     * @return 当前APP的缓存文件夹地址
     */
    public static File getCacheDirFile() {
        return instance.getCacheDir();
    }

    /**
     * 获取头像的临时存储文件地址
     *
     * @return 临时文件
     */
    public static File getPortraitTmpFile() {
        // 得到头像目录的缓存地址
        File dir = new File(getCacheDirFile(), "portrait");
        // 创建所有的对应的文件夹
        //noinspection ResultOfMethodCallIgnored
        dir.mkdirs();

        // 删除旧的一些缓存为文件
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
        }

        // 返回一个当前时间戳的目录文件地址
        File path = new File(dir, SystemClock.uptimeMillis() + ".jpg");
        return path.getAbsoluteFile();
    }

    /**
     * 获取声音文件的本地地址
     * @param isTmp 是否是缓存文件， True，每次返回的文件地址是一样的
     * @return 录音文件的地址
     */
    public static File getAudioTmpFile(boolean isTmp) {
        File dir = new File(getCacheDirFile(), "audio");
        //noinspection ResultOfMethodCallIgnored
        dir.mkdirs();
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
        }

        // aar
        File path = new File(getCacheDirFile(), isTmp ? "tmp.mp3" : SystemClock.uptimeMillis() + ".mp3");
        return path.getAbsoluteFile();
    }

    /**
     * 显示一个Toast
     * @param msg 字符串
     */
    public static void showToast(final String msg) {
        // 保证一定是在主线程进行的show操作
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                Toast.makeText(instance, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 显示一个Toast
     * @param msgId 传递的是字符串的资源
     */
    public static void showToast(@StringRes int msgId) {
        showToast(instance.getString(msgId));
    }

}
