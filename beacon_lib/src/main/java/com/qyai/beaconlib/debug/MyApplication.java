package com.qyai.beaconlib.debug;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;

public class MyApplication extends BaseApp {


    @Override
    public void onCreate() {
        super.onCreate();
       Common.initARouter(BaseApp.getIns());
//        CrashHandler.getInstance().init(myApplication);
    }

}
