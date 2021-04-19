package com.lib.picturecontrol.cameralibary;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.lib.common.base.BaseActivity;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.FileUtils;
import com.lib.common.baseUtils.IntentKey;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.lib.picturecontrol.R;
import com.lib.picturecontrol.R2;
import com.lib.picturecontrol.cameralibary.InterFace.CameraOpenListener;
import com.lib.picturecontrol.cameralibary.InterFace.TakePhotoSuccListener;
import com.lib.picturecontrol.cameralibary.wegit.ShutterView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 自定义相机界面
 * Created by GYH on 2017/6/22.
 */

public class CameraActivity extends BaseActivity implements CameraOpenListener {
    private final static float BASE_PIC_MEMORY = 90f;

    @BindView(R2.id.cv)
    public CameraView cv;
    @BindView(R2.id.bt_change)
    public ImageView bt_change;
    @BindView(R2.id.bt_take)
    public ShutterView bt_take;
    @BindView(R2.id.bt_reLocation)
    public Button bt_reLocation;
    @BindView(R2.id.bt_graffiti)
    TextView bt_graffiti;
    @BindView(R2.id.iv_bg)
    public ImageView imageView;
    private boolean showWeaterMask = false;
    private String projName;
    private String filePath;
    private Bitmap tempBitmap;
    private ArrayList<String> filePathList;
    private ArrayList<String> tempPicPathList;//临时拍照图片路径集合，在本地相册地址列表filePathList排序结束后添加至列表前
    private boolean hasLoopEnd = false;
    private boolean isContinue = false;//是否连拍
    private int SAVEPIC_FLAG = 0;

    private int totalMemory;

    @Override
    protected int layoutId() {
        return R.layout.camera_activity;
    }

    @Override
    protected void initUIData(Bundle bundle) {
        cv.open(this, this);
        if (getIntent().hasExtra(CameraIntentKey.CAMERA_PIC_PATH))
            filePath = getIntent().getStringExtra(CameraIntentKey.CAMERA_PIC_PATH);
        else
            filePath = FileUtils.createFilePng(this).getAbsolutePath();
        isContinue = getIntent().getBooleanExtra(CameraIntentKey.CAMERA_PIC_COUTINUE, false);
        filePathList = new ArrayList<>();
        tempPicPathList = new ArrayList<>();
        showWeaterMask = getIntent().getBooleanExtra(CameraIntentKey.CAMERA_PIC_WATERMASK, true);
        projName = getIntent().getStringExtra(CameraIntentKey.CAMERA_PIC_PROJNAME);
        cv.showWaterMask(showWeaterMask, projName);

        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        totalMemory = am.getLargeMemoryClass();
        asyncErgodicFiles();
    }


    /**
     * 遍历文件夹下图片
     */
    private void asyncErgodicFiles() {
        new AsyncTask<String, Integer, List<String>>() {
            @Override
            protected void onPreExecute() {
                hasLoopEnd = false;
            }

            @Override
            protected List<String> doInBackground(String... params) {
                filePathList.clear();
                ergodicFiles(FileUtils.getFilePath(Common.STORAGE_PICTURE, mActivity));
                return filePathList;
            }

            @Override
            protected void onPostExecute(List<String> s) {
                hasLoopEnd = true;
                if (tempPicPathList.size() > 0) {
                    s.addAll(0, tempPicPathList);
                    tempPicPathList.clear();
                }
                if (s.size() > 0) {
//                    fileInner.setImageUrlAndSaveLocal(s.get(0), true, ImageView.ScaleType.CENTER_CROP);
//                    filePathList.clear();
//                    filePathList.addAll(s);
                } else {

                }
            }
        }.execute();
    }


    private void ergodicFiles(String path) {
        File file = new File(path);
        File[] subFiles = file.listFiles();
        for (File sub : subFiles) {
            if (!sub.isDirectory()) {
                String fileName = sub.getName();
                if (fileName.trim().toLowerCase().endsWith(".jpg") || fileName.trim().toLowerCase().endsWith(".jpeg")
                        || fileName.trim().toLowerCase().endsWith(".png") || fileName.trim().toLowerCase().endsWith(".gif")) {
                    filePathList.add(FileUtils.getFilePath(Common.STORAGE_PICTURE, mActivity) + fileName);
                }
            }
        }
    }

    @OnClick({R2.id.bt_change, R2.id.bt_take, R2.id.iv_back,
            R2.id.bt_reLocation, R2.id.pic_file_inner,
            R2.id.bt_graffiti})
    public void onClick(View view) {
        if (view.getId() == R.id.bt_change) {
            cv.ChangeCamera();
        }
        if (view.getId() == R.id.bt_take) {
            try {
                if (bt_take.getStatus() == ShutterView.SHUTTER_STATUS_UNTAKE) {
                    int currentMemory = getMemory();
                    if (totalMemory - (BASE_PIC_MEMORY + CameraHelper.getInstance().getCurrentPicSize()) < currentMemory) {
                        UIHelper.ToastMessage(this, "点击过于频繁，请稍后再试");
                        bt_take.setUnable();
                        return;
                    }

                    bt_take.setStatus(ShutterView.SHUTTER_STATUS_DOING);
                    cv.takePhoto(new Camera.ShutterCallback() {
                        @Override
                        public void onShutter() {
                        }
                    }, filePath, new TakePhotoSuccListener() {
                        @Override
                        public void takePhotoComplete(Bitmap bitmap) {
                            tempBitmap = bitmap;
                            bt_reLocation.setVisibility(View.GONE);
                            imageView.setVisibility(View.VISIBLE);
                            imageView.setImageBitmap(bitmap);
                            bt_change.setVisibility(View.GONE);
                            bt_take.startAnimation("确定");
                            bt_graffiti.setVisibility(View.VISIBLE);
                        }
                    });
                } else if (bt_take.getStatus() == ShutterView.SHUTTER_STATUS_TAKE) {
                    int[] fileLoca = new int[2];
                    int[] preViewLoca = new int[2];
//                        fileInner.getLocationInWindow(fileLoca);
                    imageView.getLocationInWindow(preViewLoca);
                    AnimationSet animationSet = new AnimationSet(true);
                    animationSet.addAnimation(new ScaleAnimation(1.0f, .0f, 1.0f, .0f));
                    animationSet.addAnimation(new TranslateAnimation(0, fileLoca[0] - preViewLoca[0], 0, fileLoca[1] - preViewLoca[1]));
                    animationSet.setDuration(300);
                    animationSet.setFillAfter(false);
                    animationSet.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            tempPicPathList.add(0, filePath);
                            if (hasLoopEnd) {
                                filePathList.addAll(0, tempPicPathList);
                                tempPicPathList.clear();
//                                    fileInner.setImageUrlAndSaveLocal(filePathList.get(0), true, ImageView.ScaleType.CENTER_CROP);
//                                    fileInner.setVisibility(View.VISIBLE);
                            }
                            hideConfigView();
                            bt_take.resetStatus();
                            bt_graffiti.setVisibility(View.GONE);
                            bt_change.setVisibility(View.VISIBLE);
                            filePath = FileUtils.createFilePng(CameraActivity.this).getAbsolutePath();
                            SPValueUtil.saveStringValue(CameraActivity.this, Common.CAMERA_TEMP_PATH, filePath);
                            SAVEPIC_FLAG = 0;
                            tempBitmap.recycle();
                            tempBitmap = null;
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });

                    if (isContinue) {
                        if (SAVEPIC_FLAG == 0) {
                            CUtilts.getInstance().saveBitmap(tempBitmap, filePath);
                            imageView.startAnimation(animationSet);
                            SAVEPIC_FLAG = 1;
                        }
                    } else {
                        CUtilts.getInstance().saveBitmap(tempBitmap, filePath);
                        tempBitmap.recycle();
                        tempBitmap = null;
                        setResult(RESULT_OK);
                        finish();
                    }
//                        ImageUtils.addImage(getContentResolver(), filePath);
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri uri = Uri.fromFile(new File(filePath));
                    intent.setData(uri);
                    sendBroadcast(intent);
                }
            } catch (Exception e) {
                if (tempBitmap != null) {
                    tempBitmap.recycle();
                    tempBitmap = null;
                }
                bt_take.setStatus(ShutterView.SHUTTER_STATUS_UNTAKE);
                e.printStackTrace();
            }
        }
        if (view.getId() == R.id.bt_reLocation) {
            cv.resetLocaInfo();
        }
        if (view.getId() == R.id.iv_back) {
            if (imageView.getVisibility() == View.VISIBLE) {
                hideConfigView();
                bt_take.resetStatus();
                bt_change.setVisibility(View.VISIBLE);
                bt_graffiti.setVisibility(View.GONE);
//                    bt_reLocation.setVisibility(View.GONE);
            } else
                finish();
        }
        if (view.getId() == R.id.pic_file_inner) {
            Intent intent = new Intent(this, LookPictureAct.class);
            intent.putStringArrayListExtra(IntentKey.CHECK_FILE_PATH, filePathList);
            startActivity(intent);
        }
        if (view.getId() == R.id.bt_graffiti) {
            if (tempBitmap != null) {
                // 涂鸦参数

                /*
                // 橡皮擦底图，如果为null，则底图为当前图片路径
                params.mEraserPath = "/storage/emulated/0/tencent/MicroMsg/WeiXin/mmexport1485172092678.jpg";
                //  橡皮擦底图是否调整大小，如果为true则调整到跟当前涂鸦图片一样的大小．
                params.mEraserImageIsResizeable = true;
                // 设置放大镜的倍数，当小于等于0时表示不使用放大器功能.放大器只有在设置面板被隐藏的时候才会出现
                params.mAmplifierScale = 2.5f;
                */
//                    GraffitiAct.startActivityForResult(CameraActivity.this, params, IntentKey.REQ_CODE_GRAFFITI);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (bt_take.getStatus() == ShutterView.SHUTTER_STATUS_TAKE) {
            hideConfigView();
            bt_take.resetStatus();
            bt_change.setVisibility(View.VISIBLE);
            bt_graffiti.setVisibility(View.GONE);
//            bt_reLocation.setVisibility(View.GONE);
        } else
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        cv.destory();
        if (tempBitmap != null) {
            tempBitmap.recycle();
            tempBitmap = null;
        }
        super.onDestroy();
    }

    /**
     * 隐藏拍照结果页面
     */
    public void hideConfigView() {
        imageView.setVisibility(View.GONE);
        imageView.setImageBitmap(null);
    }


    @Override
    public void openFailed() {
        bt_take.setClickable(false);
        cv.setClickable(false);
        bt_change.setClickable(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private int getMemory() {
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        //获得系统里正在运行的所有进程
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessesList = mActivityManager.getRunningAppProcesses();
        int total = 0;
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessesList) {
            // 进程ID号
            int pid = runningAppProcessInfo.pid;
            // 用户ID
            int uid = runningAppProcessInfo.uid;
            // 进程名
            String processName = runningAppProcessInfo.processName;
            // 占用的内存
            int[] pids = new int[]{pid};
            Debug.MemoryInfo[] memoryInfo = mActivityManager.getProcessMemoryInfo(pids);
            total = memoryInfo[0].dalvikPrivateDirty;
        }
        return total / 1024;
    }

    public interface CameraIntentKey {
        String CAMERA_PIC_PATH = "camera_output";
        String CAMERA_PIC_WATERMASK = "camera_watermask";//是否显示水印
        String CAMERA_PIC_COUTINUE = "camera_continue";
        String CAMERA_PIC_PROJNAME = "camear_projname";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentKey.REQ_CODE_GRAFFITI) {
            if (data == null) {
                return;
            }
        }
    }


}
