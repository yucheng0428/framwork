package com.lib.picturecontrol.views;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 图片阴影遮罩层
 * Created by GYH on 2017/6/9.
 */

public class MaskView extends View {
    private static final int DEFAULT_RADIUS = 40;

    private int width, height;//控件宽高
    private Paint mPaint;// 画笔
    private Context mContext;
    private int leftLength, rightLength;
    private int radius;//半径
    private ObjectAnimator openAnimator;

    public MaskView(Context context) {
        super(context);
        this.mContext = context;
        initliaze();
    }

    public MaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initliaze();
    }

    public MaskView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initliaze();
    }

    private void initliaze(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setColor(Color.argb(99, 34, 34, 34));
        mPaint.setStyle(Paint.Style.FILL);//空心

        radius = dip2px(mContext, DEFAULT_RADIUS)/2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);

        leftLength = rightLength = width/2;

//        openAnimator = ObjectAnimator.ofInt(this, "maskLength", width/2, 0).setDuration(500);
        openAnimator = ObjectAnimator.ofInt(this, "centerRadius", dip2px(mContext, DEFAULT_RADIUS)/2, (int) Math.sqrt(Math.pow(height/2, 2) + Math.pow(width/2, 2))).setDuration(300);
        openAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        Path lPath = new Path();
        lPath.moveTo(0, 0);
        lPath.lineTo(leftLength, 0);
        lPath.lineTo(leftLength, height/2 - radius);
        lPath.arcTo(new RectF(leftLength - radius, height/2 - radius, leftLength + radius, height/2 + radius), 270, -180);
        lPath.lineTo(leftLength, height);
        lPath.lineTo(0, height);
        lPath.lineTo(0, 0);
        Path rPath = new Path();
        rPath.moveTo(width, 0);
        rPath.lineTo(width - rightLength, 0);
        rPath.lineTo(width - rightLength, height/2 - radius);
        rPath.arcTo(new RectF(width - rightLength - radius, height/2 - radius, width - rightLength + radius, height/2 + radius), 270, 180);
        rPath.lineTo(width - rightLength, height);
        rPath.lineTo(width, height);
        rPath.lineTo(width, 0);
        canvas.drawPath(lPath, mPaint);
        canvas.drawPath(rPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    /**
     * dip转为 px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public void setMaskLength(int length){
        this.leftLength = Math.min(Math.max(0, length), width/2);
        this.rightLength = Math.min(Math.max(0, length), width/2);
        invalidate();
    }

    public void setCenterRadius(int radius){
        this.radius = radius;
        invalidate();
    }

    public void startAnimation(Animator.AnimatorListener listener){
        if(listener != null)
            openAnimator.addListener(listener);
        openAnimator.start();
    }
}
