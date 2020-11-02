package com.qyai.beaconlib;

import android.content.Context;

import com.lib.common.base.BaseApp;
import com.lib.common.base.CrashHandler;

public class MyApplication extends BaseApp {

    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
//        CrashHandler.getInstance().init(myApplication);
    }

    public static Context getAppContext() {
        return myApplication;
    }
}
