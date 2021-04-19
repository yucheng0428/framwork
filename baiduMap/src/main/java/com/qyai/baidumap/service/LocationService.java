package com.qyai.baidumap.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.webkit.WebView;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.DateUtils;
import com.lib.common.baseUtils.LogUtil;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.netHttp.HttpReq;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.OnHttpCallBack;
import com.qyai.baidumap.R;

/**
 * 高德地图   后台定位服务
 * 定位服务LocationClient 相关
 *
 * @author baidu
 */
public class LocationService extends Service {
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    public static final String CHANNEL_ID_STRING = "service_message";
    private NotificationManager manager = null;

    @Override
    public void onCreate() {
        super.onCreate();
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
        initLocation();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startLocation();
        return super.onStartCommand(intent, flags, startId);
    }

    public static void initService(Activity activity) {
        Intent serviceIntent = new Intent(activity, LocationService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.startForegroundService(serviceIntent);
        } else {
            activity.startService(serviceIntent);
        }
    }


    public static void stopService(Activity activity) {
        Intent stopIntent = new Intent(activity, LocationService.class);
        activity.stopService(stopIntent);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    /**
     * 开始定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void startLocation(){
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void stopLocation(){
        // 停止定位
        locationClient.stopLocation();
    }
    /**
     * 初始化定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 0 定位失败
     *
     * 1 GPS定位结果
     *
     * 2 前次定位结果     网络定位请求低于1秒、或两次定位之间设备位置变化非常小时返回，设备位移通过传感器感知。
     *
     * 4 缓存定位结果     返回一段时间前设备在同样的位置缓存下来的网络定位结果
     *
     * 5 Wifi定位结果    属于网络定位，定位精度相对基站定位会更好，定位精度较高，在5米－200米之间。
     *
     * 6 基站定位结果    纯粹依赖移动、联通、电信等移动网络定位，定位精度在500米-5000米之间。
     *
     * 8 离线定位结果
     *
     * 9  最后位置缓存   定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {


                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {

//                    sb.append("定位类型: " + location.getLocationType() + "\n");
//                    sb.append("经    度    : " + location.getLongitude() + "\n");
//                    sb.append("纬    度    : " + location.getLatitude() + "\n");
//                    sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
//                    sb.append("提供者    : " + location.getProvider() + "\n");
                    sendLoaction(location.getLongitude()+"",location.getLatitude()+"");
                    LogUtil.writE("location",DateUtils.getCurrentTime_Today() +"===>lng="+location.getLongitude()+"&lat="+location.getLatitude()+"===>定位来源"+location.getLocationType());
                }
            }
        }
    };
    public void sendLoaction(String longitude,String Latitude) {
        GpsReq gpsReq = new GpsReq();
        gpsReq.setPosZ(0 + "");
        gpsReq.setUserId(SPValueUtil.getStringValue(BaseApp.getIns(), Common.USER_ID));
        if (SPValueUtil.isEmpty(longitude)&& SPValueUtil.isEmpty(Latitude)) {
            gpsReq.setPosX(longitude);
            gpsReq.setPosY(Latitude);
        }
        HttpServiec.getInstance().postFlowableData(100, HttpReq.getInstence().getIp() + "user/addPosition", gpsReq, new OnHttpCallBack() {
            @Override
            public void onSuccessful(int id, Object o) {
            }

            @Override
            public void onFaild(int id, Object o, String err) {
            }
        }, String.class);
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        //高精度定位模式：会同时使用网络定位和GPS定位，优先返回最高精度的定位结果，以及对应的地址描述信息。
        //低功耗定位模式：不会使用GPS和其他传感器，只会使用网络定位（Wi-Fi和基站定位）
        //仅用设备定位模式：不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位，
        // 需要在室外环境下才可以成功定位。仅设备定位模式下支持返回地址描述信息。
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(5000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(false); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationManager mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mManager.cancel(10001);
        stopLocation();

    }
}