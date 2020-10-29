package com.lib.picturecontrol.tools;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.lib.common.base.BaseFragment;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.FileUtils;
import com.lib.common.baseUtils.IntentKey;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.lib.picturecontrol.R;
import com.lib.picturecontrol.album.LocalFileAct;
import com.lib.picturecontrol.album.LocalAlbum;
import com.lib.picturecontrol.album.LocalImageHelper;
import com.lib.picturecontrol.cameralibary.CameraActivity;


/**
 * <照片选择dlg> <功能详细描述>
 *
 * @author 姓名
 * @version [版本号, 2015年10月23日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PicpickDlg extends Dialog implements
        View.OnClickListener {

    Context context;

    BaseFragment fragment;

    private boolean isSingle = false;// 相册单选
    private boolean isShowSeleceFile = false;// 只选照片,false表示既能选照片也能选文件
    private boolean showWaterMask = true;//拍照显示水印

    public void setShowSeleceFile(boolean isShowSeleceFile) {
        this.isShowSeleceFile = isShowSeleceFile;
    }

    public void setSingle(boolean isSingle) {
        this.isSingle = isSingle;
    }

    public void isShowWaterMask(boolean showWaterMask){
        this.showWaterMask = showWaterMask;
    }

    public void setProjName(String projName){
        this.projName = projName;
    }

    public PicpickDlg(Context context, int picnums) {
        super(context, R.style.DialogTheme);
        setContentView(R.layout.dlg_pick_pic);

        if (0 == picnums) {

            LocalImageHelper.setMaxNum(3);
        } else {
            LocalImageHelper.setMaxNum(picnums);

        }
        setWindow();
        this.context = context;
        findViewById(R.id.btn_take_fujian).setOnClickListener(this);
        findViewById(R.id.btn_take_pic).setOnClickListener(this);
        findViewById(R.id.btn_album).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    public void setPicNums(int num) {
        if (num <= 0)
            LocalImageHelper.setMaxNum(3);
        else
            LocalImageHelper.setMaxNum(num);
    }

    public void setFragment(BaseFragment fragment) {
        this.fragment = fragment;
    }

    private void setWindow() {
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay();
        LayoutParams p = window.getAttributes();
        p.width = d.getWidth();
        p.height = LayoutParams.WRAP_CONTENT;
        window.setAttributes(p);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_take_pic) {
            dismiss();
            if (null != fragment) {

                notifyCamera(fragment);
            } else {

                notifyCamera((Activity) context);
            }
        } else if (id == R.id.btn_album) {
            dismiss();
            // 重新初始化相册

            if (null != fragment) {

                notifyAlbum(fragment);
            } else {
                notifyAlbum((Activity) context);

            }
        } else if (id == R.id.btn_take_fujian) {
            dismiss();
            if (null != fragment) {

                notifyFile(fragment);
            } else {
                notifyFile((Activity) context);

            }
        } else if (id == R.id.btn_cancel) {
            dismiss();
        }
    }

    /**
     * <相机拍照> <功能详细描述>
     *
     * @param context
     * @see [类、类#方法、类#成员]
     */
    public void notifyCamera(Activity context) {

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 返回大图
            imagepath = FileUtils.createFilePng(context).getAbsolutePath();
            Intent intent = new Intent(this.context, CameraActivity.class);

            SPValueUtil.saveStringValue(this.context,
                    Constants.CAMERA_TEMP_PATH, imagepath);
            intent.putExtra(CameraActivity.CameraIntentKey.CAMERA_PIC_WATERMASK, showWaterMask);
            intent.putExtra(CameraActivity.CameraIntentKey.CAMERA_PIC_PATH, imagepath);
            intent.putExtra(CameraActivity.CameraIntentKey.CAMERA_PIC_PROJNAME, projName);
            context.startActivityForResult(intent, PickImageUtils.PICK_CAMERA);
        } else {
            UIHelper.ToastMessage(context, "请插入SD卡");
        }

    }

    private String imagepath;
    private String projName;

    /**
     * <相机拍照> <功能详细描述>
     *
     * @param context
     * @see [类、类#方法、类#成员]
     */
    public void notifyCamera(BaseFragment context) {

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(this.context, CameraActivity.class);
            // 返回大图
            imagepath = FileUtils.createFilePng(context.getContext()).getAbsolutePath();
            SPValueUtil.saveStringValue(this.context,
                    Constants.CAMERA_TEMP_PATH, imagepath);
            intent.putExtra(CameraActivity.CameraIntentKey.CAMERA_PIC_WATERMASK, showWaterMask);
            intent.putExtra(CameraActivity.CameraIntentKey.CAMERA_PIC_PATH, imagepath);
            intent.putExtra(CameraActivity.CameraIntentKey.CAMERA_PIC_PROJNAME, projName);
            context.startActivityForResult(intent, PickImageUtils.PICK_CAMERA);
        } else {
            UIHelper.ToastMessage(context.getActivity(), "请插入SD卡");
        }

    }

    /**
     * 选择文件附件
     *
     * @param context
     */
    public void notifyFile(Activity context) {
        Intent intent = new Intent(context, LocalFileAct.class);
       // intent.putExtra("ClassName",context.get);
        context.startActivityForResult(intent, PickImageUtils.PICK_FILE);
    }

    /**
     * 选择文件附件
     *
     * @param context
     */
    public void notifyFile(BaseFragment context) {
        Intent intent = new Intent(context.getActivity(), LocalFileAct.class);

        context.startActivityForResult(intent, PickImageUtils.PICK_FILE);
    }

    /**
     * <相册选取照片> <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public void notifyAlbum(Activity context) {
        if (isSingle) {

            /***
             * 这个是调用android内置的intent，来过滤图片文件 ，同时也可以过滤其他的
             */
            try {

                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                context.startActivityForResult(intent,
                        PickImageUtils.PICK_ALBUM);
            } catch (Exception e) {
                // TODO: handle exception
            }
        } else {
            try {

                Intent intent = new Intent(context, LocalAlbum.class);
                context.startActivityForResult(intent,
                        IntentKey.REQUEST_CODE_GETIMAGE_BYCROP);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * <相册选取照片> <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public void notifyAlbum(BaseFragment context) {
        if (isSingle) {
            /***
             * 这个是调用android内置的intent，来过滤图片文件 ，同时也可以过滤其他的
             */
            try {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                context.startActivityForResult(intent,
                        PickImageUtils.PICK_ALBUM);
            } catch (Exception e) {
                // TODO: handle exception
            }
        } else {
            try {
                Intent intent = new Intent(context.getActivity(),
                        LocalAlbum.class);
                context.startActivityForResult(intent,
                        IntentKey.REQUEST_CODE_GETIMAGE_BYCROP);
            } catch (Exception e) {
            }

        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (isShowSeleceFile) {
            findViewById(R.id.btn_take_fujian).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.btn_take_fujian).setVisibility(View.GONE);
        }
    }

    // ==============
    public void selectPicView() {
        dismiss();
        // 重新初始化相册

        if (null != fragment) {

            notifyAlbum(fragment);
        } else {
            notifyAlbum((Activity) context);

        }
    }

    public void takePicView() {
        dismiss();
        if (null != fragment) {

            notifyCamera(fragment);
        } else {

            notifyCamera((Activity) context);
        }
    }
}
