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
        LocalImageHelper.init(BaseApp.getIns());
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
