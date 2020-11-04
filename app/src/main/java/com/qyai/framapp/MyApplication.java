package com.qyai.framapp;

import android.Manifest;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.Utils;
import com.lib.picturecontrol.album.LocalImageHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MyApplication extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        initARouter();
        if (Utils.hasPermission(this, Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            LocalImageHelper.init(this);
        }else {
            Utils.grantedPermissions(this, new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    "获取拍照、定位获取本地位置权限");
        }
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
    }

    private void initARouter() {
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    public boolean isDebug() {
        return Constants.isDebug();
    }
}
