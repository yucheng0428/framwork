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
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.lib.common.baseUtils.Utils;


/**
 * 附件删除按钮
 * Created by GYH on 2017/6/16.
 */

public class ImageDeleteView extends View {

    private int width, height;//控件宽高
    private Context mContext;
    private int xOffSet, yOffSet;
    private int raduis;//半径
    private Paint xPaint, yPaint, bgPaint;
    private ObjectAnimator xAnimator, yAnimator;
    private boolean animatiored = false;

    public ImageDeleteView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public ImageDeleteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public ImageDeleteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init(){
        raduis = Utils.dip2px(mContext, 6);

        xPaint =  new Paint();
        xPaint.setAntiAlias(true);
        xPaint.setStyle(Paint.Style.FILL);
        xPaint.setStrokeWidth(4);
        xPaint.setColor(Color.WHITE);
        yPaint = new Paint();
        yPaint.setAntiAlias(true);
        yPaint.setStyle(Paint.Style.FILL);
        yPaint.setStrokeWidth(4);
        yPaint.setColor(Color.WHITE);

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.argb(99, 163,163, 163));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(), widthMeasureSpec);
        xOffSet = width/5;
        yOffSet = height/5;

        xAnimator = ObjectAnimator.ofInt(this, "xoffSet", width*4/5, width/5).setDuration(300);
        yAnimator = ObjectAnimator.ofInt(this, "yoffSet", height*4/5, height/5).setDuration(200);
        yAnimator.setInterpolator(new AccelerateInterpolator());
        xAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                yAnimator.start();
                animatiored = true;
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

        Path bgPath = new Path();
        bgPath.moveTo(0, 0);
        bgPath.lineTo(width, 0);
        bgPath.lineTo(width, height);
        bgPath.lineTo(raduis, height);
        bgPath.arcTo(new RectF(0, height - 2*raduis, 2*raduis, height), 90, 90);
        bgPath.lineTo(0, height - raduis);
        canvas.drawPath(bgPath, bgPaint);

        if(yOffSet == height*4/5)
            yPaint.setColor(Color.TRANSPARENT);
        else
            yPaint.setColor(Color.WHITE);

        if(xOffSet == width*4/5)
            xPaint.setColor(Color.TRANSPARENT);
        else
            xPaint.setColor(Color.WHITE);
        canvas.drawLine(width/5, width/5, width - xOffSet, height - xOffSet, xPaint);
        canvas.drawLine(height*4/5, height/5, yOffSet, height - yOffSet, yPaint);
    }

    public void setXoffSet(int xOffSet){
        if(xOffSet < Math.min(width, height)/5 || xOffSet > Math.min(width, height)*4/5)
            return;
        this.xOffSet = xOffSet;
        invalidate();
    }

    public void setYoffSet(int yOffSet){
        if(yOffSet < Math.min(width, height)/5 || yOffSet > Math.min(width, height)*4/5)
            return;
        this.yOffSet = yOffSet;
        invalidate();
    }

    public void setVisibility(int visibility, boolean withAnimation){
        super.setVisibility(visibility);
        if(withAnimation){
            if(visibility == View.VISIBLE){
                xOffSet = width*4/5;
                yOffSet = height*4/5;
                invalidate();
                showViewWithAnimator();
            }
        }
    }

    public void showViewWithAnimator(){
        if(xAnimator != null)
            xAnimator.start();
    }
}
