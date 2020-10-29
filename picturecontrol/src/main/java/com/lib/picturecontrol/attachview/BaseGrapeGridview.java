package com.lib.picturecontrol.attachview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by GYH on 2017/9/22.
 */

public class BaseGrapeGridview extends GridView {

    public BaseGrapeGridview(Context context) {
        super(context);
    }

    public BaseGrapeGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseGrapeGridview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
