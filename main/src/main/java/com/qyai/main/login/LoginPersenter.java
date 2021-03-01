package com.qyai.main.login;


import com.alibaba.fastjson.JSONObject;
import com.lib.common.BaseMvp.presenter.BasePresenter;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.Utils;
import com.lib.common.baseUtils.baseModle.BaseResult;
import com.lib.common.netHttp.HttpReq;
import com.qyai.main.login.bean.UserEvent;

import java.util.HashMap;
import java.util.Map;

public class LoginPersenter extends BasePresenter<LoginView> {

    final LoginModel model;

    public LoginPersenter() {
        model = new LoginModel(this);
    }

    public void loginding(String name, String password, int type) {
        getMvpView().showLodingDialog();
        Map<Object, Object> para = new HashMap<>();
        para.put("account", name);
        para.put("pwd", Utils.md5(password));
        para.put("loginType", "phone");
        if (type == 1) {
            model.LoginReq(para, HttpReq.getInstence().getIp() + "login/login");
        } else {
            model.LoginReq(para, HttpReq.getInstence().getIp() + "login/login");
        }
    }
    public void loginVerificationCode(String name, String code, int type) {
        Map<Object, Object> para = new HashMap<>();
        para.put("phoneNum", name);
        para.put("verifyCode", code);
        para.put("loginType", "phone");
        //验证码登录
        if (type == 2) {
            model.LoginReq(para, HttpReq.getInstence().getIp() + "login/verificationCodeLogin");
        }
    }

    /**
     * 获取验证码
     *
     * @param phone
     * @param type
     */
    public void getVerificationCode(String phone, int type) {
        if (type == 2) {
            if(phone.length()==11){
            model.getVerificationCode(phone, HttpReq.getInstence().getIp() + "login/getVerificationCode/");
            }else {
                getMvpView().showErrMsg("请输入11位手机号");
            }
        }
    }


    public void reqSuccessful(int id, Object baseResponse) {
        BaseResult event = JSONObject.parseObject((String) baseResponse, BaseResult.class);
        switch (id) {
            case 100:
                getMvpView().hidLodingDialog();
                if (event != null && event.getCode().equals("000000")) {
                    getMvpView().loginNext(JSONObject.parseObject((String) baseResponse, UserEvent.class));
                } else {
                    if (event != null) {
                        if (event.getCode().equals("502")) {
                            getMvpView().showErrMsg("密码错误");
                        } else {
                            getMvpView().showErrMsg(event.getMsg());
                        }
                    }

                }
                break;
            case 101:
                if(event != null && event.getCode().equals("000000")){
                    getMvpView().countDown();
                }else {
                    getMvpView().showErrMsg(event.getMsg());
                }
                break;
        }
    }

    public void reqErro(int id, Object baseResponse, String err) {
        getMvpView().hidLodingDialog();
        getMvpView().showErrMsg(err);
    }

}
