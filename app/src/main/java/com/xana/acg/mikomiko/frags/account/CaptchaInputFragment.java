package com.xana.acg.mikomiko.frags.account;


import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.xana.acg.com.app.Application;
import com.xana.acg.com.app.Fragment;
import com.xana.acg.com.widget.phonecode.PhoneCode;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.actis.AccountActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class CaptchaInputFragment extends Fragment {

    @BindView(R.id.edit_captcha)
    PhoneCode mCaptcha;

    @BindView(R.id.tv_smart)
    TextView mSmart;

    @BindView(R.id.tv_resend)
    TextView mResend;

    public AccountActivity activity;

    private int waitTime = 59;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AccountActivity) context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_captcha_input;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mSmart.setText(String.format(getString(R.string.label_smart), activity.smart));
    }

    @Override
    protected void initData() {
        super.initData();
        mCaptcha.setOnCompleteListener(new PhoneCode.OnCompleteListener() {
            @Override
            public void onComplete(PhoneCode phoneCode) {
                Application.showToast(phoneCode.getPhoneCode());
                activity.captcha = phoneCode.getPhoneCode();
                activity.getPresenter().verifyCaptcha(activity.smart, activity.captcha);
            }
        });
        activity.getPresenter().sendCaptcha(activity.smart);

        new ResendTask().start();
    }

    @OnClick(R.id.tv_resend)
    void send(){
        new ResendTask().start();
        activity.getPresenter().sendCaptcha(activity.smart);
    }

    private class ResendTask extends Thread{
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void run() {
            mResend.setEnabled(false);
            mResend.setTextColor(activity.getColor(R.color.grey));
            while (waitTime>0){
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mResend.setText(String.format(getString(R.string.label_reverse_second), waitTime));
                        --waitTime;
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mResend.setEnabled(true);
                    mResend.setText(R.string.label_verify_resend);
                    mResend.setTextColor(activity.getColor(R.color.blue_200));
                }
            });
            waitTime = 59;
        }
    }
}