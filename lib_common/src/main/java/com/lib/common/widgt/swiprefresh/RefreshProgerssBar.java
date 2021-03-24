package com.lib.common.widgt.swiprefresh;

import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;

import com.lib.common.R;
import com.lib.common.baseUtils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class RefreshProgerssBar extends BaseSwipProgressBar {
    private int lineColor;
    private int LOGO_MARGINTOP=60;//
    private final static float LOGO_LINEWIDTH = 1.0f;//logo 绘制线的宽度
    private final static float LOGO_LINEWIDTH_SMALL = .5f;

    private int lineWidth;
    private int logoWidth;
    private int logoHeight;

    private static Point[] shape;
    private static Point[] progressShape;
    private static Point[] newShape;
    public RefreshProgerssBar(View mParent,int logoHeight, int lineWidth) {
        super(mParent);
        this.logoHeight = logoHeight;
        this.lineWidth = (int) (lineWidth * LOGO_LINEWIDTH);
        this.logoWidth = (int) (Math.sin(Math.toRadians(60)) * logoHeight);
        mPaint.setStyle(Paint.Style.STROKE);//只绘制图形的边
        mPaint.setStrokeCap(Paint.Cap.ROUND);//半圆形
        mPaint.setAntiAlias(true);
    }


    /**
     * 执行顺序  1
     * 这里是做初始化操作
     * 绘制范围,绘制描点的坐标
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        final int width = mBounds.width();
        final int height = mBounds.height();
        final int edge = (logoHeight/2);//边缘
        final int bevel = (edge/2);//斜面
        final int subEdge = ((edge + bevel)/2);
        final int subBevel = (subEdge/2);
        shape = new Point[]{
                new Point(width/2 + logoWidth/2, LOGO_MARGINTOP + bevel),
                new Point(width/2, LOGO_MARGINTOP),
                new Point(width/2 - logoWidth/2, LOGO_MARGINTOP + bevel),
                new Point(width/2 - logoWidth/2, LOGO_MARGINTOP + logoHeight - bevel),
                new Point(width/2, LOGO_MARGINTOP + logoHeight),
                new Point(width/2 + logoWidth/2, LOGO_MARGINTOP + logoHeight - bevel),
                new Point(width/2 + logoWidth/2, LOGO_MARGINTOP + bevel),
                new Point(width/2 + logoWidth/2 - 5, LOGO_MARGINTOP + bevel + 5),
                new Point(width/2, LOGO_MARGINTOP + logoHeight/2),
                new Point(width/2, LOGO_MARGINTOP + logoHeight/6 + lineWidth/2 + 2),
                new Point(width/2 - logoWidth/4, LOGO_MARGINTOP + bevel/2 + subBevel + lineWidth/2),
                new Point(width/2 - logoWidth/4, LOGO_MARGINTOP + logoHeight - bevel/2 - subBevel - lineWidth/2),
                new Point(width/2, LOGO_MARGINTOP + logoHeight - logoHeight/6 - lineWidth/2 - 2),
                new Point(width/2 + logoWidth/4, LOGO_MARGINTOP + logoHeight - bevel/2 - subBevel - lineWidth/2)};

        progressShape = Arrays.copyOfRange(shape, 0, 6);
        newShape= Arrays.copyOfRange(shape,7,shape.length-1);
    }

    /**
     * 执行 绘制操作
     * LOGO_MARGINTOP  这里修改后误差大小会影响图形所在位置
     * @param canvas  画布
     */
    @Override
    public void draw(Canvas canvas) {
        final int width = mBounds.width();
        final int height = mBounds.height();
        LOGO_MARGINTOP = Math.max((height - logoHeight), 0)/2;//logo顶部间距
        int restoreCount = canvas.save();//画布将当前的状态保存
        canvas.clipRect(mBounds);//设置画布的显示区域
        if(mRunning || mFinishTime > 0){
            long now = AnimationUtils.currentAnimationTimeMillis();//开始时间
            long elapsed = (now - mStartTime) % ANIMATION_DURATION_MS_SHORT;//经过的时间
            int floor = (int) Math.floor((now - mStartTime) / (float)ANIMATION_DURATION_MS_SHORT);//最终时间
            float loadProgress = elapsed / (float)ANIMATION_DURATION_MS_SHORT;
            int progressLum = floor%2 == 0 ? (int) (60 - loadProgress * 120) : (int) (60 - (1-loadProgress) * 120);
            drawLines(canvas, R.color.darkgrey);
//            setLum(progressLum);
            drawOutProgress(canvas, R.color.color_248bfe, floor, loadProgress);
            newProgress(canvas, R.color.color_248bfe, floor, loadProgress);
        } else if(mTriggerPercentage > 0 && mTriggerPercentage <= 1.0){
            drawLogoProgress(canvas, R.color.darkgrey);
        }
        canvas.restoreToCount(restoreCount);
    }

    //画直线
    private void drawLines(Canvas canvas, int color){
        mPaint.setColor(mParent.getResources().getColor(color));
        mPaint.setStrokeWidth(lineWidth);
        Path path = new Path();
        if(shape != null){
            for(int i=0;i<shape.length;i++){
                Point p = shape[i];
                if(i == 0 || i == 7 || i == 9)
                    path.moveTo(p.x, p.y);
                else{
                    //先移动后连线而不是直接lineTo，这样线条转折处会带有弧度
                    Point leastP = shape[i - 1];
                    path.moveTo(leastP.x, leastP.y);
                    path.lineTo(p.x, p.y);
                }
            }
        }
        canvas.drawPath(path, mPaint);
    }
    /**
     * 绘制加载过程
     * @param canvas
     * @param color
     * @param outProgress
     */
    private void drawOutProgress(Canvas canvas, int color, int floor, float outProgress){
        if(progressShape != null){
            Paint progressPaint = new Paint(mPaint);
            int progressLum = floor%2 == 0 ? (int) (60 - outProgress * 120) : (int) (60 - (1-outProgress) * 120);
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.set(new float[] {
                    1,0,0,0,progressLum,
                    0,1,0,0,progressLum,
                    0,0,1,0,progressLum,
                    0,0,0,1,0,
            });
            progressPaint.setColor(mParent.getResources().getColor(color));
//            progressPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            progressPaint.setStrokeWidth(lineWidth);
            progressPaint.setStyle(Paint.Style.STROKE);
            Path path = new Path();
            float pre_max = 1f/(float) (progressShape.length);
            float progress = outProgress/pre_max;
            float lineProgress = progress%1f;
            int linePart = (int) Math.ceil(progress);
            for(int i = 0; i< Math.min(progressShape.length, linePart); i++){
                Point p = progressShape[i];
                if(i == 0)
                    path.moveTo(p.x, p.y);
                else{
                    //先移动后连线而不是直接lineTo，这样线条转折处会带有弧度
                    Point leastP = progressShape[i - 1];
                    path.moveTo(leastP.x, leastP.y);
                    path.lineTo(p.x, p.y);
                }
            }
            if(linePart < progressShape.length + 1 && linePart > 0){
                Point endP;
                Point startP;
                if(linePart == progressShape.length){
                    endP = progressShape[0];
                    startP = progressShape[linePart - 1];
                }else{
                    endP = progressShape[linePart];
                    startP = progressShape[linePart - 1];
                }
                float xProgress = (endP.x - startP.x) * lineProgress;
                float yProgress = (endP.y - startP.y) * lineProgress;
                path.lineTo(startP.x + xProgress, startP.y + yProgress);
                canvas.drawPath(path,progressPaint);
            }
//            canvas.drawPath(path, mPaint);
            ViewCompat.postInvalidateOnAnimation(mParent);
        }
    }


    private void newProgress(Canvas canvas, int color, int floor, float outProgress){
        if(newShape != null){
            Paint progressPaint = new Paint(mPaint);
            int progressLum = floor%2 == 0 ? (int) (60 - outProgress * 120) : (int) (60 - (1-outProgress) * 120);
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.set(new float[] {
                    1,0,0,0,progressLum,
                    0,1,0,0,progressLum,
                    0,0,1,0,progressLum,
                    0,0,0,1,0,
            });
            progressPaint.setColor(mParent.getResources().getColor(color));
//            progressPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            progressPaint.setStrokeWidth(lineWidth);
            progressPaint.setStyle(Paint.Style.STROKE);
            Path path = new Path();
            float pre_max = 1f/(float) (newShape.length);
            float progress = outProgress/pre_max;
            float lineProgress = progress%1f;
            int linePart = (int) Math.ceil(progress);
            for(int i = 0; i< Math.min(newShape.length, linePart); i++){
                Point p = progressShape[i];
                if(i == 0)
                    path.moveTo(p.x, p.y);
                else{
                    //先移动后连线而不是直接lineTo，这样线条转折处会带有弧度
                    Point leastP = newShape[i - 1];
                    path.moveTo(leastP.x, leastP.y);
                    path.lineTo(p.x, p.y);
                }
            }
            if(linePart < progressShape.length + 1 && linePart > 0){
                Point endP;
                Point startP;
                if(linePart == newShape.length){
                    endP = newShape[0];
                    startP = newShape[linePart - 1];
                }else{
                    endP = newShape[linePart];
                    startP = newShape[linePart - 1];
                }
                float xProgress = (endP.x - startP.x) * lineProgress;
                float yProgress = (endP.y - startP.y) * lineProgress;
                path.lineTo(startP.x + xProgress, startP.y + yProgress);
                canvas.drawLine(startP.x,startP.y,endP.x,endP.y,progressPaint);
            }
//            canvas.drawPath(path, mPaint);
            ViewCompat.postInvalidateOnAnimation(mParent);
        }
    }

    /**
     * 这里是根据描点画出图形的方法
     * @param canvas
     * @param color
     */
    private void drawLogoProgress(Canvas canvas, int color){
        if(shape != null){
            mPaint.setColor(mParent.getResources().getColor(color));
            mPaint.setStrokeWidth(lineWidth);
            Path path = new Path();
            float pre_max = 1f/(float) (shape.length);
            float progress = mTriggerPercentage/pre_max;
            float lineProgress = progress%1f;
            int linePart = (int) Math.ceil(progress);
            LogUtil.e("drawLogoProgress","shape"+shape.length+"\npre_max"+pre_max+"\nprogress"+progress+"\nlinePart"+linePart);
            for(int i = 0; i< Math.min(shape.length, linePart); i++){
                Point p = shape[i];
                if(i == 0 || i == 7 || i == 9)
                    path.moveTo(p.x, p.y);
                else{
                    //先移动后连线而不是直接lineTo，这样线条转折处会带有弧度
                    Point leastP = shape[i - 1];
                    path.moveTo(leastP.x, leastP.y);
                    path.lineTo(p.x, p.y);
                }
            }
            if(linePart < shape.length && linePart > 0){
                Point endP = shape[linePart];
                Point startP = shape[linePart - 1];
                float xProgress = (endP.x - startP.x) * lineProgress;
                float yProgress = (endP.y - startP.y) * lineProgress;
                if(linePart == 7 || linePart == 9)
                    path.moveTo(endP.x, endP.y);
                else
                    path.lineTo(startP.x + xProgress, startP.y + yProgress);
            }
            canvas.drawPath(path, mPaint);
        }
    }
    /**
     * 亮度调节  亮度范围  -127--126
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setLum(int lum){
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(new float[] {
                1,0,0,0,lum,
                0,1,0,0,lum,
                0,0,1,0,lum,
                0,0,0,1,0,
        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        ViewCompat.postInvalidateOnAnimation(mParent);
    }




    @Override
    public void setColorScheme(int... colors) {
        if(colors != null && colors.length > 0)
            lineColor = colors[0];
    }
}
