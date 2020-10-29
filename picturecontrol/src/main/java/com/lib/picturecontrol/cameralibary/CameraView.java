package com.lib.picturecontrol.cameralibary;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lib.common.baseUtils.Utils;
import com.lib.picturecontrol.R;
import com.lib.picturecontrol.cameralibary.InterFace.CameraOpenListener;
import com.lib.picturecontrol.cameralibary.InterFace.TakePhotoSuccListener;
import com.lib.picturecontrol.cameralibary.InterFace.WaterImageListener;
import com.lib.picturecontrol.cameralibary.wegit.FoucsView;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Bert on 2017/5/5.
 */

public class CameraView extends FrameLayout {

    private View rootView, maskView;
    private TextureView sfv_camera_view;
    private ImageView waterMask;
    private Activity mActivity;
    public static int CAMERA_BACK = 0;
    public static int CAMERA_FRONT = 1;
    private int current_camrea = CAMERA_BACK;
    private Bitmap waterMaskBitmap = null;
    private boolean camera_is_open = false;
    private Context mContext;
    private Bitmap bgMap = null;
    private Bitmap waterBit = null;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String locaString;
    private String projName;
    private int layout_width;
    private int fouce_size;
    private int locaReTryCount;
    private int totalLine;

    private int currentType = 0x100;//水印拍照还是普通拍照


    private enum Position {
        BACK,
        FRONT,
    }

    private Handler timeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
                locaString = "";
            buildWaterMask(locaString, projName);
            if(waterMask.getVisibility() == View.VISIBLE)
                timeHandler.sendEmptyMessageDelayed(1, 1000);
        }
    };


    public CameraView( Context context) {
        super(context,null);
        mContext = context;
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CameraView);
        current_camrea = a.getInt(R.styleable.CameraView_direction, Position.BACK.ordinal());
        a.recycle();
        initData();
        initView(context);
    }

    private void initData() {
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        layout_width = outMetrics.widthPixels;
        fouce_size = layout_width / 4;
    }

    private void initView(Context context){
        rootView = View.inflate(context, R.layout.layout_camera_view, this);
        sfv_camera_view = (TextureView) rootView.findViewById(R.id.sfv_camera_view);
        maskView = rootView.findViewById(R.id.maskView);
        waterMask = (ImageView) rootView.findViewById(R.id.waterMask);
        //mFoucsView
        mFoucsView = new FoucsView(mContext, fouce_size);
        RelativeLayout.LayoutParams foucs_param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mFoucsView.setLayoutParams(foucs_param);
        mFoucsView.setVisibility(INVISIBLE);
        this.addView(mFoucsView);
    }

    /**
     * 打开相机
     * @param mActivity
     */
    public void open(Activity mActivity, CameraOpenListener openListener){
        this.mActivity = mActivity;
      CameraUtils.getInstance().openCamera(sfv_camera_view,mActivity,current_camrea, maskView);
      CameraHelper.getInstance().setOpenListener(openListener);
        camera_is_open = true;
    }

    /**
     * 关闭相机
     * @param mActivity
     */
    public void close(Activity mActivity){
        if(cameraIsOpen()){
          CameraUtils.getInstance().closeCamera(sfv_camera_view,mActivity);
        }

    }

    /**
     * 切换摄像头（相反切换）
     */
    public void ChangeCamera(){
        if(cameraIsOpen())
      CameraUtils.getInstance().changeCamera(sfv_camera_view,mActivity);
    }

    /**
     * 切换摄像头（指定切换）
     */
    public void ChangeCamera(int direction){
        if(cameraIsOpen())
      CameraUtils.getInstance().changeCamera(sfv_camera_view,mActivity,direction);
        current_camrea = direction;
    }

    /**
     * 拍照
     * @param mTakeSuccess
     * @return
     */
    public Bitmap takePhoto(Camera.ShutterCallback shutterCallback, CameraHelper.takeSuccess mTakeSuccess){
        return CameraHelper.getInstance().takePhoto(mContext, shutterCallback, mTakeSuccess);
    }

    /**
     * 拍照
     * @param ImgPath
     * @return
     */
    public void takePhoto(Camera.ShutterCallback shutterCallback, final String ImgPath, final TakePhotoSuccListener listener){
        if(currentType == 0x100){
          CameraHelper.getInstance().takePhoto(mContext, shutterCallback, new CameraHelper.takeSuccess() {
                @Override
                public void success(final Bitmap mBitmap) {
//                    CUtilts.getInstance().saveBitmap(mBitmap, ImgPath);
                    if(listener != null)
                        listener.takePhotoComplete(mBitmap);
                }
            });
        }else if(currentType == 0x200){
            takePhotoAddWaterMask(shutterCallback, bgMap, CUtilts.dip2px(mContext, 2), CUtilts.dip2px(mContext, 80) + bgMap.getHeight(),ImgPath, listener);
        }
    }

    /**
     * 拍照并添加水印
     * @param waterMask
     * @param paddingLeft
     * @param paddingTop
     * @return
     */
    public void takePhotoAddWaterMask(Camera.ShutterCallback shutterCallback, final Bitmap waterMask, final int paddingLeft, final int paddingTop,
                                      final WaterImageListener mWaterImageListener, final TakePhotoSuccListener listener){
      CameraHelper.getInstance().takePhoto(mContext, shutterCallback, new CameraHelper.takeSuccess() {
            @Override
            public void success(Bitmap mBitmap) {
                waterMaskBitmap = CUtilts.getInstance().createWaterMaskBitmap(mContext, mBitmap, waterMask, paddingLeft, paddingTop);
                Log.d("cameraAction", waterMaskBitmap.getRowBytes() * waterMaskBitmap.getHeight() + "---" + System.currentTimeMillis());

                mWaterImageListener.Success(waterMaskBitmap);
                if(listener != null)
                    listener.takePhotoComplete(waterMaskBitmap);
            }
        });

    }

    /**
     * 拍照并添加水印
     * @param waterMask
     * @param paddingLeft
     * @param paddingTop
     * @return
     */
    public void takePhotoAddWaterMask(Camera.ShutterCallback shutterCallback, final Bitmap waterMask, final int paddingLeft, final int paddingTop, final String
            savePath, final TakePhotoSuccListener listener){
        takePhotoAddWaterMask(shutterCallback, waterMask, paddingLeft, paddingTop, new WaterImageListener() {
            @Override
            public void Success(Bitmap mBitmap) {
//                CUtilts.getInstance().saveBitmap(mBitmap,savePath);
            }
        }, listener);
    }

    /**
     * 显示隐藏水印
     * @param show
     */
    public void showWaterMask(boolean show, String projName){
        waterMask.setVisibility(show ? View.VISIBLE : View.GONE);
        currentType = show ? 0x200 : 0x100;
        this.projName = projName;
        if(show)
            timeHandler.sendEmptyMessageDelayed(1, 1000);
    }

    /**
     * 显示隐藏水印
     * @param show
     */
    public void showWaterMask(boolean show){
       showWaterMask(show, null);
    }

    /**
     * 构建水印
     * @return
     */
    private void buildWaterMask(String locaStr, String projName){
        String userName = "照片水印";
        locaStr = (TextUtils.isEmpty(locaStr) ? "" : locaStr) + userName;
        Date date = new Date();
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(Utils.sp2px(mContext, 13));
        textPaint.setShadowLayer(3, 5, 5, Color.GRAY);
        StaticLayout staticLayout = new StaticLayout(locaStr, textPaint, getMeasuredWidth() - CUtilts.dip2px(mContext, 18), Layout.Alignment.ALIGN_OPPOSITE, 1.0f, 0.0f, true);
        StaticLayout projNameStatic = null;
        if(!TextUtils.isEmpty(projName)){
            projNameStatic = new StaticLayout(projName, textPaint, getMeasuredWidth() - CUtilts.dip2px(mContext, 18), Layout.Alignment.ALIGN_OPPOSITE, 1.0f, 0.0f, true);
        }
        float oneLineHeight = (float)staticLayout.getHeight()/(float) staticLayout.getLineCount();//单行高度
        int locStrHeight = (int) (staticLayout.getHeight() > oneLineHeight ? staticLayout.getHeight() : oneLineHeight);//位置信息文字的高度，涉及到文字换行 高度需要动态改变
        float projNameOneLineHeight = 0f;
        int projNameHeight = 0;
        if(projNameStatic != null){
            projNameOneLineHeight = (float)projNameStatic.getHeight()/(float) projNameStatic.getLineCount();//单行高度
            projNameHeight = (int) (projNameStatic.getHeight() > projNameOneLineHeight ? projNameStatic.getHeight() : projNameOneLineHeight);//位置信息文字的高度，涉及到文字换行 高度需要动态改变
        }
        final int currentLines = staticLayout.getLineCount() + (projNameStatic == null ? 0 : projNameStatic.getLineCount()) + 1;//当前行数， 这里默认logo加时间为一行
        if(waterBit == null)
            waterBit = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.save_down);
        if(bgMap == null || totalLine != currentLines){//水印位图未创建或水印文字高度发生改变，那么构建水印位图
            bgMap = Bitmap.createBitmap(getMeasuredWidth(), waterBit.getHeight() + locStrHeight + projNameHeight, Bitmap.Config.ARGB_8888);
            totalLine = currentLines;
        }
        int width = waterBit.getWidth();
        int height = waterBit.getHeight();
        int dateStrWidth = (int) (textPaint.measureText(dateFormat.format(date)) + width);//时间加水印图标文字的宽度
        int locStrWidth = Math.min((int) textPaint.measureText(locaStr), bgMap.getWidth() - CUtilts.dip2px(mContext, 18));//地址加用户名文字的宽度
        int canvasTranslateNum = Math.max(dateStrWidth, locStrWidth);//画布移动距离
        Canvas canvas = new Canvas(bgMap);
        /**清空画布**/
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));

        //绘制第一行水印：logo及时间
        canvas.save();
        canvas.translate(bgMap.getWidth() - dateStrWidth - CUtilts.dip2px(mContext, 22), 0);//CUtilts.dip2px(mContext, 22),这边的22是文字右边距18加上文字和图片间距4
//        canvas.drawBitmap(waterBit, 0, 0, null);
        canvas.drawText(dateFormat.format(date), width + CUtilts.dip2px(mContext, 4), (height)*6/7, textPaint);//图片和文字间距4
        canvas.restore();
        // 绘制第二行水印：项目名称
        if(projNameStatic != null){
            canvas.save();
            canvas.translate(0, height);//bgMap.getWidth() - locStrWidth - CUtilts.dip2px(mContext, 18)  因为设置了Layout.Alignment.ALIGN_OPPOSITE属性  所以取消了X坐标的移动，位置信息文字右对齐
            projNameStatic.draw(canvas);
            canvas.restore();
        }

        //绘制第三行水印：当前地址
        canvas.save();
        canvas.translate(0, height + projNameHeight);//bgMap.getWidth() - locStrWidth - CUtilts.dip2px(mContext, 18)  因为设置了Layout.Alignment.ALIGN_OPPOSITE属性  所以取消了X坐标的移动，位置信息文字右对齐
        staticLayout.draw(canvas);
        canvas.restore();

        canvas.save();
        canvas.restore();
        waterMask.setImageBitmap(bgMap);
    }

    /**
     * 重设水印位置信息
     */
    public void resetLocaInfo(){
        locaString ="水印";
        buildWaterMask(locaString, projName);
    }

    /**
     * 判断当前相机是否是前置摄像头
     * @return
     */
    public Boolean currentCameraIsFront(){
       return CameraHelper.getInstance().checkCameraIsFront();
    }

    private boolean cameraIsOpen(){
        return camera_is_open;
    }

    private boolean firstTouch = true;
    private float firstTouchLength = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getPointerCount() == 1) {
                    WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
                    int height = windowManager.getDefaultDisplay().getHeight();
                    if(event.getY() < height - Utils.dip2px(mContext, 80)){
                        //显示对焦指示器
                        setFocusViewWidthAnimation(event.getX(), event.getY());
                    }
                }
                if (event.getPointerCount() == 2) {
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 1) {
                    firstTouch = true;
                }
                if (event.getPointerCount() == 2) {
                    //第一个点
                    float point_1_X = event.getX(0);
                    float point_1_Y = event.getY(0);
                    //第二个点
                    float point_2_X = event.getX(1);
                    float point_2_Y = event.getY(1);

                    float result = (float) Math.sqrt(Math.pow(point_1_X - point_2_X, 2) + Math.pow(point_1_Y -
                            point_2_Y, 2));

                    if (firstTouch) {
                        firstTouchLength = result;
                        firstTouch = false;
                    }
                    if (Math.abs(result - firstTouchLength) / CameraHelper.CAMERA_SENSITIVITY != 0) {
                        firstTouch = true;
                      CameraHelper.getInstance().setZoom(result - firstTouchLength);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                firstTouch = true;
                break;
        }
        return true;
    }

    public void destory(){
        locaReTryCount = 0;
        timeHandler.removeMessages(1);
      CameraHelper.getInstance().release();
    }

    private FoucsView mFoucsView;
    /**
     * 显示对焦指示器
     * focusview animation
     */
    private void setFocusViewWidthAnimation(float x, float y) {
        if (currentCameraIsFront()) {
            return;
        }
        mFoucsView.setVisibility(VISIBLE);
        if (x < mFoucsView.getWidth() / 2) {
            x = mFoucsView.getWidth() / 2;
        }
        if (x > layout_width - mFoucsView.getWidth() / 2) {
            x = layout_width - mFoucsView.getWidth() / 2;
        }
        if (y < mFoucsView.getWidth() / 2) {
            y = mFoucsView.getWidth() / 2;
        }
      CameraHelper.getInstance().handleFocus(mContext, x, y, new CameraHelper.FocusListener() {
            @Override
            public void focusSuccess() {
                mFoucsView.setVisibility(INVISIBLE);
            }
        });

        mFoucsView.setX(x - mFoucsView.getWidth() / 2);
        mFoucsView.setY(y - mFoucsView.getHeight() / 2);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mFoucsView, "scaleX", 1, 0.6f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mFoucsView, "scaleY", 1, 0.6f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mFoucsView, "alpha", 1f, 0.3f, 1f, 0.3f, 1f, 0.3f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(scaleX).with(scaleY).before(alpha);
        animSet.setDuration(400);
        animSet.start();
    }

}
