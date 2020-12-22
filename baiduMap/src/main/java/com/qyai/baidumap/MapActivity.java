package com.qyai.baidumap;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.permssion.PermissionCheckUtils;
import com.qyai.baidumap.postion.MyLocationListener;

import butterknife.BindView;
@Route(path = "/maplib/MapActivity")
public class MapActivity extends BaseHeadActivity {

    private LocationClient mClient;
    private MyLocationListener myLocationListener;
    @BindView(R2.id.mv_foreground)
    MapView mMapView;
    private BaiduMap mBaiduMap;
    private boolean isFirstLoc = true;


    @Override
    public int layoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected void initUIData() {
        setTvTitle("位置信息");
        initViews();
        PermissionCheckUtils.requestPermissions(MapActivity.this, Constants.REQUEST_CODE, Common.permissionList1); // 动态请求权限
        // 定位初始化
        mClient = new LocationClient(this);
        LocationClientOption mOption = new LocationClientOption();
        mOption.setScanSpan(5000);
        mOption.setCoorType("bd09ll");// 设置坐标类型
        mOption.setIsNeedAddress(true);
        mOption.setOpenGps(true);
        //设置locationClientOption
        mClient.setLocOption(mOption);
        myLocationListener = new MyLocationListener(mMapView,mBaiduMap,isFirstLoc);
        //注册LocationListener监听器
        mClient.registerLocationListener(myLocationListener);
        //设置后台定位
        //android8.0及以上使用NotificationUtils
        mClient.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mMapView = null;
        // 关闭前台定位服务
        mClient.disableLocInForeground(true);
        // 取消之前注册的 BDAbstractLocationListener 定位监听函数
        mClient.unRegisterLocationListener(myLocationListener);
        // 停止定位sdk
        mClient.stop();
    }

    private void initViews() {
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        /**
         * mCurrentMode = LocationMode.FOLLOWING;//定位跟随态
         * mCurrentMode = LocationMode.NORMAL;   //默认为 LocationMode.NORMAL 普通态
         * mCurrentMode = LocationMode.COMPASS;  //定位罗盘态
         */
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,
                true,
                null,
                0xAAFFFF88,
                0xAA00FF00));
    }



    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
}