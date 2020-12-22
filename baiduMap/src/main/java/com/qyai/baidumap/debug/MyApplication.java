package com.qyai.baidumap.debug;

import android.app.Service;
import android.os.Vibrator;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.LogUtil;
import com.qyai.baidumap.service.LocationService;

/**
 * 单独调试时的初始化 application
 */
public class MyApplication extends BaseApp {
    public LocationService locationService;
    public Vibrator mVibrator;
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.e("init", "初始化地图sdk");
        locationService = new LocationService(BaseApp.getIns());
        mVibrator = (Vibrator) BaseApp.getIns().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(BaseApp.getIns());
        SDKInitializer.setCoordType(CoordType.BD09LL);
        Common.initARouter(BaseApp.getIns());
    }
}
