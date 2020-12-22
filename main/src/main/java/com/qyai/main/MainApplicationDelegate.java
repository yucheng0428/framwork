package com.qyai.main;

import com.lib.common.base.ApplicationDelegate;
import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.Common;

public class MainApplicationDelegate implements ApplicationDelegate {
    @Override
    public void onCreate() {
        Common.initARouter(BaseApp.getIns());
    }

    @Override
    public void onTerminate() {

    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onTrimMemory(int level) {

    }
}
