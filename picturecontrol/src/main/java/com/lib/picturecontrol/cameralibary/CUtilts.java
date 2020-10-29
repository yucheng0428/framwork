package com.lib.picturecontrol.cameralibary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import com.lib.common.baseUtils.Utils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Bert on 2017/5/11.
 */

public class CUtilts {

    private static CUtilts instance;
    private int maxWidth = 720;
    private int maxHeight = 1280;

    public static CUtilts getInstance() {
        if(instance == null)
            instance = new CUtilts();
        return instance;
    }

    public String getScreen(Context mContext) {
        int x, y;
        WindowManager wm = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE));
        Display display = wm.getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point screenSize = new Point();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealSize(screenSize);
                x = screenSize.x;
                y = screenSize.y;
            } else {
                display.getSize(screenSize);
                x = screenSize.x;
                y = screenSize.y;
            }
        } else {
            x = display.getWidth();
            y = display.getHeight();
        }
        return x+"---"+y;
    }


    /**
     * byte[]转换成Bitmap
     * @param b
     * @return
     */
    public Bitmap Bytes2Bitmap(byte[] b, Context context) {
        if (b.length != 0) {
            //压缩拍照获取的图片  防止oom
            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            BitmapFactory.decodeByteArray(b, 0, b.length, options);
//
//            String screen = getScreen(context);
//            String[] split = screen.split("---");
//            maxWidth = Integer.valueOf(split[0]);
//            maxHeight = Integer.valueOf(split[1]);
//
//            // Calculate inSampleSize
//            options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
//
//            // Decode bitmap with inSampleSize set
//            options.inJustDecodeBounds = false;

            Bitmap scaledBitmap = BitmapFactory.decodeByteArray(b, 0, b.length, options);
            return scaledBitmap;
        }
        return null;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    /**
     * 改变bitmap宽高
     *
     * @param bm
     * @param f
     * @return
     */
    public Bitmap zoomImg(Bitmap bm, float f) {

        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = f;
        float scaleHeight = f;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    /**
     * 保存Bitmap
     *
     * @param bm
     * @param savePath
     * @return
     */
    public String saveBitmap(Bitmap bm, String savePath) {
        File file=new File(savePath);
        try {
            FileOutputStream out = new FileOutputStream(file);
            Bitmap bitmap = zoomImg(bm, 1f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
            out.flush();
            out.close();
            return savePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 选择图片
     * @param angle
     * @param bitmap
     * @return
     */
    public Bitmap rotaingImageView(int angle , Bitmap bitmap) {

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);

        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 加水印
     * @param src
     * @param watermark
     * @param paddingLeft
     * @param paddingTop
     * @return
     */
    public Bitmap createWaterMaskBitmap(Context context, Bitmap src, Bitmap watermark, int paddingLeft, int paddingTop) {
        if (src == null) {
            return null;
        }
        int height = src.getHeight();
        int width = src.getWidth();
        String screen = getScreen(context);
        String[] split = screen.split("---");
        int widthp = Integer.parseInt(split[0]);
        int heightp = Integer.parseInt(split[1]);
        float rateW = (float)width/(float)widthp;
        float rateH = (float)height/(float)heightp;
        watermark = scale(watermark, rateW, rateH);
        Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newb);
        canvas.drawBitmap(src, 0, 0, null);
        canvas.drawBitmap(watermark, paddingLeft, height - rateH * paddingTop, null);
        canvas.save();
        canvas.restore();

        Bitmap resultBitmap = cut(newb, newb.getWidth(), (int) (newb.getHeight() - rateH * Utils.dip2px(context, 80)));
        src.recycle();
        newb.recycle();
        newb = null;
        src = null;

        return resultBitmap;
    }

    /**
     * 位图裁剪
     * @param src  裁剪对象
     * @param width  要裁剪的宽度
     * @param height  要裁剪的高度
     * @return
     */
    public Bitmap cut(Bitmap src, int width, int height){
        Bitmap resultBitmap = Bitmap.createBitmap(src, 0, 0, width, height, null, false);
        return resultBitmap;
    }

    /**
     * 位图缩放
     * @param src
     * @param sw
     * @param sh
     * @return
     */
    public Bitmap scale(Bitmap src, float sw, float sh){
        Matrix matrix = new Matrix();
        matrix.postScale(sw, sh);
        Bitmap resultBitmap = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return resultBitmap;
    }

    /**
     * dip转为 px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
