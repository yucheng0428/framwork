package com.qyai.main.bracelet;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.IntentKey;
import com.lib.common.baseUtils.LogUtil;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.OnHttpCallBack;
import com.qyai.main.Common;
import com.qyai.main.R;
import com.qyai.main.bracelet.bean.ReqBraceletInfo;
import com.qyai.main.bracelet.bean.SyncBloodBean;
import com.qyai.main.bracelet.bean.SyncHeartBean;
import com.qyai.main.bracelet.bean.SyncHistiryBean;
import com.yucheng.ycbtsdk.Response.BleConnectResponse;
import com.yucheng.ycbtsdk.YCBTClient;

public class BlueToothService extends Service {
    public static final String CHANNEL_ID_STRING = "service_01";
    BleConnectResponse response = new BleConnectResponse() {
        @Override
        public void onConnectResponse(int i) {
            LogUtil.e("蓝牙连接状态", i + "");
            if (i == 3) {
                BraceletApi.getInstance().search(new BraceletCallBack() {
                    @Override
                    public void onSuccess(Object o) {

                    }
                });
            }
        }
    };

    /**
     * 数据同步服务(10秒一次)
     * 同步心率，步数
     * 轮询线程
     */
    private Handler syncDataHandler = new Handler();
    Runnable syncDataRunnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //提交请求
            syncDataHandler.removeCallbacksAndMessages(null);
            syncData();

        }

    };

    public void syncData() {
        syncDataHandler.postDelayed(syncDataRunnable, 60000);
        ReqBraceletInfo   info = new ReqBraceletInfo();
        String code = SPValueUtil.getStringValue(getApplicationContext(), Common.BRACELET_MAC);
        if (SPValueUtil.isEmpty(code)) {
            info.setCode(code);
        }
        Intent mIntent = new Intent();
        mIntent.setAction(BlueToothService.CHANNEL_ID_STRING);
        BraceletApi.getInstance().syncHistoryDataAll(0x0509, new BraceletCallBack() {
            @Override
            public void onSuccess(Object o) {
                mIntent.putExtra("data",(String)o);
                SyncHistiryBean bean = JSON.parseObject((String)o, SyncHistiryBean.class);
                if (bean != null && bean.getData().size() > 0) {
                    SyncHistiryBean.DataBean dataBean = bean.getData().get(bean.getData().size() - 1);
                    info.setTemperature(dataBean.getTempIntValue() + "");
                    info.setBloodOxygen(dataBean.getOOValue() + "");
                    info.setRespirationRate(dataBean.getRespiratoryRateValue() + "");
                }
               getApplicationContext().sendBroadcast(mIntent);
            }
        });
        BraceletApi.getInstance().syncHistoryDataAll(0x0506, new BraceletCallBack() {
            @Override
            public void onSuccess(Object o) {
                mIntent.putExtra("xinlv",(String)o);
                SyncHeartBean heartBean = JSON.parseObject((String)o, SyncHeartBean.class);
                if (heartBean != null && heartBean.getData().size() > 0) {
                    SyncHeartBean.DataBean hBean = heartBean.getData().get(heartBean.getData().size() - 1);
                    info.setHeartRate(hBean.getHeartValue() + "");
                }
               getApplicationContext().sendBroadcast(mIntent);
            }
        });
        BraceletApi.getInstance().syncHistoryDataAll(0x0508, new BraceletCallBack() {
            @Override
            public void onSuccess(Object o) {
                mIntent.putExtra("xueya",(String)o);
                SyncBloodBean bloodBean = JSON.parseObject((String)o, SyncBloodBean.class);
                if (bloodBean != null && bloodBean.getData().size() > 0) {
                    SyncBloodBean.DataBean bBean = bloodBean.getData().get(bloodBean.getData().size() - 1);
                    info.setBloodPressureH(bBean.getBloodDBP() + "");
                    info.setBloodPressureL(bBean.getBloodSBP() + "");
                }
                getApplicationContext().sendBroadcast(mIntent);
            }
        });
        syncDataHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reqMessage(info);
            }
        },5000);
    }

    public void reqMessage(ReqBraceletInfo info){
        HttpServiec.getInstance().postFlowableData(100, Common.UPLOADBRACELETINFO, info, new OnHttpCallBack() {
            @Override
            public void onSuccessful(int id, Object o) {

            }

            @Override
            public void onFaild(int id, Object o, String err) {

            }
        },String.class);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        /***适配8.0 解决报IllegalStateException  start***/
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID_STRING, getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);
            Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID_STRING).build();
            startForeground(1, notification);
        }
        /***适配8.0 解决报IllegalStateException  end***/
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        YCBTClient.registerBleStateChange(response);
        syncDataHandler.postDelayed(syncDataRunnable, 5000);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        syncDataHandler.removeCallbacksAndMessages(syncDataRunnable);
        LogUtil.e("蓝牙连接服务", "服务关了乐乐");
        YCBTClient.unRegisterBleStateChange(response);
    }


}
