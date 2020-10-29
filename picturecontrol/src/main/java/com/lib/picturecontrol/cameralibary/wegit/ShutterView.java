package com.lib.picturecontrol.cameralibary.wegit;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lib.common.baseUtils.Utils;

import java.lang.ref.SoftReference;

/**
 * 快门view
 * Created by GYH on 2017/6/20.
 */

public class ShutterView extends View {
    /** 已获取照片*/
    public static final int SHUTTER_STATUS_TAKE = 0X200;
    /** 正在获取照片*/
    public static final int SHUTTER_STATUS_DOING = 0X100;
    /** 未获取照片*/
    public static final int SHUTTER_STATUS_UNTAKE = 0x300;

    private Context mContext;
    private int width, height, outRadius, offSet;
    private Paint mPaint, intterPaint;
    private TextPaint textPaint;
    private String centerText;
    private float textX, textY;
    private int status = SHUTTER_STATUS_UNTAKE;
    private MyHandler handler;
    private boolean btnEnable = false;

    public ShutterView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public ShutterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public ShutterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(dip2px(mContext, 2));
        mPaint.setStyle(Paint.Style.STROKE);

        intterPaint = new Paint();
        intterPaint.setAntiAlias(true);
        intterPaint.setColor(Color.WHITE);
        intterPaint.setStyle(Paint.Style.FILL);

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(Utils.sp2px(mContext, 16));
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        textY = fontMetrics.descent - fontMetrics.ascent;

        offSet = dip2px(mContext, 2);
        handler = new MyHandler(new SoftReference<ShutterView>(this));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        outRadius = (int) (Math.min(width, height)/2 - mPaint.getStrokeWidth()/2);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(status != SHUTTER_STATUS_DOING && !btnEnable) {
            int x = (int) event.getRawX();
            int y = (int) event.getRawY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    if (isTouchPointInView(x, y))
                        intterPaint.setColor(Color.rgb(165, 165, 165));
                    else
                        intterPaint.setColor(Color.WHITE);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    if (isTouchPointInView(x, y)) {
                        intterPaint.setColor(Color.WHITE);
                        invalidate();
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                    intterPaint.setColor(Color.WHITE);
                    invalidate();
                    break;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private boolean isTouchPointInView(int x, int y){
         int[] location = new int[2];
        getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + getMeasuredWidth();
        int bottom = top + getMeasuredHeight();
        if (y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));

        canvas.drawColor(Color.BLACK);
        canvas.drawCircle(width/2, height/2, outRadius, mPaint);
        canvas.drawCircle(width/2, height/2, outRadius - offSet - mPaint.getStrokeWidth(), intterPaint);
        if(!TextUtils.isEmpty(centerText)){
            textX = textPaint.measureText(centerText);
            canvas.drawText(centerText, width/2 - textX/2, height/2 + textY/4, textPaint);
        }
    }

    public void setOffset(int offset){
        this.offSet = offset;
        invalidate();
    }

    public void setTextSize(int size){
        if(textPaint != null)
            textPaint.setTextSize(size);
        invalidate();
    }

    public void setStatus(int status){
        this.status = status;
    }

    public int getStatus(){
        return this.status;
    }

    public void setUnable(){
        btnEnable = true;
        setClickable(false);
        handler.sendEmptyMessageDelayed(100, 3000);
    }

    public void clickAnimation(){
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "Offset", dip2px(mContext, 2), (int)(outRadius - mPaint.getStrokeWidth())).setDuration(420);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.start();
    }

    public void startAnimation(final String text){
        setOffset((int) (outRadius - mPaint.getStrokeWidth()));
        centerText = text;
        final ObjectAnimator animatorText = ObjectAnimator.ofInt(this, "TextSize", 0, Utils.sp2px(mContext, 16)).setDuration(150);
        animatorText.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setStatus(ShutterView.SHUTTER_STATUS_TAKE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animatorText.start();
    }

    /**
     * 状态重置
     */
    public void resetStatus(){
        this.status = SHUTTER_STATUS_UNTAKE;
        this.centerText = null;
        this.offSet = dip2px(mContext, 2);
        invalidate();
    }

    /**
     * dip转为 px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public void handlerMessage(Message msg){
        switch (msg.what){
            case 100:
                btnEnable = false;
                setClickable(true);
                break;
        }
    }

    static class MyHandler extends Handler {
        SoftReference<ShutterView> view;
        public MyHandler(SoftReference<ShutterView> view){
            this.view = view;
        }

        @Override
        public void handleMessage(Message msg) {
            view.get().handlerMessage(msg);
        }
    }
}
