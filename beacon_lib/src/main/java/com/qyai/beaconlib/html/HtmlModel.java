package com.qyai.beaconlib.html;

import com.lib.common.BaseMvp.model.BaseModel;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.OnHttpCallBack;
import com.qyai.beaconlib.login.bean.UserEvent;
import com.qyai.beaconlib.utlis.Constants;


public class HtmlModel extends BaseModel {
    
    HtmlPersener persener;

    public HtmlModel(HtmlPersener persener) {
        this.persener = persener;
    }


    public void queryBlueToothInfo(int id,Object para) {
        HttpServiec.getInstance().postFlowableData(id, Constants.QUERYBLUETOOTHINFO,para, new OnHttpCallBack<UserEvent>() {
            @Override
            public void onSuccessful(int id, UserEvent baseResult) {
                persener.reqSuccessful(id,baseResult);
            }

            @Override
            public void onFaild(int id, UserEvent baseResult, String err) {
                persener.reqErro(id,baseResult,err);
            }
        },UserEvent.class);
    }

    public void addBlueToothInfo(int id,Object para) {
        HttpServiec.getInstance().postFlowableData(id, Constants.ADDBLUETOOTHINFO,para, new OnHttpCallBack<UserEvent>() {
            @Override
            public void onSuccessful(int id, UserEvent baseResult) {
                persener.reqSuccessful(id,baseResult);
            }

            @Override
            public void onFaild(int id, UserEvent baseResult, String err) {
                persener.reqErro(id,baseResult,err);
            }
        },UserEvent.class);
    }

    public void delectBlueToothInfo(int id,Object para) {
        HttpServiec.getInstance().postFlowableData(id, Constants.DELECTBLUETOOTHINFO,para, new OnHttpCallBack<UserEvent>() {
            @Override
            public void onSuccessful(int id, UserEvent baseResult) {
                persener.reqSuccessful(id,baseResult);
            }

            @Override
            public void onFaild(int id, UserEvent baseResult, String err) {
                persener.reqErro(id,baseResult,err);
            }
        },UserEvent.class);
    }

    public void updateBlueToothInfo(int id,Object para) {
        HttpServiec.getInstance().postFlowableData(id, Constants.UPDATEBLUETOOTHINFO,para, new OnHttpCallBack<UserEvent>() {
            @Override
            public void onSuccessful(int id, UserEvent baseResult) {
                persener.reqSuccessful(id,baseResult);
            }

            @Override
            public void onFaild(int id, UserEvent baseResult, String err) {
                persener.reqErro(id,baseResult,err);
            }
        },UserEvent.class);
    }
}
