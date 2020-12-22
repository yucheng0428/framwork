package com.qyai.main.changepsw;

import com.lib.common.BaseMvp.presenter.BasePresenter;

public class ChangePswPersenter extends BasePresenter<ChangePswView> {
    final ChangePswModel forgetModel;

    public ChangePswPersenter() {
        this.forgetModel = new ChangePswModel(this);
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
