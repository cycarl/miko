package com.xana.acg.mikomiko.frags.account;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.xana.acg.com.app.Fragment;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.actis.AccountActivity;

import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

public class PassInputFragment extends Fragment {


    private static int id = 0;

    @BindView(R.id.edit_pass)
    EditText mPass;

    @BindView(R.id.btn_next)
    Button mNext;

    public PassInputFragment(int passId){
        id = passId;
    }


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
    protected void initWidget(View root) {
        super.initWidget(root);
        if(id==1){
            mPass.setHint(R.string.label_change_pass_hint);
            mNext.setText(R.string.label_next);
            root.findViewById(R.id.tv_change_pass).setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pass_input;
    }

    @OnClick({R.id.btn_next, R.id.tv_change_pass})
    void addFrag(View view){

        switch (view.getId()){
            case R.id.tv_change_pass:
                activity.addFrag(new PassInputFragment(1));
                return;

            case R.id.btn_next:
                String pass = mPass.getText().toString();
                if(id==0)
                    activity.getPresenter().login(activity.smart, pass);
                else
                    activity.addFrag(new CaptchaInputFragment());
        }

    }

}