package com.qyai.main.bracelet;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSON;
import com.lib.common.baseUtils.LogUtil;

import com.lib.common.baseUtils.UIHelper;
import com.lib.common.baseUtils.permssion.PermissionCheckUtils;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.baseUtils.SPValueUtil;

import com.lib.common.netHttp.OnHttpCallBack;
import com.qyai.main.Common;
import com.qyai.main.R;
import com.qyai.main.bracelet.bean.LactionInfo;
import com.qyai.main.bracelet.bean.ReqBraceletInfo;
import com.qyai.main.bracelet.bean.SyncBloodBean;
import com.qyai.main.bracelet.bean.SyncHeartBean;
import com.qyai.main.bracelet.bean.SyncHistiryBean;
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

    Runnable uploadLoaction = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void run() {
//            syncDataHandler.removeCallbacksAndMessages(null);
//
             sendLoaction(getLocation());
//            syncDataHandler.postDelayed(uploadLoaction, 1000);
        }
    };

    public void sendLoaction(LactionInfo info){
        HttpServiec.getInstance().postFlowableData(100, Common.UPLOADLOCATION, info, new OnHttpCallBack() {
                            @Override
                public void onSuccessful(int id, Object o) {

                }

                @Override
                public void onFaild(int id, Object o, String err) {

                }
            }, String.class);
    }
    public void syncData() {
        syncDataHandler.postDelayed(syncDataRunnable, 60000);
        ReqBraceletInfo info = new ReqBraceletInfo();
        String code = SPValueUtil.getStringValue(getApplicationContext(), Common.BRACELET_MAC);
        if (SPValueUtil.isEmpty(code)) {
            info.setWatchId(code);
        }
        Intent mIntent = new Intent();
        mIntent.setAction(BlueToothService.CHANNEL_ID_STRING);
        BraceletApi.getInstance().syncHistoryDataAll(0x0509, new BraceletCallBack() {
            @Override
            public void onSuccess(Object o) {
                mIntent.putExtra("data", (String) o);
                SyncHistiryBean bean = JSON.parseObject((String) o, SyncHistiryBean.class);
                if (bean != null && bean.getData().size() > 0) {
                    SyncHistiryBean.DataBean dataBean = bean.getData().get(bean.getData().size() - 1);
                    info.setTemperature(dataBean.getTempIntValue() + "");
                    info.setOxygen(dataBean.getOOValue() + "");
                    info.setBreathingRate(dataBean.getRespiratoryRateValue() + "");
                }
                getApplicationContext().sendBroadcast(mIntent);
            }
        });
        BraceletApi.getInstance().syncHistoryDataAll(0x0506, new BraceletCallBack() {
            @Override
            public void onSuccess(Object o) {
                mIntent.putExtra("xinlv", (String) o);
                SyncHeartBean heartBean = JSON.parseObject((String) o, SyncHeartBean.class);
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
                mIntent.putExtra("xueya", (String) o);
                SyncBloodBean bloodBean = JSON.parseObject((String) o, SyncBloodBean.class);
                if (bloodBean != null && bloodBean.getData().size() > 0) {
                    SyncBloodBean.DataBean bBean = bloodBean.getData().get(bloodBean.getData().size() - 1);
                    info.setBloodPressureHigh(bBean.getBloodDBP() + "");
                    info.setBloodPressureLow(bBean.getBloodSBP() + "");
                }
                getApplicationContext().sendBroadcast(mIntent);
            }
        });
        syncDataHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reqMessage(info);
            }
        }, 5000);
    }

    public void reqMessage(ReqBraceletInfo info) {
        HttpServiec.getInstance().postFlowableData(100, Common.UPLOADBRACELETINFO, info, new OnHttpCallBack() {
            @Override
            public void onSuccessful(int id, Object o) {

            }

            @Override
            public void onFaild(int id, Object o, String err) {

            }
        }, String.class);
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
//        syncDataHandler.postDelayed(uploadLoaction, 5000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sendLoaction(getLocation());
        }
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


    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public LactionInfo getLocation() {
        LactionInfo info = new LactionInfo();

        String locationProvider = null;
        //1.获取位置管理器
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        //2.获取位置提供器，GPS或是NetWork
        List<String> providers = locationManager.getProviders(true);

         if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS定位
            locationProvider = LocationManager.GPS_PROVIDER;
        }
        else {
            Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
        }

        //3.获取上次的位置，一般第一次运行，此值为null
//        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(locationProvider);
//        if (location != null) {
//               info=getNewInfo(location);
//        } else {
            locationManager.addGpsStatusListener(new GpsStatus.Listener() {
                @Override
                public void onGpsStatusChanged(int i) {
                    switch (i) {
                        //GPS状态为可见时
                        case LocationProvider.AVAILABLE:
                            UIHelper.ToastMessage(getApplicationContext(), "onGpsStatusChanged：当前GPS状态为可见状态");

                            break;
                        //GPS状态为服务区外时
                        case LocationProvider.OUT_OF_SERVICE:
                            UIHelper.ToastMessage(getApplicationContext(), "onGpsStatusChanged:当前GPS状态为服务区外状态");

                            break;
                        //GPS状态为暂停服务时
                        case LocationProvider.TEMPORARILY_UNAVAILABLE:
                            UIHelper.ToastMessage(getApplicationContext(), "onGpsStatusChanged:当前GPS状态为暂停服务状态");

                            break;
                    }
                }
            });
//            // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
            locationManager.requestLocationUpdates(locationProvider, 1000, 1, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    UIHelper.ToastMessage(getApplicationContext(), "onLocationChanged：改变位置");
                      sendLoaction(getNewInfo(location));
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                    switch (i) {
                        //GPS状态为可见时
                        case LocationProvider.AVAILABLE:
                            UIHelper.ToastMessage(getApplicationContext(), "onStatusChanged：当前GPS状态为可见状态");

                            break;
                        //GPS状态为服务区外时
                        case LocationProvider.OUT_OF_SERVICE:
                            UIHelper.ToastMessage(getApplicationContext(), "onStatusChanged:当前GPS状态为服务区外状态");

                            break;
                        //GPS状态为暂停服务时
                        case LocationProvider.TEMPORARILY_UNAVAILABLE:
                            UIHelper.ToastMessage(getApplicationContext(), "onStatusChanged:当前GPS状态为暂停服务状态");

                            break;
                    }
                }

                @Override
                public void onProviderEnabled(String s) {
                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });

          return  info;

    }



    public  LactionInfo getNewInfo(Location location){
        Log.e("经纬度", "longitude:" + location.getLongitude() + "latitude: " + location.getLatitude());
        LactionInfo info=new LactionInfo();
        if (location != null) {
            info.setLocX(location.getLongitude() + "");
            info.setLocY(location.getLatitude() + "");
            String code = SPValueUtil.getStringValue(getApplicationContext(), Common.BRACELET_MAC);
            if (SPValueUtil.isEmpty(code)) {
                info.setDeviceId(code);
            }
        }
        return info;
    }



}
