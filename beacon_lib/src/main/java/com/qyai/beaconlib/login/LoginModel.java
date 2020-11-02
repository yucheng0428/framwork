package com.qyai.beaconlib.login;

import android.text.TextUtils;

import com.lib.common.BaseMvp.model.BaseModel;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.OnHttpCallBack;
import com.qyai.beaconlib.login.bean.UserEvent;
import com.qyai.beaconlib.utlis.Constants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LoginModel extends BaseModel {

    public LoginPersenter mPerrsenter;
    public LoginModel(LoginPersenter loginPersenter) {
        mPerrsenter = loginPersenter;
    }

    public void LoginReq(final String name, final String password) {
        Map<Object, Object> para = new HashMap<>();
        para.put("type", "LoginApp");
        para.put("account", name);
        para.put("pwd", md5(password));
        para.put("loginType", "phone");
        HttpServiec.getInstance().postFlowableMap(100, Constants.LOGIN_URL,para, new OnHttpCallBack<UserEvent>() {
            @Override
            public void onSuccessful(int id, UserEvent baseResult) {
                mPerrsenter.reqSuccessful(id,baseResult);
            }

            @Override
            public void onFaild(int id, UserEvent baseResult, String err) {
                   mPerrsenter.reqErro(id,baseResult,err);
            }
        },UserEvent.class);

    }

    private String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
