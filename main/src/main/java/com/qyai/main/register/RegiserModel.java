package com.qyai.main.register;

import com.lib.common.BaseMvp.model.BaseModel;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.OnHttpCallBack;

public class RegiserModel extends BaseModel {
    RegiserPersenter persenter;

    public RegiserModel(RegiserPersenter persenter) {
        this.persenter = persenter;
    }

    public void reqRegiserHttp(Object para,String url){
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
