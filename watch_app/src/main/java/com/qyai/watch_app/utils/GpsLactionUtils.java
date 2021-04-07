package com.qyai.watch_app.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.Toast;

import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.LogUtil;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.netHttp.HttpReq;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.OnHttpCallBack;
import com.qyai.watch_app.home2.UserEvent;

import java.util.Iterator;
import java.util.List;

public class GpsLactionUtils {
    public Context mActivity;
    public static  GpsLactionUtils instance;
    public LocationListener locationlistener;
    public GpsStatus.Listener gpsListener;
    public  LocationManager locationManager;
    String locationProvider = null;

    public GpsLactionUtils(Context activity) {
        this.mActivity=activity;
    }

    public static  GpsLactionUtils getInstance(Context activity){
        if(instance==null){
            instance=new GpsLactionUtils(activity);
        }
        return instance;
    }

    @SuppressLint("MissingPermission")
    public void startGps(){
        //1.获取位置管理器
        locationManager = (LocationManager) mActivity.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        //2.获取位置提供器，GPS或是NetWork
        List<String> providers = locationManager.getProviders(true);

        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS定位
            locationProvider = LocationManager.GPS_PROVIDER;
        }
        else {
            Toast.makeText(mActivity, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
        }
        LogUtil.e("Gps","startGps()");
        setInitListener();
        locationManager.addGpsStatusListener(gpsListener);
//            // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
        locationManager.requestLocationUpdates(locationProvider, 1000, 1,locationlistener );
    }

    @SuppressLint("MissingPermission")
    public  void  stopGps(){
        LogUtil.e("Gps","stopGps()");
        if(locationManager==null){
            return;
        }
        locationManager.removeGpsStatusListener(gpsListener);
        locationlistener=null;
        locationManager=null;
    }
    public void sendLoaction(LactionInfo info){
        GpsReq gpsReq=new GpsReq();
        gpsReq.setPosZ(0+"");
        gpsReq.setUserId(SPValueUtil.getStringValue(mActivity, Common.USER_ID));
        if(info!=null){
            gpsReq.setPosX(info.getLocX());
            gpsReq.setPosY(info.getLocY());
        }
        HttpServiec.getInstance().postFlowableData(100, HttpReq.getInstence().getIp()+"user/addPosition", gpsReq, new OnHttpCallBack() {
            @Override
            public void onSuccessful(int id, Object o) {
//                LogUtil.e("GPS_REQ_onSuccessful", o.toString());
            }

            @Override
            public void onFaild(int id, Object o, String err) {
//                LogUtil.e("GPS_REQ_onFaild", err);
            }
        }, String.class);
    }

    @SuppressLint("MissingPermission")
    public void setInitListener(){
        locationlistener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (locationManager==null){
                    return;
                }
                LogUtil.e("Gps","onLocationChanged：改变位置");
                UIHelper.ToastMessage(mActivity.getApplicationContext(), "onLocationChanged：改变位置");
                try {
                    sendLoaction(getNewInfo(location));
                }catch (Exception e){

                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                switch (i) {
                    //GPS状态为可见时
                    case LocationProvider.AVAILABLE:
                        LogUtil.e("Gps","onStatusChanged：当前GPS状态为可见状态");
                        UIHelper.ToastMessage(mActivity.getApplicationContext(), "onStatusChanged：当前GPS状态为可见状态");

                        break;
                    //GPS状态为服务区外时
                    case LocationProvider.OUT_OF_SERVICE:
                        LogUtil.e("Gps","onStatusChanged:当前GPS状态为服务区外状态");

                        break;
                    //GPS状态为暂停服务时
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        LogUtil.e("Gps","onStatusChanged:当前GPS状态为暂停服务状态");

                        break;
                }
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        gpsListener= new GpsStatus.Listener() {
            @Override
            public void onGpsStatusChanged(int event) {
                switch (event) {
                    case GpsStatus.GPS_EVENT_FIRST_FIX:
                        LogUtil.e("Gps", "第一次定位");
                        break;
                    // 卫星状态改变
                    case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                        if(locationManager==null){
                            return;
                        }
                        LogUtil.e("Gps", "卫星状态改变");
                        GpsStatus gpsStatus = locationManager.getGpsStatus(null);
                        // 获取卫星颗数的默认最大值
                        int maxSatellites = gpsStatus.getMaxSatellites();
                        // 创建一个迭代器保存所有卫星
                        Iterator<GpsSatellite> iters = gpsStatus.getSatellites()
                                .iterator();
                        int count = 0;
                        while (iters.hasNext() && count <= maxSatellites) {
                            GpsSatellite s = iters.next();
                            count++;
                        }
                        LogUtil.e("Gps","搜索到：" + count + "颗卫星");
                        break;
                    //GPS状态为可见时
                    case LocationProvider.AVAILABLE:
                        LogUtil.e("Gps","onGpsStatusChanged：当前GPS状态为可见状态");
                        UIHelper.ToastMessage(mActivity.getApplicationContext(), "onGpsStatusChanged：当前GPS状态为可见状态");

                        break;
                    //GPS状态为服务区外时
                    case LocationProvider.OUT_OF_SERVICE:
                        LogUtil.e("Gps","onGpsStatusChanged:当前GPS状态为服务区外状态");

                        break;
                    //GPS状态为暂停服务时
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        LogUtil.e("Gps","onGpsStatusChanged:当前GPS状态为暂停服务状态");

                        break;
                }
            }
        };
    }


    public  LactionInfo getNewInfo(Location location){
        UIHelper.ToastMessage(mActivity,"经纬度longitude:" + location.getLongitude() + "latitude: " + location.getLatitude());
        LogUtil.e("GPS","onGpsStatusChanged:当前GPS状态为暂停服务状态");
        LactionInfo info=new LactionInfo();
        if (location != null) {
            info.setLocX(location.getLongitude() + "");
            info.setLocY(location.getLatitude() + "");
        }
        return info;
    }
}
