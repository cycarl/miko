package com.xana.acg.mikomiko.frags.account;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.xana.acg.com.app.Fragment;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.actis.AccountActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SmartInputFragment extends Fragment {



    public interface KEY{
        String TITLE = "TITLE";
        String SMART = "SMART";
        String PASS = "PASS";
        String NEW_PASS = "NEW_PASS";
        String CAPTCHA = "CAPTCHA";
    }

    @BindView(R.id.edit_smart)
    EditText mSmart;



    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);

    }

    private AccountActivity activity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AccountActivity) context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_smart_input;
    }

    @OnClick(R.id.btn_next)
    void addFrag(View view){
        String smart = mSmart.getText().toString();
        activity.smart = smart;
        activity.getPresenter().checkExist(smart);
    }
}