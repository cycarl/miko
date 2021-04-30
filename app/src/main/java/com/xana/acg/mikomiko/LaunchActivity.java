package com.xana.acg.mikomiko;


import com.xana.acg.com.app.Activity;

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
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                navTo(MainActivity.class);
                finish();
            }
        }.start();
    }
}