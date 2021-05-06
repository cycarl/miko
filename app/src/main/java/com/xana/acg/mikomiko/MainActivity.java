package com.xana.acg.mikomiko;

import android.view.View;

import com.xana.acg.com.app.Activity;
import com.xana.acg.mikomiko.actis.AccountActivity;

public class MainActivity extends Activity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    public void navToIndex(View view) {
        switch (view.getId()) {
            case R.id.btn_smart_login:
                navTo(AccountActivity.class);
                finish();
                return;
        }
        navTo(IndexActivity.class);
        finish();
    }


}