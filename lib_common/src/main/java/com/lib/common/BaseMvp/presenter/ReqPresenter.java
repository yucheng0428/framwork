package com.lib.common.BaseMvp.presenter;

import com.lib.common.BaseMvp.view.BaseView;

public abstract class ReqPresenter<T extends BaseView> extends BasePresenter<T> {
    public abstract void reqSuccessful(int id, Object baseResponse);

    public abstract void reqErro(int id, Object baseResponse, String err);
}
