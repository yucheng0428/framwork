package com.qyai.beaconlib.login;


import com.lib.common.BaseMvp.presenter.BasePresenter;
import com.qyai.beaconlib.login.bean.UserEvent;

public class LoginPersenter extends BasePresenter<LoginView> {


    final LoginModel model;

    public LoginPersenter() {
        model = new LoginModel(this);
    }

    public void loginding(String name, String password) {
        getMvpView().showLodingDialog();
        model.LoginReq(name, password);
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
