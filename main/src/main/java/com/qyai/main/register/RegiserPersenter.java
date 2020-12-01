package com.qyai.main.register;

import com.lib.common.BaseMvp.presenter.BasePresenter;
import com.qyai.main.forget.ForgetModel;

public class RegiserPersenter extends BasePresenter<RegiserView> {
    final RegiserModel forgetModel;

    public RegiserPersenter() {
        this.forgetModel = new RegiserModel(this);
    }

    public void reqForget(Object para){
        getMvpView().showLodingDialog();
        forgetModel.reqRegiserHttp(para,"");
    }

    public void reqSuccessful(int id, Object baseResponse) {
        switch (id) {
            case 100:
                getMvpView().hidLodingDialog();
                getMvpView().nextRegiser((String) baseResponse);
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
