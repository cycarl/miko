package com.xana.acg.mikomiko.frags.account;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xana.acg.com.app.Fragment;
import com.xana.acg.com.utils.TextUtils;
import com.xana.acg.mikomiko.R;
import com.xana.acg.mikomiko.actis.AccountActivity;

import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

import static com.xana.acg.fac.presenter.account.AccountPresenter.check;

public class InputFragment extends Fragment {


    private int id;

    @BindView(R.id.edit_pass)
    EditText mEdit;

    @BindView(R.id.btn_next)
    Button mNext;

    @BindView(R.id.tv_change_pass)
    TextView change;

    public InputFragment(int passId){
        id = passId;
    }

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
    }
    private AccountActivity act;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        act = (AccountActivity) context;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        // 更改密码
        if(id==1){
            mEdit.setHint(R.string.label_change_pass_hint);
            mNext.setText(R.string.label_next);
            change.setVisibility(View.GONE);
        }else if(id==2){
            mEdit.setHint("设置一个昵称");
            mNext.setText(R.string.label_enjoy_now);
            change.setVisibility(View.GONE);
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
                act.addFrag(new InputFragment(1));
                break;

            case R.id.btn_next:
                String msg = mEdit.getText().toString();
                if(id==0)
                    act.click(1, msg);
                else if (id == 2)
                    act.click(4, msg);
                else {
                    if(!check(null, msg, null)) return;
                    act.setPass(msg);
                    act.addFrag(new CaptchaInputFragment());
                }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        act = null;
    }
}