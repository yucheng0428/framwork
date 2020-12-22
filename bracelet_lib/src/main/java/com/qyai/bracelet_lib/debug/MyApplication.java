package com.qyai.bracelet_lib.debug;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;
import com.yucheng.ycbtsdk.AITools;
import com.yucheng.ycbtsdk.YCBTClient;

public class MyApplication extends BaseApp {


    @Override
    public void onCreate() {
        super.onCreate();
        YCBTClient.initClient(BaseApp.getIns(), false);
        AITools.getInstance().Init();
//        CrashHandler.getInstance().init(myApplication);
        Common.initARouter(BaseApp.getIns());
    }


}
