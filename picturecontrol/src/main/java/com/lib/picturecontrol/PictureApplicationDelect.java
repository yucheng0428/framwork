package com.lib.picturecontrol;

import android.Manifest;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.common.base.ApplicationDelegate;
import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.Utils;
import com.lib.picturecontrol.album.LocalImageHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class PictureApplicationDelect implements ApplicationDelegate {
    @Override
    public void onCreate() {
        if (Utils.hasPermission(BaseApp.getIns(), Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            LocalImageHelper.init(BaseApp.getIns());
        }else {
            Utils.grantedPermissions(BaseApp.getIns(), new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    "获取拍照、定位获取本地位置权限");
        }
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(BaseApp.getIns()));
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
