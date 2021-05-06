package com.xana.acg.mikomiko.actis.local;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import androidx.core.app.ActivityCompat;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.m3u8.M3U8VodOption;
import com.arialyy.aria.core.task.DownloadTask;
import com.xana.acg.com.app.ToolbarActivity;
import com.xana.acg.mikomiko.App;
import com.xana.acg.mikomiko.R;

import java.io.File;

import static com.xana.acg.com.Common.Avatar;

public class DownloadActivity extends ToolbarActivity {

    //    private String uri = "http://112.74.191.65:3001/proxy/anime/6b313038307c3537393633/0/0";
    private static String uri = Avatar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_download;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        App.download(uri, "休比");
    }


}