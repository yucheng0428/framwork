package com.qyai.main.changepsw;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.common.BaseMvp.BaseMvpHeadAct;
import com.lib.common.BaseMvp.factory.CreateMvpPresenter;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.qyai.main.R;
import com.qyai.main.R2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

@CreateMvpPresenter(ChangePswPersenter.class)
@Route(path = "/main/changePsw")
public class ChangePswActivity extends BaseMvpHeadAct<ChangePswView, ChangePswPersenter> implements ChangePswView {
    @BindView(R2.id.et_user)
    EditText et_user;
    @BindView(R2.id.iv_open2)
    ImageView iv_open2;
    @BindView(R2.id.et_password_agan)
    EditText et_password_agan;
    @BindView(R2.id.iv_open)
    ImageView iv_open;
    @BindView(R2.id.et_password)
    EditText et_password;
    @BindView(R2.id.btn_ok)
    Button btn_ok;
    Disposable disposable;
    boolean isShow=false;
    @Override
    public int layoutId() {
        return R.layout.activity_forget;
    }

    @Override
    protected void initUIData() {
        setTvTitle(getResources().getString(R.string.title_change));
    }

    @OnClick({ R2.id.btn_ok,R2.id.iv_open,R2.id.iv_open2})
    public void onClick(View v) {
        if (v.getId() == R.id.btn_ok) {
            if (!TextUtils.isEmpty(userName()) && !TextUtils.isEmpty(passWord())) {
                Map<Object,Object> para=new HashMap<>();
                para.put("userName",userName());
                para.put("password",passWord());
                para.put("code",sendCode());
                getMvpPresenter().reqForget(para);
            }
        } else if(v.getId()==R.id.iv_open){
            if(isShow){
                isShow=false;
                et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }else {
                isShow=true;
                et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); //密码可见
            }
        } else if(v.getId()==R.id.iv_open2){
            if(isShow){
                isShow=false;
                et_password_agan.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }else {
                isShow=true;
                et_password_agan.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); //密码可见
            }
        }
    }

    @Override
    public void showLodingDialog() {
        showProgress();
    }

    @Override
    public void hidLodingDialog() {
        hindProgress();
    }

    @Override
    public void showErrMsg(String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public String userName() {
        String userName = et_user.getText().toString().trim();
        if (!TextUtils.isEmpty(userName)) {
            return userName;
        } else {
            UIHelper.ToastMessage(mActivity, "请输入密码");
            return "";
        }
    }

    @Override
    public String passWord() {
        String password = et_password.getText().toString().trim();
        if (!TextUtils.isEmpty(password)) {
            return password;
        } else {
            UIHelper.ToastMessage(mActivity, "请输入新密码");
            return "";
        }
    }

    @Override
    public String sendCode() {
        String code= et_password_agan.getText().toString().trim();
        if (!TextUtils.isEmpty(code)) {
            return code;
        } else {
            UIHelper.ToastMessage(mActivity, "请再次输入新密码");
            return "";
        }
    }

    @Override
    public void nextForget(String s) {
        showErrMsg(s);
    }
}