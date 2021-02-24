package com.qyai.watch_app.message;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.LogUtil;
import com.lib.common.baseUtils.UIHelper;
import com.qyai.watch_app.R;
import com.qyai.watch_app.message.bean.AlarmPushBean;
import com.qyai.watch_app.message.websocket.AlamListenser;
import com.qyai.watch_app.message.websocket.PushListener;
import com.qyai.watch_app.message.websocket.WebSocketUtlts;

import org.java_websocket.client.WebSocketClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompMessage;


public class MessageService extends Service  {
    public static final String CHANNEL_ID_STRING = "service_message";
    private static AlamListenser mAlamListenser;


    /**
     * 手机消息通知管理器，用于弹系统消息通知
     */
    private NotificationManager manager = null;
    private NotificationChannel notificationChannel = null;
    String CHANNEL_ONE_NAME = "One";
    String CHANNEL_ONE_ID = "com.qyai.base";
    private int intervalTime=10000;
    WebSocketUtlts webSocketUtlts;


    public static void setAlamListenser(AlamListenser alamListenser) {
        mAlamListenser = alamListenser;
    }

    /**
     * 轮询服务器消息(3秒一次)
     * 获取消息通知，如新警情、预警任务的提醒
     */
    private Handler queryAlarmInSignHandler = new Handler();
    /**
     * 消息通知轮询线程
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //提交请求
            queryAlarmInSignHandler.removeCallbacksAndMessages(null);
            queryAlarmInSign();

        }

    };
    /**
     * 停止轮询告警
     */
    private void stopRunnable() {
        queryAlarmInSignHandler.removeCallbacksAndMessages(null);
    }
    //轮询告警
    public void queryAlarmInSign() {
        mAlamListenser.reshAlamList();
        queryAlarmInSignHandler.postDelayed(runnable, intervalTime);
    }

    @Override
    public Context getApplicationContext() {
        return BaseApp.getIns();
    }
    /**
     * 创建手机信息通知，进行业务内容提醒
     *
     * @param bean
     */
    private void createNotification(AlarmPushBean bean) {
        Intent intent = new Intent(this.getApplicationContext(), AlarmDetailActivity.class);
        // 点击Notification不重启MainActivity.class
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.putExtra("info",bean);
        PendingIntent pendingIntent = PendingIntent.getActivity(this.getApplicationContext(), 10001, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String content = bean.getContent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationChannel == null) {
                notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                        CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.enableLights(true);
                notificationChannel.enableVibration(true);
//                notificationChannel.setVibrationPattern(new long[]{100, 200, 300});
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.setShowBadge(true);
                notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                manager.createNotificationChannel(notificationChannel);
            }
            @SuppressLint("WrongConstant") NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ONE_ID)
                    .setTicker(getResources().getString(R.string.app_name))
                    .setSmallIcon(R.mipmap.icon_message_blue)
                    .setContentTitle("告警提示")
                    .setContentText(content)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)//点击后消失
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setVisibility(Notification.VISIBILITY_PUBLIC)
                    .setDefaults(NotificationCompat.DEFAULT_ALL);
//                    .setVibrate(new long[]{100, 200, 300});
            Notification notification = builder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            manager.notify(10001, notification);
//            startForeground(Constants.NOTICE_ID, notification);//点击后不能消失

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Notification.Builder builder = new Notification.Builder(this.getApplicationContext());
            builder.setSmallIcon(R.mipmap.icon_message_blue);
            builder.setContentTitle("标题");
            builder.setContentText(content);
            builder.setDefaults(NotificationCompat.DEFAULT_ALL);
            builder.setPriority(Notification.PRIORITY_MAX);
            builder.setContentIntent(pendingIntent);
            builder.setVisibility(Notification.VISIBILITY_PUBLIC);
            builder.setAutoCancel(true);//点击后消失
            Notification notification = builder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
//            notification.defaults = Notification.DEFAULT_SOUND;
//            startForeground(Constants.NOTICE_ID, notification);//点击后不能消失
            manager.notify(10001, notification);
        }
    }





    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        position = LocationUtils.getRandomNumber(LocationUtils.getRawData(getResources().openRawResource(R.raw.a)).size());
        /***适配8.0 解决报IllegalStateException  start***/
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID_STRING, getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);
            Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID_STRING)
                    .setSmallIcon(R.mipmap.icon_app)
                    .setContentTitle("健康守护运行中")
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_app))
                    .build();

            startForeground(1, notification);
        }
        /***适配8.0 解决报IllegalStateException  end***/
        //获取手机通知对象
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    /**
     * 服务启动入口，每次服务启动时都会调用该方法
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        queryAlarmInSignHandler.postDelayed(runnable, intervalTime);
        //启动长连接
        webSocketUtlts=new WebSocketUtlts(getApplicationContext(), new PushListener() {
            @Override
            public void pushMsg(AlarmPushBean bean) {
                createNotification(bean);
                mAlamListenser.pushMsgReshList(bean);
            }
        });
        webSocketUtlts.stompConnect();
        return START_STICKY;
    }



    public static void initService(Activity activity) {
        Intent serviceIntent = new Intent(activity, MessageService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.startForegroundService(serviceIntent);
        } else {
            activity.startService(serviceIntent);
        }
    }


    public static void stopService(Activity activity) {
        Intent stopIntent = new Intent(activity, MessageService.class);
        activity.stopService(stopIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRunnable();
        NotificationManager mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mManager.cancel(10001);
        if(webSocketUtlts!=null){
            webSocketUtlts.stopConnect();
        }
    }
}
