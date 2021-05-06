package com.xana.acg.mikomiko;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;

import androidx.core.app.ActivityCompat;

import com.xana.acg.com.app.Activity;
import com.xana.acg.fac.priavte.Account;
import com.xana.acg.mikomiko.actis.local.DownloadActivity;

public class LaunchActivity extends Activity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void init() {
        super.init();
        skip();
    }

    private void skip(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Account.isLogin())
                    navTo(IndexActivity.class);
                else navTo(MainActivity.class);
                finish();
            }
        }, 1000);
    }

}