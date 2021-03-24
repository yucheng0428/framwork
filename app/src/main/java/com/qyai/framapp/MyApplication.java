package com.qyai.framapp;



import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.Common;
import com.lib.common.netHttp.HttpReq;


public class MyApplication extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        Common.initARouter(this);
//        HttpReq.getInstence().setIp(Common.HTTP_REQ);
        HttpReq.getInstence().setIp("http://172.16.1.232:16800/");
    }














}
