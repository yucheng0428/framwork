package com.qyai.watch_app.postion;



import com.lib.common.base.BaseHeadActivity;
import com.qyai.watch_app.R;

public class PostionActivity extends BaseHeadActivity {
  public String title="位置";

    @Override
    public int layoutId() {
        return R.layout.activity_postion;
    }

    @Override
    protected void initUIData() {
         title=getIntent().getStringExtra("title");
    }
}