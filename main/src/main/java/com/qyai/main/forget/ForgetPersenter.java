package com.qyai.main.forget;

import com.lib.common.BaseMvp.presenter.BasePresenter;
import com.qyai.main.login.bean.UserEvent;

public class ForgetPersenter extends BasePresenter<ForgetView> {
    final  ForgetModel forgetModel;

    public ForgetPersenter() {
        this.forgetModel = new ForgetModel(this);
    }

    public void reqForget(Object para){
        getMvpView().showLodingDialog();
        forgetModel.reqForgetHttp(para,"");
    }

    public void reqSuccessful(int id, Object baseResponse) {
        switch (id) {
            case 100:
                getMvpView().hidLodingDialog();
                getMvpView().nextForget((String) baseResponse);
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
