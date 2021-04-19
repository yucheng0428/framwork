package com.qyai.baidumap;

import android.app.Service;
import android.os.Vibrator;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.lib.common.base.ApplicationDelegate;
import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.LogUtil;
import com.qyai.baidumap.service.LocationService;

public class MapApplicationDelegate implements ApplicationDelegate {

    @Override
    public void onCreate() {
        LogUtil.e("init", "初始化地图sdk");
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
