package com.qyai.watch_app.message;

import com.lib.common.base.BaseHeadActivity;
import com.qyai.watch_app.R;

public class MessageDetailAct extends BaseHeadActivity {
    @Override
    public int layoutId() {
        return R.layout.activity_message_detail;
    }

    @Override
    protected void initUIData() {
       setTvTitle("消息详情");
    }
}
