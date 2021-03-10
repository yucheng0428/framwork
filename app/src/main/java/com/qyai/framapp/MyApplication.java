package com.qyai.framapp;



import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.Common;
import com.lib.common.netHttp.HttpReq;


public class MyApplication extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        Common.initARouter(this);
        HttpReq.getInstence().setIp("http://124.71.140.164:16808/");
//        HttpReq.getInstence().setIp("http://172.16.1.236:16800/");
    }

}
