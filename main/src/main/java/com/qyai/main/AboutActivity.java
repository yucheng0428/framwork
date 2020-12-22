package com.qyai.main;



import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.common.base.BaseHeadActivity;
@Route(path = "/main/about")
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