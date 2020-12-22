package com.qyai.bracelet_lib;


import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.common.base.ApplicationDelegate;
import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.LogUtil;
import com.yucheng.ycbtsdk.AITools;
import com.yucheng.ycbtsdk.YCBTClient;

public class BraceletApplicationDelegate implements ApplicationDelegate {

    private static BraceletApplicationDelegate instance;

    @Override
    public void onCreate() {
        YCBTClient.initClient(BaseApp.getIns(), false);
        AITools.getInstance().Init();
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
