package com.qyai.main.bracelet;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.lib.common.baseUtils.LogUtil;
import com.lib.common.baseUtils.permssion.PermissionCheckUtils;
import com.lib.common.netHttp.HttpServiec;
import com.qyai.main.R;
import com.yucheng.ycbtsdk.Response.BleConnectResponse;
import com.yucheng.ycbtsdk.YCBTClient;

import java.util.List;

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

    Runnable uploadLoaction=new Runnable() {
        @Override
        public void run() {
            syncDataHandler.removeCallbacksAndMessages(null);
            Location location=getLastKnownLocation();
//            HttpServiec.getInstance().postFlowableData(100,Con);
            Log.e("经纬度","longitude:" + location.getLongitude() + "latitude: " + location.getLatitude());
        }
    };

    public void syncData() {
        syncDataHandler.postDelayed(syncDataRunnable, 60000);
        Intent mIntent = new Intent();
        mIntent.setAction(BlueToothService.CHANNEL_ID_STRING);
        BraceletApi.getInstance().syncHistoryDataAll(0x0509, new BraceletCallBack() {
            @Override
            public void onSuccess(Object o) {
                mIntent.putExtra("data",(String)o);
               getApplicationContext().sendBroadcast(mIntent);
            }
        });
        BraceletApi.getInstance().syncHistoryDataAll(0x0506, new BraceletCallBack() {
            @Override
            public void onSuccess(Object o) {
                mIntent.putExtra("xinlv",(String)o);
               getApplicationContext().sendBroadcast(mIntent);
            }
        });
        BraceletApi.getInstance().syncHistoryDataAll(0x0508, new BraceletCallBack() {
            @Override
            public void onSuccess(Object o) {
                mIntent.putExtra("xueya",(String)o);
                getApplicationContext().sendBroadcast(mIntent);
            }
        });
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
        syncDataHandler.postDelayed(uploadLoaction,1000);
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

    /**
     * 定位：得到位置对象
     * @return
     */
    private Location getLastKnownLocation() {


        //获取地理位置管理器
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }
}
