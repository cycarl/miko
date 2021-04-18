package com.xana.acg.com.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class Fragment extends androidx.fragment.app.Fragment {
    protected View mRoot;
    protected Unbinder mRootUnbinder;

    protected boolean isFirstInitData = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initArgs(getArguments());
    }

    @Nullable
    @Override
    public final View  onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot==null){
            int id = getLayoutId();
            /* 初始化当前根布局， 但是不在创建时就添加到container里面*/
            View root = inflater.inflate(id, container, false);
            initWidget(root);
            mRoot = root;
            return root;
        }else if(mRoot.getParent()!=null){
            ((ViewGroup)mRoot.getParent()).removeView(mRoot);
        }
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(isFirstInitData){
            onFirstInit();
            isFirstInitData = false;
        }

        initData();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected void initWidget(View root){
        mRootUnbinder = ButterKnife.bind(this, root);
    }
    protected void initData(){

    }

    protected void onFirstInit(){

    }

    protected void initArgs(Bundle bundle){

    }

    /**
     * 返回按键触发时调用
     * @return true代表我已处理返回逻辑，Activity不用finish
     * false 代表没有处理
     */
    public boolean onBackPressed(){
        return false;
    }


    @Nullable
    protected Activity activity() {
        return (Activity) super.getActivity();
    }
}
