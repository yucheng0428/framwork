package com.qyai.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.lib.common.BaseMvp.BaseMvpAct;
import com.lib.common.BaseMvp.factory.CreateMvpPresenter;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.IntentKey;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.baseUtils.permssion.PermissionCheckUtils;
import com.lib.common.dialog.IphoneDialog;
import com.lib.common.netHttp.HttpReq;
import com.lib.common.netHttp.NetHeaderInterceptor;
import com.qyai.main.R;
import com.qyai.main.R2;
import com.qyai.main.forget.ForgetActivity;
import com.qyai.main.login.bean.UserEvent;
import com.qyai.main.register.RegiserActivity;

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
    @BindView(R2.id.tv_register)
    TextView tv_register;
    @BindView(R2.id.tv_forget_psw)
    TextView tv_forget_psw;
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
        setTranslucentNavigationColor(getResources().getColor(R.color.half_transparent));
        et_password.setText(psw);
        et_user.setText(userName);
        setScreenModel(2);
        Common.openGPSSEtting(mActivity);
        PermissionCheckUtils.requestPermissions(mActivity, Constants.REQUEST_CODE, Common.permissionList); // 动态请求权限
    }


    @OnClick({R2.id.btn_login, R2.id.login_logo,R2.id.tv_forget_psw,R2.id.tv_register})
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            if (!TextUtils.isEmpty(getUserName()) && !TextUtils.isEmpty(getPassWord())) {
                getMvpPresenter().loginding(getUserName(), getPassWord());
            }
        } else if (v.getId() == R.id.login_logo) {
            IphoneDialog iphoneDialog = new IphoneDialog(mActivity, new IphoneDialog.IphoneListener() {
                @Override
                public void upContent(String string) {
                    if (string.contains("http://")) {
                        HttpReq.getInstence().setIp(string);
                    }
                }
            }, true, "输入ip",  HttpReq.getInstence().getIp());
            iphoneDialog.show();
        }else  if(v.getId()==R.id.tv_register){
            Intent intent=new Intent(mActivity, RegiserActivity.class);
            startActivityForResult(intent,IntentKey.REQ_UPLAOD);

        }else if(v.getId()==R.id.tv_forget_psw){
            Intent intent=new Intent(mActivity, ForgetActivity.class);
            startActivityForResult(intent,IntentKey.REQ_DELECT);
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
        heard.put("token", userData.getUserInDeptDTO().getToken());
        NetHeaderInterceptor.getInterceptor().setHeaders(heard);
        String json = JSON.toJSONString(userData);
        SPValueUtil.saveStringValue(mActivity, Common.USER_DATA, json);
        SPValueUtil.saveStringValue(mActivity, Common.USER_TOKEN, userData.getUserInDeptDTO().getToken() + "");
        SPValueUtil.saveStringValue(mActivity, Common.USER_PASSWORD, getPassWord());
        SPValueUtil.saveStringValue(mActivity, Common.USER_NAME, getUserName());
//        Intent intent = new Intent(LoginAct.this, SechAct.class);
//        startActivity(intent);
        ARouter.getInstance().build("/bracelet/SechAct")
                .navigation();
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
