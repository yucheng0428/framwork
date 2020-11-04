package com.qyai.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSON;
import com.lib.common.BaseMvp.BaseMvpAct;
import com.lib.common.BaseMvp.factory.CreateMvpPresenter;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.netHttp.NetHeaderInterceptor;
import com.qyai.main.Common;
import com.qyai.main.R;
import com.qyai.main.R2;
import com.qyai.main.home.HomeAct;
import com.qyai.main.login.bean.UserEvent;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


@CreateMvpPresenter(LoginPersenter.class)
@Route(path = "/main/login")
public class LoginAct extends BaseMvpAct<LoginView, LoginPersenter> implements LoginView {

    @BindView(R2.id.et_user)
    EditText et_user;
    @BindView(R2.id.et_password)
    EditText et_password;
    @BindView(R2.id.btn_login)
    Button btn_login;

    @Autowired
    public String userName;
    @Autowired
    public String psw;
    @Override
    protected int layoutId() {
        return R.layout.login;
    }


    @Override
    protected void initUIData(Bundle bundle) {
        et_password.setText(psw);
        et_user.setText(userName);
        setScreenModel(2);
    }


    @OnClick({R2.id.btn_login})
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            if (!TextUtils.isEmpty(getUserName()) && !TextUtils.isEmpty(getPassWord())) {
                getMvpPresenter().loginding(getUserName(), getPassWord());
            }
        }

    }

    @Override
    public String getUserName() {
        String userName = et_user.getText().toString().trim();
        if (!TextUtils.isEmpty(userName)) {
            return userName;
        } else {
            UIHelper.ToastMessage(mActivity, "请填写账号");
            return "";
        }
    }

    @Override
    public String getPassWord() {
        String password = et_password.getText().toString().trim();
        if (!TextUtils.isEmpty(password)) {
            return password;
        } else {
            UIHelper.ToastMessage(mActivity, "请输入密码");
            return "";
        }
    }

    @Override
    public void loginNext(UserEvent baseBean) {
        UserEvent.UserData userData = baseBean.getData();
        Map<String, String> heard = new HashMap<>();
        heard.put("token", userData.getToken());
        NetHeaderInterceptor.getInterceptor().setHeaders(heard);
        String json = JSON.toJSONString(userData);
        SPValueUtil.saveStringValue(mActivity, Common.USER_DATA, json);
        SPValueUtil.saveStringValue(mActivity, Common.USER_TOKEN, userData.getToken() + "");
        SPValueUtil.saveStringValue(mActivity, Common.USER_PASSWORD, getPassWord());
        SPValueUtil.saveStringValue(mActivity, Common.USER_NAME, getUserName());
        Intent intent = new Intent(LoginAct.this, HomeAct.class);
        startActivity(intent);
        finish();
        btn_login.setEnabled(true);
        hidLodingDialog();
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
        UIHelper.ToastMessage(mActivity, msg);
    }
}
