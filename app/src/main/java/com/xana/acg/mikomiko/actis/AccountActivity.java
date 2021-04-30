package com.xana.acg.mikomiko.actis;

import androidx.fragment.app.FragmentManager;

import com.xana.acg.com.app.Fragment;
import com.xana.acg.com.app.PresenterActivity;
import com.xana.acg.fac.model.account.MusicUser;
import com.xana.acg.fac.presenter.account.AccountContract;
import com.xana.acg.fac.presenter.account.AccountPresenter;
import com.xana.acg.mikomiko.IndexActivity;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.frags.account.PassInputFragment;
import com.xana.acg.mikomiko.frags.account.SmartInputFragment;

import io.vov.vitamio.utils.Log;


public class AccountActivity extends PresenterActivity<AccountContract.Presenter>
 implements AccountContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account;
    }

    public String smart;
    public String pass;
    public String captcha;

    @Override
    protected void initWidget() {
        super.initWidget();
        addFrag(new SmartInputFragment());
    }

    public void addFrag(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container, fragment)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();

    }

    public AccountContract.Presenter getPresenter(){
        return mPresenter;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        FragmentManager fm = getSupportFragmentManager();
        if(fm.getFragments().size()==1)
            super.onBackPressed();
        fm.popBackStack();
        showToast("点击返回");
    }

    @Override
    protected AccountContract.Presenter initPresenter() {
        return new AccountPresenter(this);
    }

    @Override
    public void switchFrag(boolean flag) {

        // 存在账号
        addFrag(new PassInputFragment(flag? 0: 1));
    }

    @Override
    public void onLoginSuccess(MusicUser extra) {
//        Log.e("MusicUser", extra.toString());
        navTo(IndexActivity.class);
        finish();
    }

    @Override
    public void onFail(String msg) {

    }
}