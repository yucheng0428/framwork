package com.qyai.watch_app.message.websocket;

import com.qyai.watch_app.message.bean.AlarmPushBean;

public interface AlamListenser {
    //更新列表
    void reshAlamList();
    //根据推送信息更新列表
    void pushMsgReshList(AlarmPushBean bean);

}
