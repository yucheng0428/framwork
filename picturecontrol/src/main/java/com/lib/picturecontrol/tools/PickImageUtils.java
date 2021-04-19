package com.lib.picturecontrol.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.LinearLayout;


import com.alibaba.fastjson.JSON;
import com.lib.common.base.BaseFragment;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.FileUtils;
import com.lib.common.baseUtils.IntentKey;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.Utils;
import com.lib.picturecontrol.album.LocalImageHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * <照相选照片工具类> <功能详细描述>
 *
 * @author 姓名
 * @version [版本号, 2015年10月23日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PickImageUtils {
    /**
     * 相机
     */
    public static final int PICK_CAMERA = 1000;

    /**
     * 相册
     */
    public static final int PICK_ALBUM = 2000;

    /**
     * 附件
     */
    public static final int PICK_FILE = 3000;

    Context mContext;
    int id;

    InotifyBitmap listener;

    private boolean isSingle;

    private boolean isCanChoseFile = false;
    private boolean showWaterMask = true;

    private String projName;

    private boolean isOriginal = false;//是否上传原图

    private int picnums = 0;

    public void setMaxPics(int picnums) {
        this.picnums = picnums;
    }

    public int getMaxPics() {
        if (0 == picnums) {
            return 2;
        }
        return picnums - 1;
    }

    /**
     * <是否单选> <功能详细描述>
     *
     * @param isSingle
     * @see [类、类#方法、类#成员]
     */
    public void setIsSingle(boolean isSingle) {
        this.isSingle = isSingle;

    }

    public void isShowWaterMask(boolean showWaterMask){
        this.showWaterMask = showWaterMask;
    }

    public void setProjName(String projName){
        this.projName = projName;
    }


    PicpickDlg picDlg;

    public PickImageUtils(int id, Context context, InotifyBitmap listener) {
        this.mContext = context;
        this.id = id;
        this.listener = listener;
    }

    /**
     * <统一样式的选择图片框> <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public void showPickDialog(BaseFragment fragment) {
        if (null == picDlg && null != mContext) {
            picDlg = new PicpickDlg(mContext, picnums);
            picDlg.setFragment(fragment);
            picDlg.setShowSeleceFile(isCanChoseFile);
            picDlg.isShowWaterMask(showWaterMask);
            picDlg.setProjName(projName);
        }
        if (null != picDlg && null != mContext) {
            picDlg.setSingle(isSingle);
            picDlg.setShowSeleceFile(isCanChoseFile);
            picDlg.setPicNums(picnums);
            picDlg.isShowWaterMask(showWaterMask);
            picDlg.setProjName(projName);
        }

        if (!picDlg.isShowing() && null != mContext) {
            picDlg.setShowSeleceFile(isCanChoseFile);
            picDlg.isShowWaterMask(showWaterMask);
            picDlg.setProjName(projName);
            picDlg.show();
        }
    }

    public PickImageUtils(Context context, InotifyBitmap listener) {
        this.mContext = context;
        this.id = 0;
        this.listener = listener;
    }

    /**
     * <统一样式的选择图片框> <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public void showPickDialog() {
        if (null == picDlg && null != mContext) {
            picDlg = new PicpickDlg(mContext, picnums);
            picDlg.setShowSeleceFile(isCanChoseFile);
            picDlg.isShowWaterMask(showWaterMask);
            picDlg.setProjName(projName);
        }

        if (null != picDlg && null != mContext) {
            picDlg.setSingle(isSingle);
            picDlg.setShowSeleceFile(isCanChoseFile);
            picDlg.setPicNums(picnums);
            picDlg.isShowWaterMask(showWaterMask);
            picDlg.setProjName(projName);
        }

        if (null != picDlg&&!picDlg.isShowing() && null != mContext) {
            picDlg.setShowSeleceFile(isCanChoseFile);
            picDlg.isShowWaterMask(showWaterMask);
            picDlg.setProjName(projName);
            picDlg.show();
        }
    }


    /**
     * <一句话功能简述> <功能详细描述>
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @see [类、类#方法、类#成员]
     */
    public void notifyActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {// 相册选取图片

            switch (requestCode) {

                case PICK_ALBUM:
                    /**
                     * 当选择的图片不为空的话，在获取到图片的途径
                     */
                    if (null != data) {
                        try {
                            Uri selectedImage = data.getData();
                            String path = getPickPhotoPath((Activity) mContext, data);

                            /***
                             * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话， 你选择的文件就不一定是图片了， 这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
                             */
                            String end = path.toLowerCase();
                            if (end.endsWith("jpg") || end.endsWith("png") || end.endsWith("jpeg")) {
                                // 优化图片大小
                                // picPath = path;
                                Bitmap bitmap = BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(selectedImage));
                                File file = new File(path);
                                bitmap = compressBitmap(file, 60,mContext);
                                // Bitmap bitmap = safeDecodeStream(selectedImage, 800, 600)
                                Log.e("PICKIMAGEUTIL", "path:" + path);

                                listener.notifyBitmap(id, bitmap, path);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                case IntentKey.REQUEST_CODE_GETIMAGE_BYCROP:

                    if (LocalImageHelper.getInstance().isResultOk()) {
                        LocalImageHelper.getInstance().setResultOk(false);

                        // 获取选中的图片
                        List<LocalImageHelper.LocalFile> files = LocalImageHelper.getInstance().getCheckedItems();
                        List<String> paths = new ArrayList<String>();
                        for (LocalImageHelper.LocalFile f : files) {
                            //大于150kb传缩略图
                            if (f.getFileSize() > 150) {
                                File sourceFile = null;
                                //获取图片地址
                                String bitmapPath = getPath(Uri.parse(f.getOriginalUri()));
                                try {
                                    sourceFile = new File(bitmapPath);
                                    File file = compressFile(sourceFile,mContext);
                                    if(file != null && file.exists())
                                        paths.add(file.getAbsolutePath());
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            } else {
                                paths.add(getPath(Uri.parse(f.getOriginalUri())));
                            }
                        }
                        if (paths.size() > 0) {
                            for (int i = 0; i < paths.size(); i++) {
                                for (int j = paths.size() - 1; j > i; j--) {
                                    if (paths.get(i) == paths.get(j)) {
                                        paths.remove(j);
                                    }
                                }
                            }
                            try {
                                listener.notifyBitmap(id, paths);
                            } catch (Exception e) {
                                // TODO: handle exception
                                e.printStackTrace();
                            }
                            Log.e("PICKIMAGEUTIL", "path:" + JSON.toJSONString(paths));

                        }
                        LocalImageHelper.getInstance().clearList();
                    }

                    break;
                case PICK_CAMERA:
                    String imagePath = SPValueUtil.getStringValue(mContext, Common.CAMERA_TEMP_PATH);
                    File file = compressFile(new File(imagePath),mContext);
                    Bitmap bitmap1 = BitmapFactory.decodeFile(file.getAbsolutePath());
                    Log.i("TAG", "picDlg.getImagePath():");
                    LocalImageHelper.getInstance().refreshData();
                    try {
                        MediaStore.Images.Media.insertImage(mContext.getContentResolver(), file.getAbsolutePath(), file.getName(), null);//图片插入到系统图库
                    }catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));//通知图库刷新
                    listener.notifyBitmap(id, bitmap1, file.getAbsolutePath());
                    break;

                case PICK_FILE:
                    try {
                       /* String filePath = data.getStringExtra("Uri");
                        listener.notifyFilePath(id, filePath);*/
//                        List<FileInfo> fileInfos = FileDao.queryAll();
//                        List<String> paths=new ArrayList<>();
//                        for (FileInfo i:fileInfos) {
//                            paths.add(i.getFilePath());
//                        }
//                        listener.notifyFilePath(id, paths);
                    } catch (Exception e) {
                    }
                    break;
                default:
                    break;
            }
        }
    }


    private String getPath(Uri uri) {
        String[] data = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(mContext, uri, data, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String url = cursor.getString(column_index);
        if (Integer.parseInt(Build.VERSION.SDK) < 14) {
            cursor.close();
        }

        return url;
    }

    /**
     * <清除选中的图片> <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public void clearCheckedItems() {
        // 清空选中的图片
        LocalImageHelper.getInstance().getCheckedItems().clear();
    }

    /**
     * 获取选择图片路径
     *
     * @param activity
     * @param data
     * @return path
     */
    private String getPickPhotoPath(Activity activity, Intent data) {
        String path = "";
        Uri imageuri = data.getData();
        if (null != imageuri && imageuri.getScheme().compareTo("file") == 0) {
            path = imageuri.toString().replace("file://", "");
        } else {
            if (imageuri != null) {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = activity.getContentResolver().query(imageuri, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    path = cursor.getString(column_index);
                }
                if (Integer.parseInt(Build.VERSION.SDK) < 14) {
                    cursor.close();
                }
            }
        }
        return path;
    }

    /**
     * 使用Compress开源项目  高保真压缩图片
     * @return
     */
    public static Bitmap compressBitmap(File file, int quality, Context context){
        try {
            if(file != null && file.exists() && file.isFile()){
                Bitmap compressedImageBitmap = new Compressor(context).setQuality(quality).compressToBitmap(file);
                Log.d("compress", "sourceFileSize: " + FileUtils.FormetFileSize(file.length()) + " compressFileSize:" + FileUtils.FormetFileSize(compressedImageBitmap.getByteCount()));
                return compressedImageBitmap;
            }else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用Compress开源项目  高保真压缩图片
     * @return
     */
    public static File compressFile(File file, Context context){
        try {
            if(file != null && file.exists() && file.isFile()){
                File compressedImageFile = new Compressor(context).setQuality(60).compressToFile(file);
                Log.d("compress", "sourceFileSize: " + FileUtils.FormetFileSize(file.length()) + " compressFileSize:" + FileUtils.FormetFileSize(compressedImageFile.length()));
                return compressedImageFile;
            }else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }






    public interface InotifyBitmap {
        public void notifyBitmap(int id, Bitmap bitmap, String path);

        public void notifyBitmap(int id, List<String> paths);

    }


    /**
     * 根据手机厂商设置压缩比例
     *
     * @return
     */
    @SuppressLint("DefaultLocale")
    private float getCompressRatio() {
        float ratio = 2.0f;
        String manufacturer = "";
        manufacturer = PickImageUtils.getManufacturer();
        if (null != manufacturer && manufacturer.toLowerCase().contains("samsung")) {
            //三星机器对内存使用限制严格，故提高压缩比，以避免oom
            ratio = 1.6f;
        } else if (null != manufacturer && manufacturer.toLowerCase().contains("mi")) {
            //小米机器对内存使用限制严格，故提高压缩比，以避免oom
            ratio = 1.0f;
        }

        return ratio;
    }

    /**
     * 获取手机厂商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }
}
