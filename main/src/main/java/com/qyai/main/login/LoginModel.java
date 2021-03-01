package com.qyai.main.login;

import android.text.TextUtils;

import com.lib.common.BaseMvp.model.BaseModel;
import com.lib.common.baseUtils.Utils;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.OnHttpCallBack;
import com.qyai.main.login.bean.UserEvent;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LoginModel extends BaseModel {

    public LoginPersenter mPerrsenter;
    public LoginModel(LoginPersenter loginPersenter) {
        mPerrsenter = loginPersenter;
    }

    public void LoginReq(Map<Object,Object>para,String LOGIN_URL) {
        HttpServiec.getInstance().postFlowableMap(100, LOGIN_URL,para, new OnHttpCallBack<String>() {
            @Override
            public void onSuccessful(int id, String baseResult) {
                try {
                    mPerrsenter.reqSuccessful(id,baseResult);
                }catch (Exception e){

                }
            }

            @Override
            public void onFaild(int id, String baseResult, String err) {
                   mPerrsenter.reqErro(id,baseResult,err);
            }
        },String.class);

    }

    public void getVerificationCode(String phone, String url) {
        Map<String, String> para = new HashMap<>();
        HttpServiec.getInstance().getFlowbleData(101, url+phone,para, new OnHttpCallBack<String>() {
            @Override
            public void onSuccessful(int id, String baseResult) {
                try {
                    mPerrsenter.reqSuccessful(id,baseResult);
                }catch (Exception e){

                }
            }

            @Override
            public void onFaild(int id, String baseResult, String err) {
                mPerrsenter.reqErro(id,baseResult,err);
            }
        },String.class);
    }
}
