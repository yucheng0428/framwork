package com.qyai.main.forget;

import com.lib.common.BaseMvp.model.BaseModel;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.OnHttpCallBack;


public class ForgetModel extends BaseModel {
    ForgetPersenter persenter;

    public ForgetModel(ForgetPersenter persenter) {
        this.persenter = persenter;
    }

    public void reqForgetHttp(Object para,String url){
        HttpServiec.getInstance().postFlowableData(100, url,para, new OnHttpCallBack<String>() {
            @Override
            public void onSuccessful(int id, String baseResult) {
                persenter.reqSuccessful(id,baseResult);
            }

            @Override
            public void onFaild(int id, String baseResult, String err) {
                persenter.reqErro(id,baseResult,err);
            }
        },String.class);

    }
}
