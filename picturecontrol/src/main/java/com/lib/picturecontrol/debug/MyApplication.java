package com.lib.picturecontrol.debug;


import android.app.Application;
import com.lib.common.baseUtils.Common;
import com.lib.picturecontrol.album.LocalImageHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LocalImageHelper.init(this);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        Common.initARouter(this);
    }
}
