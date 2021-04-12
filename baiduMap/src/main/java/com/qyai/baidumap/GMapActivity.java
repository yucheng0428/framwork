package com.qyai.baidumap;


import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.baidu.mapapi.search.share.OnGetShareUrlResultListener;
import com.baidu.mapapi.search.share.PoiDetailShareURLOption;
import com.baidu.mapapi.search.share.ShareUrlResult;
import com.baidu.mapapi.search.share.ShareUrlSearch;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.LogUtil;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.baseUtils.baseModle.BaseResult;
import com.lib.common.baseUtils.permssion.PermissionCheckUtils;
import com.lib.common.netHttp.HttpReq;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.OnHttpCallBack;
import com.nostra13.universalimageloader.utils.L;
import com.qyai.baidumap.postion.bean.LoctionBean;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/maplib/GMapActivity")
public class GMapActivity extends BaseHeadActivity implements LocationSource,
        AMapLocationListener, AMap.OnMapTouchListener {

    @BindView(R2.id.layout_adress)
    RelativeLayout layout_adress;

    @BindView(R2.id.tv_adress)
    TextView tv_adress;
    @BindView(R2.id.tv_fuz)
    TextView tv_fuz;
    @BindView(R2.id.tv_share)
    TextView tv_share;
    @BindView(R2.id.mv_foreground)
    MapView mapView;
    //初始化地图控制器对象
    AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;


    boolean useMoveToLocationWithMapMode = true;

    //自定义定位小蓝点的Marker
    Marker locationMarker;

    //坐标和经纬度转换工具
    Projection projection;
    @Autowired(name = "personId")
    public String personId;
    public LatLng myLatLng;

    @Override
    public int layoutId() {
        return R.layout.activity_g_map;
    }

    @Override
    protected void initUIData() {
        setTvTitle("位置信息");
        PermissionCheckUtils.requestPermissions(GMapActivity.this, Constants.REQUEST_CODE, Common.permissionList1); // 动态请求权限
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        LogUtil.e("map==>", personId + "");
        if (SPValueUtil.isEmpty(personId)) {
            getHttpLocation(personId);
        }

    }

    @OnClick({R2.id.tv_fuz, R2.id.tv_share})
    public void onClick(View view) {
        if (view.getId() == R.id.tv_fuz) {
            ClipboardManager cmb = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(tv_adress.getText().toString());
            UIHelper.ToastMessage(mActivity, "已复制地址");
        } else if (view.getId() == R.id.tv_share) {
            ShareUtils shareUtils = new ShareUtils();
            String url = "http://uri.amap.com/marker?position="
                    + myLatLng.longitude + ","
                    + myLatLng.latitude;
            shareUtils.shareMessage("分享", tv_adress.getText().toString(), null, url, "");

        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(false);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setOnMapTouchListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        mapView.onCreate(bundle);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        useMoveToLocationWithMapMode = true;
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
        useMoveToLocationWithMapMode = false;
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                LatLng latLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                myLatLng = latLng;
                //展示自定义定位小蓝点
                if (locationMarker == null) {
                    //首次定位
                    locationMarker = aMap.addMarker(new MarkerOptions().position(latLng)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_mypostion))
                            .anchor(1f, 1f));

                    //首次定位,选择移动到地图中心点并修改级别到15级
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                } else {

                    if (useMoveToLocationWithMapMode) {
                        //二次以后定位，使用sdk中没有的模式，让地图和小蓝点一起移动到中心点（类似导航锁车时的效果）
                        startMoveLocationAndMap(latLng);
                    } else {
                        startChangeLocation(latLng);
                    }

                }
                layout_adress.setVisibility(View.VISIBLE);
                tv_adress.setText(amapLocation.getAddress());
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    /**
     * 修改自定义定位小蓝点的位置
     *
     * @param latLng
     */
    private void startChangeLocation(LatLng latLng) {

        if (locationMarker != null) {
            LatLng curLatlng = locationMarker.getPosition();
            if (curLatlng == null || !curLatlng.equals(latLng)) {
                locationMarker.setPosition(latLng);
            }
        }
    }

    /**
     * 同时修改自定义定位小蓝点和地图的位置
     *
     * @param latLng
     */
    private void startMoveLocationAndMap(LatLng latLng) {

        //将小蓝点提取到屏幕上
        if (projection == null) {
            projection = aMap.getProjection();
        }
        if (locationMarker != null && projection != null) {
            LatLng markerLocation = locationMarker.getPosition();
            Point screenPosition = aMap.getProjection().toScreenLocation(markerLocation);
            locationMarker.setPositionByPixels(screenPosition.x, screenPosition.y);

        }

        //移动地图，移动结束后，将小蓝点放到放到地图上
        myCancelCallback.setTargetLatlng(latLng);
        //动画移动的时间，最好不要比定位间隔长，如果定位间隔2000ms 动画移动时间最好小于2000ms，可以使用1000ms
        //如果超过了，需要在myCancelCallback中进行处理被打断的情况
        aMap.animateCamera(CameraUpdateFactory.changeLatLng(latLng), 1000, myCancelCallback);

    }

    MyCancelCallback myCancelCallback = new MyCancelCallback();

    @Override
    public void onTouch(MotionEvent motionEvent) {
        Log.i("amap", "onTouch 关闭地图和小蓝点一起移动的模式");
        useMoveToLocationWithMapMode = false;
    }

    /**
     * 监控地图动画移动情况，如果结束或者被打断，都需要执行响应的操作
     */
    class MyCancelCallback implements AMap.CancelableCallback {

        LatLng targetLatlng;

        public void setTargetLatlng(LatLng latlng) {
            this.targetLatlng = latlng;
        }

        @Override
        public void onFinish() {
            if (locationMarker != null && targetLatlng != null) {
                locationMarker.setPosition(targetLatlng);
            }
        }

        @Override
        public void onCancel() {
            if (locationMarker != null && targetLatlng != null) {
                locationMarker.setPosition(targetLatlng);
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //是指定位间隔
            mLocationOption.setInterval(2000);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    public void getHttpLocation(String personId) {
        Map<String, String> map = new HashMap<>();
        HttpServiec.getInstance().getFlowbleData(100, HttpReq.getInstence().getIp() + "personHealth/getPosition/" + personId, map, new OnHttpCallBack<LoctionBean>() {
            @Override
            public void onSuccessful(int id, LoctionBean bean) {
                if (bean != null && bean.getCode().equals("000000")) {
                    if (bean.getData() != null && SPValueUtil.isEmpty(bean.getData().getLocX() + "") || SPValueUtil.isEmpty(bean.getData().getLocY() + "")) {
                        LatLng latLng = new LatLng(bean.getData().getLocY(), bean.getData().getLocX(), false);
                        myLatLng = latLng;
                        if (locationMarker == null) {
                            //首次定位
                            locationMarker = aMap.addMarker(new MarkerOptions().position(latLng)
                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_mypostion))
                                    .anchor(1f, 1f));

                            //首次定位,选择移动到地图中心点并修改级别到15级
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                            tv_adress.setText(bean.getData().getPositionAddress());
                        }
                    } else {
                        startLocationMap();
                    }
                }
            }

            @Override
            public void onFaild(int id, LoctionBean bean, String err) {
                startLocationMap();
            }
        }, LoctionBean.class);
    }

    //开启定位
    public void startLocationMap() {
        aMap.setMyLocationEnabled(true);
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //是指定位间隔
            mLocationOption.setInterval(2000);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        } else {
            mlocationClient.startLocation();
        }
    }
}