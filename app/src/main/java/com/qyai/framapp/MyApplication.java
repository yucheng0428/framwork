package com.qyai.framapp;


import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.Constants;
import com.lib.common.netHttp.HttpReq;
import com.qyai.bracelet_lib.MianApplication;

import java.lang.reflect.Method;


public class MyApplication extends BaseApp {
    private MianApplication mainApplication;//module的Application映射
    @Override
    public void onCreate() {
        super.onCreate();
        initARouter();
        //同步Module的Application的onCreate
        if (mainApplication != null){
            mainApplication.onCreate();//用于执行module的一些自定义初始化操作
        }
        HttpReq.getInstence().setIp("http://192.168.10.130:16810/");
    }

    private void initARouter() {
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        mainApplication = (MianApplication) getModuleApplicationInstance(this);
        try {
            //通过反射调用moduleApplication的attach方法
            Method method = Application.class.getDeclaredMethod("attach", Context.class);
            if (method != null) {
                method.setAccessible(true);
                method.invoke(mainApplication, getBaseContext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //映射获取ModuleApplication
    private MianApplication getModuleApplicationInstance(Context paramContext) {
        try {
            if ( mainApplication == null) {
                ClassLoader classLoader = paramContext.getClassLoader();
                if (classLoader != null) {
                    Class<?> mClass = classLoader.loadClass(MianApplication.class.getName());
                    if (mClass != null)
                        mainApplication = (MianApplication) mClass.newInstance();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mainApplication;
    }
    public boolean isDebug() {
        return Constants.isDebug();
    }

}
