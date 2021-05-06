package com.xana.acg.mikomiko.frags.dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.utils.TextUtils;
import com.xana.acg.fac.net.NetCallBack;
import com.xana.acg.fac.net.Network;
import com.xana.acg.fac.priavte.Account;
import com.xana.acg.mikomiko.App;
import com.xana.acg.mikomiko.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xana.acg.com.Common.SEVER.SELF;

public class SelfDialogFragment extends DialogFragment implements DataSource.Callback {

    private View view;

    private String passStr;

    private OnHLister lister;
    public SelfDialogFragment(OnHLister l){
        lister = l;
    }

    @BindView(R.id.edit_pass)
    EditText pass;
    @OnClick(R.id.tv_submit)
    void click(View view){
        passStr = this.pass.getText().toString();
        if(TextUtils.isEmpty(passStr)) {
            App.showToast(getString(R.string.label_h_pass_hint));
            return;
        }
        Network.remote(SELF).check(passStr).enqueue(new NetCallBack<>(this));
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_h_img, container, false);
        //设置dialog背景色为透明色
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //设置dialog窗体颜色透明
//        getDialog().getWindow().setDimAmount(0);
        initWidgt(view);
        initData();
        return view;
    }
    private void initData() {

    }
    private void initWidgt(View view) {
        ButterKnife.bind(this, view);
        App.showKb(pass);
    }
    @Override
    public void success(Object data) {
        Account.setAccess(passStr);
        if(lister!=null)
            lister.ok();
        dismiss();
    }

    @Override
    public void fail(String msg) {
        App.showToast(msg);
        pass.setText("");
    }

    public interface OnHLister{
        void ok();
    }
}
