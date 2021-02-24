package com.qyai.watch_app.message.websocket;

import com.qyai.watch_app.message.bean.AlarmPushBean;

public interface PushListener {

    void pushMsg(AlarmPushBean bean);
}
