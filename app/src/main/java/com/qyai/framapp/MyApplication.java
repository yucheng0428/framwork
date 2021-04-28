package com.qyai.framapp;



import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.Common;
import com.lib.common.netHttp.HttpReq;


public class MyApplication extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        Common.initARouter(this);
//        HttpReq.getInstence().setIp(Constants.HTTP_REQ);
//        http://124.70.205.113:16869/
        HttpReq.getInstence().setIp("http://minghang.cn1.utools.club/");

}

    @Override
    public void onTerminate() {
        super.onTerminate();
        Common.destroyARouter();
    }
}
