package com.qyai.main.bracelet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.OnHttpCallBack;
import com.qyai.main.Common;
import com.qyai.main.bracelet.bean.LactionInfo;

import java.util.List;

public class GpsLactionUtils {
    public Activity mActivity;
    public static  GpsLactionUtils instance;
    public LocationListener locationlistener;
    public GpsStatus.Listener gpsListener;
    public  LocationManager locationManager;
    String locationProvider = null;

    public GpsLactionUtils(Activity activity) {
        this.mActivity=activity;
    }

    public static  GpsLactionUtils getInstance(Activity activity){
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
        setInitListener();
        locationManager.addGpsStatusListener(gpsListener);
//            // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
        locationManager.requestLocationUpdates(locationProvider, 1000, 1,locationlistener );
    }

    @SuppressLint("MissingPermission")
    public  void  stopGps(){
        if(locationManager==null){
            return;
        }
        locationManager.addGpsStatusListener(null);
//            // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
        locationManager.requestLocationUpdates(locationProvider, 1000, 1,locationlistener );
    }
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

    @SuppressLint("MissingPermission")
    public void setInitListener(){
        locationlistener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                UIHelper.ToastMessage(mActivity.getApplicationContext(), "onLocationChanged：改变位置");
                sendLoaction(getNewInfo(location));
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                switch (i) {
                    //GPS状态为可见时
                    case LocationProvider.AVAILABLE:
                        UIHelper.ToastMessage(mActivity.getApplicationContext(), "onStatusChanged：当前GPS状态为可见状态");

                        break;
                    //GPS状态为服务区外时
                    case LocationProvider.OUT_OF_SERVICE:
                        UIHelper.ToastMessage(mActivity.getApplicationContext(), "onStatusChanged:当前GPS状态为服务区外状态");

                        break;
                    //GPS状态为暂停服务时
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        UIHelper.ToastMessage(mActivity.getApplicationContext(), "onStatusChanged:当前GPS状态为暂停服务状态");

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
                    //GPS状态为可见时
                    case LocationProvider.AVAILABLE:
                        UIHelper.ToastMessage(mActivity.getApplicationContext(), "onGpsStatusChanged：当前GPS状态为可见状态");

                        break;
                    //GPS状态为服务区外时
                    case LocationProvider.OUT_OF_SERVICE:
                        UIHelper.ToastMessage(mActivity.getApplicationContext(), "onGpsStatusChanged:当前GPS状态为服务区外状态");

                        break;
                    //GPS状态为暂停服务时
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        UIHelper.ToastMessage(mActivity.getApplicationContext(), "onGpsStatusChanged:当前GPS状态为暂停服务状态");

                        break;
                }
            }
        };
    }


    public  LactionInfo getNewInfo(Location location){
        Log.e("经纬度", "longitude:" + location.getLongitude() + "latitude: " + location.getLatitude());
        LactionInfo info=new LactionInfo();
        if (location != null) {
            info.setLocX(location.getLongitude() + "");
            info.setLocY(location.getLatitude() + "");
            String code = SPValueUtil.getStringValue(mActivity.getApplicationContext(), Common.BRACELET_MAC);
            if (SPValueUtil.isEmpty(code)) {
                info.setDeviceId(code);
                info.setWatchType("bracelet");
            }
        }
        return info;
    }
}
