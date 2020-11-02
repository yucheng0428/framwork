package com.qyai.framapp;

import android.Manifest;

import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.Utils;
import com.lib.picturecontrol.album.LocalImageHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MyApplication extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
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
}
