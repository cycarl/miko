package com.xana.acg.com.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xana.acg.com.R;
import net.qiujuer.genius.ui.widget.Loading;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class Fragment extends androidx.fragment.app.Fragment
        implements OnClickListener {
    protected View mRoot;
    protected Unbinder mRootUnbinder;

    private ViewStub viewStub;
    private LinearLayout mError;
    private Loading mLoading;

    protected boolean isFirstInitData = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initArgs(getArguments());
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            /* 初始化当前根布局， 但是不在创建时就添加到container里面 */
            View root = inflater.inflate(R.layout.view_stub, container, false);
            initWidget(root);
            mRoot = root;
            return root;
        } else if (mRoot.getParent() != null) {
            ((ViewGroup) mRoot.getParent()).removeView(mRoot);
        }
        return mRoot;
    }

    @Override
    public void onClick(View v) {
        retry();
    }

    protected void retry() {
        initData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isFirstInitData) {
            onFirstInit();
            isFirstInitData = false;
        }
        initData();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected void initWidget(View root) {
        viewStub = root.findViewById(R.id.view_stub);
        mError = root.findViewById(R.id.error);
        mLoading = root.findViewById(R.id.loading);
        mError.setOnClickListener(this);
        viewStub.setLayoutResource(getLayoutId());
        viewStub.inflate();
        mRootUnbinder = ButterKnife.bind(this, root);
    }

    protected void initData() {
    }

    protected void onFirstInit() {
    }

    protected void initArgs(Bundle bundle) {
    }

    /**
     * 返回按键触发时调用
     *
     * @return true代表我已处理返回逻辑，Activity不用finish
     * false 代表没有处理
     */
    public boolean onBackPressed() {
        return false;
    }

    @Nullable
    protected Activity acti() {
        return (Activity) getActivity();
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
            case 3:
                ok();
                empty(true);
        }
    }

    public void empty(boolean ept){

    }
}
