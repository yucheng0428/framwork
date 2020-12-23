package com.qyai.watch_app.debug;


import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.Common;

public class MyApplication extends BaseApp {


    @Override
    public void onCreate() {
        super.onCreate();
//        CrashHandler.getInstance().init(myApplication);
        Common.initARouter(this);
    }


}
