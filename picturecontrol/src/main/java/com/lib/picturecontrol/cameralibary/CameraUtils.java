package com.lib.picturecontrol.cameralibary;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.view.TextureView;
import android.view.View;

/**
 * Created by Bert on 2017/2/21.
 */
public class CameraUtils {

    public static CameraUtils instance = null;
    private SurfaceTexture mSurfaceTexture;
    private TextureView.SurfaceTextureListener callback;
    private static final String TAG = "CameraUtils";
    public static int CAMERA_BACK = 0;
    public static int CAMERA_FRONT = 1;
    private int current_camrea = CAMERA_BACK;

    public static CameraUtils getInstance() {
        if(instance == null){
            instance = new CameraUtils();
        }
        return instance;
    }

    public void openCamera(final TextureView mSv, final Context mContext){
        callback = new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                mSurfaceTexture = surface;
                CameraHelper.getInstance().operationCamera(true, (Activity) mContext,mSv,mSurfaceTexture,current_camrea);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                CameraHelper.getInstance().operationCamera(false, (Activity) mContext,mSv,mSurfaceTexture,current_camrea);
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        };
        mSv.setSurfaceTextureListener(callback);
    }

    public void openCamera(final TextureView mSv, final Context mContext, int is_front, final View maskView) {
        current_camrea = is_front;
        callback = new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                mSurfaceTexture = surface;
                CameraHelper.getInstance().operationCamera(true, (Activity) mContext,mSv,mSurfaceTexture,current_camrea);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                CameraHelper.getInstance().operationCamera(false, (Activity) mContext,mSv,mSurfaceTexture,current_camrea);
                maskView.setVisibility(View.VISIBLE);
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                if(maskView.getVisibility() == View.VISIBLE)
                    maskView.setVisibility(View.GONE);
            }
        };
        mSv.setSurfaceTextureListener(callback);
    }



    public void changeCamera(final TextureView mSv, final Context mContext){
        closeCamera(mSv,mContext);
        if(current_camrea == CAMERA_BACK){
            current_camrea = CAMERA_FRONT;
            CameraHelper.getInstance().operationCamera(true, (Activity) mContext,mSv,mSurfaceTexture,current_camrea);
        }else {
            current_camrea = CAMERA_BACK;
            CameraHelper.getInstance().operationCamera(true, (Activity) mContext,mSv,mSurfaceTexture,current_camrea);
        }

    }

    public void changeCamera(final TextureView mSv, final Context mContext, int direction){
        closeCamera(mSv,mContext);
            CameraHelper.getInstance().operationCamera(true, (Activity) mContext,mSv,mSurfaceTexture,direction);
        current_camrea = direction;
    }



    public void closeCamera(final TextureView mSv, final Context mContext){
        CameraHelper.getInstance().operationCamera(false, (Activity) mContext,mSv,mSurfaceTexture,current_camrea);
    }

}
