package com.xana.acg.mikomiko.actis;

import androidx.fragment.app.FragmentManager;

import com.xana.acg.com.app.Fragment;
import com.xana.acg.com.app.PresenterActivity;
import com.xana.acg.fac.model.account.MusicUser;
import com.xana.acg.fac.presenter.account.AccountContract;
import com.xana.acg.fac.presenter.account.AccountPresenter;
import com.xana.acg.fac.priavte.Account;
import com.xana.acg.mikomiko.App;
import com.xana.acg.mikomiko.IndexActivity;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.frags.account.InputFragment;
import com.xana.acg.mikomiko.frags.account.SmartInputFragment;

import java.util.Stack;

public class AccountActivity extends PresenterActivity<AccountContract.Presenter>
 implements AccountContract.View {


    private Stack<Fragment> frags = new Stack<>();

    public boolean isRigister;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account;
    }

    private String smart;
    private String pass;
    private String captcha;
    private String nickname;

    @Override
    protected void initWidget() {
        super.initWidget();
        addFrag(new SmartInputFragment());
    }

    public void addFrag(Fragment fragment){
        frags.push(fragment);
        setFrag();
    }

    private void setFrag(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container, frags.peek())
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if(frags.size()==1) {
            frags.clear();
            super.onBackPressed();
            return;
        }
        frags.pop();
        setFrag();
    }

    @Override
    protected AccountContract.Presenter initPresenter() {
        return new AccountPresenter(this);
    }

    @Override
    public void checkExistOk(String smart, String nick) {
        // 是否存在账号
        ok(0);
        nickname = nick==null?nick:"";
        addFrag(new InputFragment(nickname==null? 1: 0));
    }

    @Override
    public void loginOk(MusicUser user) {
        Account.login(user);
        navTo(IndexActivity.class);
        finish();
    }

    @Override
    public void verifyOk(String code) {
        captcha = code;
        if(isRigister){
            addFrag(new InputFragment(2));
        }else click(4, nickname);
    }

    // 0 手机号是否存在，1登录，2发送验证码 3校验验证码 4注册/修改密码
    public void click(int id, String msg){
        switch (id){
            case 0:
                smart = msg;
                mPresenter.checkExist(smart);
                break;
            case 1:
                pass = msg;
                mPresenter.login(smart, pass);
                break;
            case 2:
                mPresenter.sendCaptcha(smart);
                break;
            case 3:
                captcha = msg;
                mPresenter.verifyCaptcha(smart, captcha);
                break;
            case 4:
                nickname = msg;
                mPresenter.register(smart, pass, nickname, captcha);
                break;
        }
    }

    @Override
    public void showMsg(String msg) {
        ok(0);
        showToast(msg);
    }

    public String getSmart() {
        return smart;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}