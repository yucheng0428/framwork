package com.qyai.main.login;


import com.alibaba.fastjson.JSONObject;
import com.lib.common.BaseMvp.presenter.BasePresenter;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.baseModle.BaseResult;
import com.lib.common.netHttp.HttpReq;
import com.qyai.main.login.bean.UserEvent;

public class LoginPersenter extends BasePresenter<LoginView> {

    final LoginModel model;

    public LoginPersenter() {
        model = new LoginModel(this);
    }

    public void loginding(String name, String password,int type) {
        getMvpView().showLodingDialog();
        if(type==1){
            model.LoginReq(name, password,HttpReq.getInstence().getIp() +"login/login");
        }else {
            // TODO: 2020/12/17 这里写验证码登录接口 
            model.LoginReq(name, password,HttpReq.getInstence().getIp() +"login/login");
        }
    }


    public void reqSuccessful(int id, Object baseResponse) {
        switch (id) {
            case 100:
                getMvpView().hidLodingDialog();
                BaseResult event= JSONObject.parseObject((String) baseResponse,BaseResult.class);
                if(event!=null&&event.getCode().equals("000000")){
                    getMvpView().loginNext(JSONObject.parseObject((String) baseResponse,UserEvent.class));
                }else {
                    if(event!=null){
                        getMvpView().showErrMsg(event.getMsg());
                    }

                }
                break;
        }
    }

    public void reqErro(int id, Object baseResponse, String err) {
        switch (id) {
            case 100:
                getMvpView().hidLodingDialog();
                getMvpView().showErrMsg(err);
                break;

        }
    }

}
