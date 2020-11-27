package com.qyai.main.login;


import com.lib.common.BaseMvp.presenter.BasePresenter;
import com.lib.common.baseUtils.Constants;
import com.lib.common.netHttp.HttpReq;
import com.qyai.main.login.bean.UserEvent;

public class LoginPersenter extends BasePresenter<LoginView> {

    final LoginModel model;

    public LoginPersenter() {
        model = new LoginModel(this);
    }

    public void loginding(String name, String password) {
        getMvpView().showLodingDialog();
        model.LoginReq(name, password,HttpReq.getInstence().getIp() +"login/login");
    }


    public void reqSuccessful(int id, Object baseResponse) {
        switch (id) {
            case 100:
                getMvpView().hidLodingDialog();
                getMvpView().loginNext((UserEvent) baseResponse);
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
