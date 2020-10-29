package com.lib.picturecontrol.cameralibary.InterFace;

import android.graphics.Bitmap;

/**
 *
 * 拍照结束回调
 * Created by GYH on 2017/6/5.
 */

public interface TakePhotoSuccListener {
    void takePhotoComplete(Bitmap bitmap);
}
