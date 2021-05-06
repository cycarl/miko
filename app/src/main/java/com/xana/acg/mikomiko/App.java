package com.xana.acg.mikomiko;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.task.DownloadTask;
import com.xana.acg.Factory;
import com.xana.acg.com.app.Application;

import java.io.File;

import static android.view.inputmethod.InputMethod.SHOW_FORCED;
import static com.xana.acg.com.Common.Avatar;

public class App extends Application {

    private static App single;

    @Override
    public void onCreate() {
        super.onCreate();
        Factory.setup();
        single = this;
        Aria.download(this).register();
    }

    public static void hintKb(View view) {
        if(view==null) return;
        view.clearFocus();
        InputMethodManager imm = (InputMethodManager)single.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void showKb(View view){
        if(view==null) return;
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager)single.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }


    public static boolean kbOpen(){
        InputMethodManager imm = (InputMethodManager)single.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

    public static App getSingle() {
        return single;
    }

    private static final String local = Environment.getExternalStorageDirectory().getPath()+"/Download";

    public static void download(String uri, String filename){
//        M3U8VodOption option = new M3U8VodOption(); // 创建m3u8点播文件配置
        File file = new File(local+"/mikomiko");
        if(!file.exists())
            file.mkdir();
        String local = file.getPath();
        long taskId = Aria.download(getSingle())
                .load(uri)     //读取下载地址
                .setFilePath(local+String.format("/%s.jpg", filename)) //设置文件保存的完整路径
                .create();   //创建并启
    }


    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        //在这里处理任务完成的状态
        showToast("下载完成！"+task.getFilePath());
    }

}
