package com.xana.acg.com.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xana.acg.com.R;

import net.qiujuer.genius.ui.widget.Loading;

import java.util.List;

import butterknife.ButterKnife;

public abstract class Activity extends AppCompatActivity implements View.OnClickListener {


    private ViewStub viewStub;
    private LinearLayout mError;
    private Loading mLoading;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindows();
        if (initArgs(getIntent().getExtras())) {
            setContentView(R.layout.view_stub);
            init();
            initWidget();
            initData();
        } else {
            finish();
        }
    }

    public <T extends Activity> void navTo(Class<T> clzz, String... args) {
        Intent intent = new Intent(this, clzz);
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length / 2; ++i) {
                intent.putExtra(args[i], args[i + 1]);
            }
        }
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        retry();
    }

    public void retry() {
        initData();
    }

    /**
     * 初始化控件调用之前
     */
    protected void init() {

    }

    /**
     * 初始化窗口
     */
    protected void initWindows() {

    }

    /**
     * 初始化相关参数
     *
     * @param bundle
     * @return true参数正确
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    /**
     * 得到当前界面资源文件id
     *
     * @return 资源文件id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget() {
        viewStub = findViewById(R.id.view_stub);
        mError = findViewById(R.id.error);
        mLoading = findViewById(R.id.loading);
        mError.setOnClickListener(this);
        viewStub.setLayoutResource(getLayoutId());
        viewStub.inflate();
        ButterKnife.bind(this);
    }

    protected void initData() {

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
        if (fragments != null && fragments.size() > 0) {
            for (androidx.fragment.app.Fragment fragment : fragments) {
                if (fragment instanceof Fragment && ((Fragment) fragment).onBackPressed())
                    return;
            }
        }
        super.onBackPressed();
        finish();
    }

    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private void ok() {
        mError.setVisibility(View.GONE);
        viewStub.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.GONE);

    }

    private void error() {
        mError.setVisibility(View.VISIBLE);
        viewStub.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
    }

    /**
     * @param flag 0: ok, 1:loading,  2:fail, 3:noData,
     */
    protected void ok(int flag) {
        switch (flag) {
            case 0:
                ok();
                break;
            case 1:
                mLoading.setVisibility(View.VISIBLE);
                break;
            case 2:
                error();
                break;
        }
    }

}
