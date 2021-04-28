package com.qyai.main;



import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.Common;

@Route(path = Common.ABOUT_VIEW)
public class AboutActivity extends BaseHeadActivity {


    @Override
    public int layoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initUIData() {
        setTvTitle("关于");
    }
}