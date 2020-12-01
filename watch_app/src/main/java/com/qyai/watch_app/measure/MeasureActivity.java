package com.qyai.watch_app.measure;


import com.lib.common.base.BaseHeadActivity;
import com.qyai.watch_app.R;

/**
 * 测量界面
 */
public class MeasureActivity extends BaseHeadActivity {
    public String title;
    public int type;

    @Override
    public int layoutId() {
        return R.layout.activity_measure;
    }

    @Override
    protected void initUIData() {
    setTvTitle("血压测量");
    }
}