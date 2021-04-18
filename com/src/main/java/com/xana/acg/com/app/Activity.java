package com.xana.acg.com.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;

public abstract class Activity extends AppCompatActivity {

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindows();
        if(initArgs(getIntent().getExtras())) {
            setContentView(getLayoutId());
            init();
            initWidget();
            initData();
        }else {
            finish();
        }
    }

    public <T extends Context> void navTo(Class<T> clzz, String...args){
        Intent intent = new Intent(this, clzz);
        if(args!=null&&args.length>0){
            for (int i = 0; i < args.length/2; ++i) {
                intent.putExtra(args[i], args[i+1]);
            }
        }
        startActivity(intent);
    }

    /**
     * 初始化控件调用之前
     */
    protected void init(){

    }

    /**
     * 初始化窗口
     */
    protected void initWindows(){

    }

    /**
     * 初始化相关参数
     * @param bundle
     * @return true参数正确
     */
    protected boolean initArgs(Bundle bundle){
        return true;
    }

    /**
     * 得到当前界面资源文件id
     * @return 资源文件id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget(){
        ButterKnife.bind(this);
    }

    protected void initData(){

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        /* 获取当前Activity下的所有Fragment*/
        List<androidx.fragment.app.Fragment> fragments = getSupportFragmentManager().getFragments();
        if(fragments!=null && fragments.size()>0){
            for (androidx.fragment.app.Fragment fragment : fragments) {
                if(fragment instanceof Fragment && ((Fragment) fragment).onBackPressed())
                    return;
            }
        }
        super.onBackPressed();
        finish();
    }

    public void showToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

}
