package com.qyai.main.debug;


import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;
import com.lib.common.netHttp.HttpReq;

public class MyApplication extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
       Common.initARouter(BaseApp.getIns());
        HttpReq.getInstence().setIp(Constants.HTTP_REQ);
    }

}
