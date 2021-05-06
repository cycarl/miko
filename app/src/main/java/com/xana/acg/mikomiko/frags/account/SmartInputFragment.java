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

    @BindView(R.id.edit_smart)
    EditText mSmart;
    private AccountActivity act;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        act = (AccountActivity) context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_smart_input;
    }

    @OnClick({R.id.btn_next, R.id.iv_clear})
    void click(View view){
        if(view.getId()==R.id.btn_next) {
            String smart = mSmart.getText().toString();
            act.click(0, smart);
        }else {
            mSmart.setText("");
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        act = null;
    }
}