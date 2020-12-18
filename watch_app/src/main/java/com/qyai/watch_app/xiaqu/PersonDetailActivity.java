package com.qyai.watch_app.xiaqu;


import com.lib.common.base.BaseHeadActivity;
import com.qyai.watch_app.R;

public class PersonDetailActivity extends BaseHeadActivity {


    @Override
    public int layoutId() {
        return R.layout.activity_person_detail;
    }

    @Override
    protected void initUIData() {
        setTvTitle("人员详情");
    }
}