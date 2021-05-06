package com.xana.acg.mikomiko.frags.account;


import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.xana.acg.com.app.Application;
import com.xana.acg.com.app.Fragment;
import com.xana.acg.com.widget.phonecode.PhoneCode;
import com.xana.acg.mikomiko.App;
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
    public AccountActivity act;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        act = (AccountActivity) context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_captcha_input;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mSmart.setText(String.format(getString(R.string.label_smart), act.getSmart()));
    }

    @Override
    protected void initData() {
        super.initData();
        mCaptcha.setOnCompleteListener(new PhoneCode.OnCompleteListener() {
            @Override
            public void onComplete(PhoneCode phoneCode) {
                act.click(3, phoneCode.getPhoneCode());
            }
        });
        act.click(2, null);
        new ResendTask().start();
    }

    @OnClick(R.id.tv_resend)
    void send() {
        new ResendTask().start();
        act.click(2, null);
    }

    private class TaskHanlder extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mResend.setEnabled(false);
                    mResend.setText(String.format(getString(R.string.label_reverse_second), msg.arg1));
                    break;
                case 1:
                    mResend.setEnabled(true);
                    mResend.setText(R.string.label_verify_resend);
                    break;
            }
        }
    }
    private TaskHanlder task = new TaskHanlder();

    private class ResendTask extends Thread {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void run() {
            int wait = 60;
            while (wait-- > 0) {
                Message msg = task.obtainMessage();
                msg.arg1 = wait;
                task.sendMessage(msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
            Message msg = task.obtainMessage(1);
            task.sendMessage(msg);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        act = null;
    }
}