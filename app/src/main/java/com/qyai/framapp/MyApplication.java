package com.qyai.framapp;



import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.Common;
import com.lib.common.netHttp.HttpReq;
import com.qyai.watch_app.message.bean.JusClassResult;

import java.util.ArrayList;
import java.util.List;


public class MyApplication extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        Common.initARouter(this);
//        HttpReq.getInstence().setIp(Common.HTTP_REQ);
        HttpReq.getInstence().setIp("http://172.16.1.235:16800/");

}















}
