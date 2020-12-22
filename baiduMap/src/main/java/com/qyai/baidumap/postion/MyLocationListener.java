package com.qyai.baidumap.postion;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.lib.common.base.BaseApp;
import com.lib.common.baseUtils.UIHelper;

public class MyLocationListener extends BDAbstractLocationListener {
    MapView mMapView;
    BaiduMap mBaiduMap;
    boolean isFirstLoc = true;

    public MyLocationListener(MapView mMapView, BaiduMap mBaiduMap, boolean isFirstLoc) {
        this.mMapView = mMapView;
        this.mBaiduMap = mBaiduMap;
        this.isFirstLoc = isFirstLoc;
    }

    public MapView getmMapView() {
        return mMapView;
    }

    public void setmMapView(MapView mMapView) {
        this.mMapView = mMapView;
    }

    public BaiduMap getmBaiduMap() {
        return mBaiduMap;
    }

    public void setmBaiduMap(BaiduMap mBaiduMap) {
        this.mBaiduMap = mBaiduMap;
    }

    public boolean isFirstLoc() {
        return isFirstLoc;
    }

    public void setFirstLoc(boolean firstLoc) {
        isFirstLoc = firstLoc;
    }

    public MyLocationListener() {
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation == null || mMapView == null) {
            return;
        }
        MyLocationData locData = new MyLocationData.Builder().accuracy(bdLocation.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(bdLocation.getDirection()).latitude(bdLocation.getLatitude())
                .longitude(bdLocation.getLongitude()).build();
        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);
        //地图SDK处理
        if (isFirstLoc) {
            isFirstLoc = false;
            LatLng ll = new LatLng(bdLocation.getLatitude(),
                    bdLocation.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }
        LatLng point = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
        OverlayOptions dotOption = new DotOptions().center(point).color(0xAAA9A9A9);
        mBaiduMap.addOverlay(dotOption);
//         UIHelper.ToastMessage(BaseApp.getIns(), point.toString());
    }
}
