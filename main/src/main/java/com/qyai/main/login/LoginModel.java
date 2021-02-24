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

    public void LoginReq(final String name, final String password,String LOGIN_URL) {
        Map<Object, Object> para = new HashMap<>();
        para.put("account", name);
        para.put("pwd", Utils.md5(password));
        para.put("loginType", "phone");
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
}
