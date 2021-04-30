package com.xana.acg.com.widget.phonecode;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.xana.acg.com.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 类：FocusPhoneCode
 * 作者： qxc
 * 日期：2018/3/14.
 */
public class FocusPhoneCode extends RelativeLayout{
    private Context context;
    private EditText et_code1;
    private EditText et_code2;
    private EditText et_code3;
    private EditText et_code4;
    private List<String> codes = new ArrayList<>();
    private InputMethodManager imm;

    public FocusPhoneCode(Context context) {
        super(context);
        this.context = context;
        loadView();
    }

    public FocusPhoneCode(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        loadView();
    }

    private void loadView(){
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = LayoutInflater.from(context).inflate(R.layout.focus_phone_code, this);
        initView(view);
        initEvent();
    }

    private void initView(View view){
        et_code1 = (EditText) view.findViewById(R.id.et_code1);
        et_code2 = (EditText) view.findViewById(R.id.et_code2);
        et_code3 = (EditText) view.findViewById(R.id.et_code3);
        et_code4 = (EditText) view.findViewById(R.id.et_code4);
    }

    private void initEvent(){
        et_code1.addTextChangedListener(new InputTextWatcher());
        et_code2.addTextChangedListener(new InputTextWatcher());
        et_code3.addTextChangedListener(new InputTextWatcher());
        et_code4.addTextChangedListener(new InputTextWatcher());

        et_code1.setOnKeyListener(new InputTextKeyListener());
        et_code2.setOnKeyListener(new InputTextKeyListener());
        et_code3.setOnKeyListener(new InputTextKeyListener());
        et_code4.setOnKeyListener(new InputTextKeyListener());

        et_code1.setOnFocusChangeListener(new InputTextOnFocus());
        et_code2.setOnFocusChangeListener(new InputTextOnFocus());
        et_code3.setOnFocusChangeListener(new InputTextOnFocus());
        et_code4.setOnFocusChangeListener(new InputTextOnFocus());
    }

    private boolean needListen = true;
    class InputTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(needListen){
                needListen = false;
                if (codes.size() < 4 && editable.length()>0) {
                    codes.add(editable.toString().substring(0,1));
                }
                showCode();
                needListen = true;
            }
        }
    }

    class InputTextKeyListener implements OnKeyListener{
        @Override
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
            if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN && codes.size() > 0) {
                codes.remove(codes.size() - 1);
                showCode();
                return true;
            }
            return false;
        }
    }

    
    class InputTextOnFocus implements OnFocusChangeListener{
        @Override
        public void onFocusChange(View view, boolean b) {
            if(b){
                setFocus();
            }
        }
    }

    /**
     * 显示输入的验证码
     */
    private void showCode() {
        if (codes.size() >= 1) {
            String code1 = codes.get(0);
            if(!code1.equals(et_code1.getText().toString())){
                et_code1.setText(code1);
            }
        }else{
            et_code1.setText("");
        }
        if (codes.size() >= 2) {
            String code2 = codes.get(1);
            if(!code2.equals(et_code2.getText().toString())) {
                et_code2.setText(code2);
            }
        }else{
            et_code2.setText("");
        }
        if (codes.size() >= 3) {
            String code3 = codes.get(2);
            if(!code3.equals(et_code3.getText().toString())) {
                et_code3.setText(code3);
            }
        }else{
            et_code3.setText("");
        }
        if (codes.size() >= 4) {
            String code4 = codes.get(3);
            if(!code4.equals(et_code4.getText().toString())) {
                et_code4.setText(code4);
            }
        }else{
            et_code4.setText("");
        }
        setFocus();
    }

    //设置焦点
    private void setFocus(){
        if (codes.size() == 0) {
            et_code1.setFocusable(true);
            et_code1.setFocusableInTouchMode(true);
            et_code1.requestFocus();
            et_code1.setSelection(et_code1.getText().length());
            showSoftInput(et_code1);
        }
        if (codes.size() == 1) {
            et_code2.setFocusable(true);
            et_code2.setFocusableInTouchMode(true);
            et_code2.requestFocus();
            et_code2.setSelection(et_code2.getText().length());
            showSoftInput(et_code2);
        }
        if (codes.size() == 2) {
            et_code3.setFocusable(true);
            et_code3.setFocusableInTouchMode(true);
            et_code3.requestFocus();
            et_code3.setSelection(et_code3.getText().length());
            showSoftInput(et_code3);
        }
        if (codes.size() >= 3) {
            et_code4.setFocusable(true);
            et_code4.setFocusableInTouchMode(true);
            et_code4.requestFocus();
            et_code4.setSelection(et_code4.getText().length());
            showSoftInput(et_code4);
        }
    }

    /**
     * 显示键盘
     * @param editText 输入框控件
     */
    private void showSoftInput(final EditText editText){
        //显示软键盘
        if(imm!=null && editText!=null) {
            editText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imm.showSoftInput(editText, 0);
                }
            },200);
        }
    }

    /**
     * 获得手机号验证码
     * @return 验证码
     */
    public String getPhoneCode(){
        StringBuilder sb = new StringBuilder();
        for (String code : codes) {
            sb.append(code);
        }
        return sb.toString();
    }
}
