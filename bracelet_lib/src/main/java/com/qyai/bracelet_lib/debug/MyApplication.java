package com.qyai.bracelet_lib.debug;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.Constants;
import com.yucheng.ycbtsdk.AITools;
import com.yucheng.ycbtsdk.YCBTClient;

public class MyApplication extends BaseApp {

    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
        YCBTClient.initClient(getApplicationContext(), false);
        AITools.getInstance().Init();
//        CrashHandler.getInstance().init(myApplication);
        initARouter();
    }

    public static Context getAppContext() {
        return myApplication;
    }

    private void initARouter() {
        if (Constants.isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }
}
