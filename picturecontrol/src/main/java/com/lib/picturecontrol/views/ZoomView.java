package com.lib.picturecontrol.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

public class ZoomView extends View {


    private Context mContext;
    private float mRation_WH = 0;
    private float mLastX = 0;
    private float mLastY = 0;
    private double mDoubleOne;
    private Paint mZoomPaint;
    private Drawable mZoomDrawable;
    private Rect mZoomDrawableRect = new Rect();
    private boolean isFirst = true;
    private int SINGALDOWN = 1;// 单点按下
    private int MUTILDOWM = 2;// 双点按下
    private int MUTILMOVE = 3;// 双点拖拽
    private int mCurStatus = 0;

    public ZoomView(Context context) {
        super(context);
        this.mContext = context;
        mZoomPaint = new Paint();
        mZoomPaint.setAntiAlias(true);
        mZoomPaint.setColor(Color.BLACK);
        mZoomPaint.setStyle(Paint.Style.FILL);
        mZoomPaint.setTextSize(35.0f);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        if (mZoomDrawable == null || mZoomDrawable.getIntrinsicHeight() == 0
                || mZoomDrawable.getIntrinsicWidth() == 0) {
            return;
        }
        setBackground();
        mZoomDrawable.draw(canvas);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getPointerCount()) {
            case 1:

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mCurStatus = SINGALDOWN;
                        mLastX = event.getX();
                        mLastY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        //checkBounds();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mCurStatus == SINGALDOWN) {
                            int offsetWidth = (int) (event.getX() - mLastX);
                            int offsetHeight = (int) (event.getY() - mLastY);
                            int mWidth = mZoomDrawableRect.right - mZoomDrawableRect.left;
                            int mHeight = mZoomDrawableRect.bottom - mZoomDrawableRect.top;
                            int left = mZoomDrawableRect.left;
                            int top = mZoomDrawableRect.top;
                            int l, r, t, b;
                            //当水平或者垂直滑动距离大于10,才算是拖动事件
                            if (Math.abs(offsetWidth) > 10 || Math.abs(offsetHeight) > 10) {
                                l = (int) (left + offsetWidth);
                                r = l + mWidth;
                                t = (int) (top + offsetHeight);
                                b = t + mHeight;
                                //边界判断,不让布局滑出界面
                                if (l < 0) {
                                    l = 0;
                                    r = l + mWidth;
                                } else if (r > 1920) {
                                    r = 1920;
                                    l = r - mWidth;
                                }
                                if (t < 0) {
                                    t = 0;
                                    b = t + mHeight;
                                } else if (b > 1080) {
                                    b = 1080;
                                    t = b - mHeight;
                                }
                                mLastX = event.getX();
                                mLastY = event.getY();
                                mZoomDrawableRect.set(l, t, r, b);
                                invalidate();
                            }
                        }

                        break;
                    default:
                        break;
                }
                break;
            default:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurStatus = MUTILMOVE;//双指
                        float X0 = event.getX(0);
                        float Y0 = event.getY(0);
                        float X1 = event.getX(1);
                        float Y1 = event.getY(1);
                        float k = (Y1 - Y0) / (X1 - X0);
                        float proportion = (Y0 * X1 - Y1 * X0) / (X1 - X0);
                        int RectCenterX = mZoomDrawableRect.centerX();
                        int RectCenterY = mZoomDrawableRect.centerY();
                        float mHandsX = (X0 + X1) / 2;
                        float mHandsY = mHandsX * k + proportion;
                        double mDoubleTwo = Math.sqrt(Math.pow(X0 - X1, 2)
                                + Math.pow(Y0 - Y1, 2));
                        if (mDoubleOne < mDoubleTwo) {//放大
                            if (mZoomDrawableRect.width() < mContext.getResources()
                                    .getDisplayMetrics().widthPixels * 2) {
                                int offsetwidth = 10;
                                int offsettop = (int) (offsetwidth / mRation_WH);
                                int l, r, t, b;
                                int left = mZoomDrawableRect.left;
                                int top = mZoomDrawableRect.top;
                                int right = mZoomDrawableRect.right;
                                int bottom = mZoomDrawableRect.bottom;
                                l = left - offsetwidth;
                                t = top - offsettop;
                                r = right + offsetwidth;
                                b = bottom + offsettop;
                                mZoomDrawableRect.set(l, t, r, b);
                                invalidate();
                            }
                        } else {//缩小
                            if (mZoomDrawableRect.width() > mContext.getResources()
                                    .getDisplayMetrics().widthPixels / 3) {
                                int offsetwidth = 10;
                                int offsettop = (int) (offsetwidth / mRation_WH);
                                int l, r, t, b;
                                int left = mZoomDrawableRect.left;
                                int top = mZoomDrawableRect.top;
                                int right = mZoomDrawableRect.right;
                                int bottom = mZoomDrawableRect.bottom;
                                l = left + offsetwidth;
                                t = top + offsettop;
                                r = right - offsetwidth;
                                b = bottom - offsettop;
                                mZoomDrawableRect.set(l, t, r, b);
                                invalidate();
                            }
                        }
                        mDoubleOne = mDoubleTwo;
                        if (mHandsX < RectCenterX) {
                            if (mHandsY < RectCenterY) {

                            } else {

                            }
                        } else {
                            if (mHandsY < RectCenterY) {

                            } else {

                            }
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        mCurStatus = 0;
                        break;
                    default:
                        break;
                }
                break;
        }

        return true;
    }

    public void setBackground() {
        if (isFirst) {
            mRation_WH = (float) mZoomDrawable.getIntrinsicWidth()
                    / (float) mZoomDrawable.getIntrinsicHeight();
            int px_w = Math.min(getWidth(),
                    dip2px(mContext, mZoomDrawable.getIntrinsicWidth()));
            int px_h = (int) (px_w / mRation_WH);
            int left = (getWidth() - px_w) / 2;
            int top = (getHeight() - px_h) / 2;
            int right = px_w + left;
            int bottom = px_h + top;
            mZoomDrawableRect.set(left, top, right, bottom);
            // mDrawableOffsetRect.set(mZoomDrawableRect);
            isFirst = false;
        }
        mZoomDrawable.setBounds(mZoomDrawableRect);
    }

    public Drawable getmZoomDrawable() {
        return mZoomDrawable;
    }

    public void setmZoomDrawable(Drawable mZoomDrawable) {
        this.mZoomDrawable = mZoomDrawable;
    }

    public int dip2px(Context context, int value) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (value * scale + 0.5f);
    }

}
