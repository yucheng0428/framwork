package com.lib.picturecontrol.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;

import com.lib.common.R;


/**
 * 带有最大高度的scrollView
 * 支持scrollView相互嵌套滑动
 * Created by GYH on 2017/6/21.
 */

public class MaxHeightScrollView extends NestedScrollView {
    /**
     * 触摸滑动方向--向上
     */
    public static final int DIRECTION_UP = 0X100;
    /**
     * 触摸滑动方向--向下
     */
    public static final int DIRECTION_DOWN = 0X200;

    private ScrollView parentScrollView;

    private Context mContext;
    private float maxHeight;

    public MaxHeightScrollView(Context context) {
        super(context);
        this.mContext = context;
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray mTypedArray = mContext.obtainStyledAttributes(attrs, R.styleable.MaxScrollView);
        maxHeight = mTypedArray.getDimension(R.styleable.MaxScrollView_max_height, .0f);
        mTypedArray.recycle();
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray mTypedArray = mContext.obtainStyledAttributes(attrs, R.styleable.MaxScrollView);
        maxHeight = mTypedArray.getDimension(R.styleable.MaxScrollView_max_height, .0f);
        mTypedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            //此处是关键，设置控件高度不能超过屏幕高度一半（d.heightPixels / 2）（在此替换成自己需要的高度）
            if(maxHeight != 0)
                heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) maxHeight, MeasureSpec.AT_MOST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //重新计算控件高、宽
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置嵌套的scrollView
     */
    public void setScrollView(ScrollView scrollView) {
        parentScrollView = scrollView;
    }

    private void setParendScrollable(boolean flag) {
        Log.d("attachScroll", "flag: " + flag);
        if (parentScrollView != null){
            parentScrollView.requestDisallowInterceptTouchEvent(flag);
            if(parentScrollView.getParent() != null)
                parentScrollView.getParent().requestDisallowInterceptTouchEvent(flag);
        } else if (getParent() instanceof ScrollView) {
            ((ScrollView) getParent()).requestDisallowInterceptTouchEvent(flag);
            if(getParent().getParent() != null)
                getParent().getParent().requestDisallowInterceptTouchEvent(flag);
        }
    }
}
