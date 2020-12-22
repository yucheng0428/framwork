package com.qyai.main.debug;


import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.Common;

public class MyApplication extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
       Common.initARouter(BaseApp.getIns());
    }

}
