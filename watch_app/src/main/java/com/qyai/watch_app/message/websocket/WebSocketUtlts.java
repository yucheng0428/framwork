package com.qyai.watch_app.message.websocket;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.LogUtil;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.netHttp.HttpReq;
import com.lib.common.netHttp.HttpServiec;
import com.qyai.watch_app.message.bean.AlarmPushBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompHeader;
import ua.naiksoftware.stomp.dto.StompMessage;


public class WebSocketUtlts {
    public Context mContext;
    public static  final String TAG="WebSocket";
    private StompClient mStompClient;
    private CompositeDisposable compositeDisposable;
    private PushListener pushListener;

    public WebSocketUtlts(Context mContext, PushListener pushListener) {
        this.mContext = mContext;
        this.pushListener = pushListener;
    }

    public void stompConnect(){
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, HttpReq.getInstence().getIp()+"websocket");
        connectStomp();

    }
    public void connectStomp() {
        mStompClient.withClientHeartbeat(1000).withServerHeartbeat(1000);
        resetSubscriptions();
        Disposable dispLifecycle = mStompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lifecycleEvent -> {
                    switch (lifecycleEvent.getType()) {
                        case OPENED:
                            LogUtil.e(TAG,"连接成功");
                            break;
                        case ERROR:
                            Log.e(TAG, "Stomp connection error", lifecycleEvent.getException());
                            LogUtil.e(TAG,"Stomp connection error");
                            break;
                        case CLOSED:
                            LogUtil.e(TAG,"Stomp connection closed");
                            resetSubscriptions();
                            break;
                        case FAILED_SERVER_HEARTBEAT:
                            LogUtil.e(TAG,"Stomp failed server heartbeat");
                            break;
                    }
                });

        compositeDisposable.add(dispLifecycle);
        // Receive greetings
        Disposable dispTopic = mStompClient.topic("/user/queue/subNewAlarm")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((StompMessage topicMessage) -> {

                    Log.e(TAG, "推送 " + topicMessage.getPayload());
                    //添加你的数据逻辑
                    try{
                        AlarmPushBean bean= JSONObject.parseObject(topicMessage.getPayload(),AlarmPushBean.class);
                        pushListener.pushMsg(bean);

                    }catch (Exception e){
                        Log.e(TAG, "推送数据解析出错" );
                    }

                }, throwable -> {
                    Log.e(TAG, "连接错误", throwable);
                });

        compositeDisposable.add(dispTopic);
        List<StompHeader> headers=new ArrayList<>();
        headers.add(new StompHeader("token", SPValueUtil.getStringValue(mContext, Common.USER_TOKEN)));
        mStompClient.connect(headers);
    }

    private void resetSubscriptions() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        compositeDisposable = new CompositeDisposable();
    }


    public void stopConnect(){
        mStompClient.disconnect();
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }
}
