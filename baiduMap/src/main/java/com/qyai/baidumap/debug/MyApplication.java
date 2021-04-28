package com.qyai.baidumap.debug;

import android.app.Service;
import android.os.Vibrator;

import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.LogUtil;

/**
 * 单独调试时的初始化 application
 */
public class MyApplication extends BaseApp {
    public Vibrator mVibrator;
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.e("init", "初始化地图sdk");
        mVibrator = (Vibrator) BaseApp.getIns().getSystemService(Service.VIBRATOR_SERVICE);
        Common.initARouter(BaseApp.getIns());
    }
}
