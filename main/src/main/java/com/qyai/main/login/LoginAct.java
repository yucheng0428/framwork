package com.qyai.main.login;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


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
    @BindView(R2.id.iv_open)
    ImageView iv_open;
    @BindView(R2.id.tv_sendcode)
    TextView tv_sendcode;
    @Autowired
    public String userName;
    @Autowired
    public String psw;
    @Autowired
    public int viewType = Common.viewType;
    boolean isShow = false;
    boolean isChange = true;
    boolean isCode = false;
    Disposable disposable;

    @Override
    protected int layoutId() {
        return R.layout.login;
    }


    @Override
    protected void initUIData(Bundle bundle) {
        setTranslucentNavigationColor(getResources().getColor(R.color.half_transparent));
        et_password.setText(psw == null ? "888888" : psw);
        et_user.setText(userName == null ? "SH" : userName);
        loginType(viewType);
        setScreenModel(2);
        Common.openGPSSEtting(mActivity);
        PermissionCheckUtils.requestPermissions(mActivity, Constants.REQUEST_CODE, Common.permissionList); // 动态请求权限
    }

    //设置登录界面样式
    public void loginType(int type) {
        if (type == 2) {
            tv_register.setVisibility(View.GONE);
            tv_forget_psw.setText("验证码登录");
            tv_forget_psw.setTextColor(getResources().getColor(R.color.color_248bfe));
            tv_forget_psw.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else {
            tv_register.setVisibility(View.VISIBLE);
            tv_forget_psw.setText("忘记密码");
            tv_forget_psw.setTextColor(getResources().getColor(R.color.color_999999));
            tv_forget_psw.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.icon_wh), null, null, null);
        }
    }

    public void changeCode() {
        et_password.setText("");
        et_user.setText("");
        if (isChange) {
            et_user.setHint("请输入手机号");
            et_password.setHint("请输入验证码");
            tv_forget_psw.setText("密码登录");
            tv_sendcode.setVisibility(View.VISIBLE);
            iv_open.setVisibility(View.GONE);
            isChange = false;
            isCode = false;
            tv_sendcode.setText("发送验证码");
            if (disposable != null) {
                disposable.dispose();
            }
        } else {
            et_user.setHint("请输入用户名");
            et_password.setHint("请输入密码");
            tv_forget_psw.setText("验证码登录");
            tv_sendcode.setVisibility(View.GONE);
            iv_open.setVisibility(View.VISIBLE);
            isChange = true;
        }
    }

    @OnClick({R2.id.btn_login, R2.id.login_logo, R2.id.tv_forget_psw, R2.id.tv_register, R2.id.iv_open, R2.id.tv_sendcode})
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            if (!TextUtils.isEmpty(getUserName()) && !TextUtils.isEmpty(getPassWord())) {
                if (isChange) {
                    getMvpPresenter().loginding(getUserName(), getPassWord(), viewType);
                } else {
                    getMvpPresenter().loginVerificationCode(getUserName(), getPassWord(), viewType);
                }
            }
        } else if (v.getId() == R.id.login_logo) {
            IphoneDialog iphoneDialog = new IphoneDialog(mActivity, new IphoneDialog.IphoneListener() {
                @Override
                public void upContent(String string) {
                    if (string.contains("http://")) {
                        HttpReq.getInstence().setIp(string);
                    }
                }
            }, true, "输入ip", HttpReq.getInstence().getIp());
            if (Constants.isDebug()) {
                iphoneDialog.show();
            }

        } else if (v.getId() == R.id.tv_register) {
            Intent intent = new Intent(mActivity, RegiserActivity.class);
            startActivityForResult(intent, IntentKey.REQ_UPLAOD);

        } else if (v.getId() == R.id.tv_forget_psw) {
            if (viewType == 1) {
                Intent intent = new Intent(mActivity, ForgetActivity.class);
                startActivityForResult(intent, IntentKey.REQ_DELECT);
            } else {
                changeCode();
            }
        } else if (v.getId() == R.id.iv_open) {
            if (isShow) {
                isShow = false;
                et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                isShow = true;
                et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); //密码可见
            }
        } else if (v.getId() == R.id.tv_sendcode) {
            if (isCode) {
                return;
            }
            if (!SPValueUtil.isEmpty(et_user.getText().toString())) {
                showErrMsg("请填写手机号码");
                return;
            } else {
                getMvpPresenter().getVerificationCode(getUserName(), viewType);
            }

        }

    }

    @Override
    public String getUserName() {
        String userName = et_user.getText().toString().trim();
        if (!TextUtils.isEmpty(userName)) {
            return userName;
        } else {
            if (isChange) {
                UIHelper.ToastMessage(mActivity, "请输入账号");
            } else {
                UIHelper.ToastMessage(mActivity, "请输入手机号码");
            }
            return "";
        }
    }

    @Override
    public String getPassWord() {
        String password = et_password.getText().toString().trim();
        if (!TextUtils.isEmpty(password)) {
            return password;
        } else {
            if (isChange) {
                UIHelper.ToastMessage(mActivity, "请输入密码");
            } else {
                UIHelper.ToastMessage(mActivity, "请输入验证码");
            }
            return "";
        }
    }

    @Override
    public void loginNext(UserEvent baseBean) {

        UserEvent.UserData userData = baseBean.getData();
        Map<String, String> heard = new HashMap<>();
        heard.put("token", userData.getUserInDeptDTO().getToken());
        NetHeaderInterceptor.getInterceptor().setHeaders(heard);
        SPValueUtil.saveStringValue(mActivity, Common.USER_DATA, JSON.toJSONString(userData));
        SPValueUtil.saveStringValue(mActivity, Common.USER_TOKEN, userData.getUserInDeptDTO().getToken() + "");
        SPValueUtil.saveStringValue(mActivity,Common.USER_ID,userData.getUserInDeptDTO().getUserId());
        if(isChange){
            SPValueUtil.saveStringValue(mActivity, Common.USER_PASSWORD, getPassWord());
        }
        SPValueUtil.saveStringValue(mActivity, Common.USER_NAME, getUserName());
        btn_login.setEnabled(true);
        hidLodingDialog();
        if (viewType == 1) {
            ARouter.getInstance().build("/watch/HomeActivity").navigation();
        } else {
            ARouter.getInstance().build("/watch/HomeActivity2").navigation();
        }
        finish();
    }

    @Override
    public void countDown() {
        disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return 60 - (aLong + 1);
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(final Long count) throws Exception {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                isCode = true;
                                tv_sendcode.setText(count + "秒后重发");
                            }
                        });
                        if (count == 0) {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    isCode = false;
                                    tv_sendcode.setText("发送验证码");
                                }
                            });

                            if (disposable != null) {
                                disposable.dispose();
                            }
                        }
                    }
                });
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
