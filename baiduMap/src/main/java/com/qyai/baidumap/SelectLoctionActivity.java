package com.qyai.baidumap;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.qyai.baidumap.postion.bean.PoiOverlay;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectLoctionActivity extends BaseHeadActivity implements AMapLocationListener,
        GeocodeSearch.OnGeocodeSearchListener,
        AMap.OnCameraChangeListener,
        LocationSource,
        AMap.OnMapTouchListener,
        PoiSearch.OnPoiSearchListener,
        INaviInfoCallback {
    @BindView(R2.id.map)
    MapView mMapView;
    @BindView(R2.id.tv_adress)
    TextView tv_adress;
    @BindView(R2.id.layout_adress)
    RelativeLayout layout_adress;
    private AMap aMap = null;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    boolean useMoveToLocationWithMapMode = true;
    public UiSettings mUiSettings;//定义一个UiSettings对象
    LatLng mLatlng;//当前位置
    LatLng startLatLng;
    LatLng endLatLng;
    private GeocodeSearch geocoderSearch;
    private Marker startMarker;
    private Marker endMarker;

    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiResult poiResult; // poi返回的结果


    /******选择导航位置*******/
    @BindView(R2.id.layout_nav)
    RelativeLayout layout_nav;
    @BindView(R2.id.tv_start)
    TextView tv_start;
    @BindView(R2.id.tv_end)
    TextView tv_end;
    @BindView(R2.id.tv_go)
    TextView tv_go;
    @BindView(R2.id.tv_ride)
    TextView tv_ride;
    @BindView(R2.id.tv_walk)
    TextView tv_walk;
    int wichType = 0;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        mMapView.onCreate(bundle);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_select_loction;
    }

    @Override
    protected void initUIData() {
        setTvTitle("地图选点");
        if (aMap == null) {
            aMap = mMapView.getMap();
            setUpMap();
        }
        startLocationMap();
    }


    @OnClick({R2.id.tv_start, R2.id.tv_end, R2.id.tv_go,R2.id.tv_ride,R2.id.tv_walk})
    public void onClick(View view) {
        if (view.getId() == R.id.tv_start) {
            wichType = 0;
            setStartMarker(startLatLng);
        } else if (view.getId() == R.id.tv_end) {
            wichType = 1;
            setEndMarker(endLatLng);
        } else if (view.getId() == R.id.tv_go) {
            if (!SPValueUtil.isEmpty(tv_end.getText().toString())) {
                UIHelper.ToastMessage(mActivity, "请选择目的地");
                return;
            }
            startNav(startLatLng,  endLatLng,1);
        }else if(view.getId()==R.id.tv_ride){
            startNav(startLatLng,  endLatLng,3);
        }else if(view.getId()==R.id.tv_walk){
            startNav(startLatLng,  endLatLng,2);
        }
    }

    /**
     * 启动路线导航
     */
    public void startNav(LatLng startLatLng, LatLng endLatng, int type) {
        Intent intent=new Intent();
        // 起点信息
        NaviLatLng start = new NaviLatLng(startLatLng.latitude, startLatLng.longitude);
        // 终点信息
        NaviLatLng end = new NaviLatLng(endLatng.latitude, endLatng.longitude);
        intent.putExtra("start",start);
        intent.putExtra("end",end);
       if(type==1){
           intent.setClass(mActivity,DriverListActivity.class);
       }else if(type==2){
           intent.setClass(mActivity,WalkRouteCalculateActivity.class);
       }else if(type==3){
           intent.setClass(mActivity,RideRouteCalculateActivity.class);
       }
       startActivity(intent);
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setOnMapTouchListener(this);//地图触摸监听
        aMap.setOnCameraChangeListener(this);//添加地图移动监听
        mUiSettings = aMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(false);//隐藏 放大缩小
    }


    private void setStartMarker(LatLng target) {
        if (startMarker != null) startMarker.remove();
        if (target == null) {
            startLatLng = mLatlng;
        } else {
            startLatLng = target;
        }
        setMapCenter(startLatLng);
        startMarker = aMap.addMarker(new MarkerOptions().position(startLatLng).
                title("起始位置").snippet("").
                icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_mypostion)).
                anchor(1f, 1f));
        startMarker.showInfoWindow();
    }

    private void setEndMarker(LatLng target) {
        if (endMarker != null) endMarker.remove();
        if (target == null) {
            endLatLng = mLatlng;
        } else {
            endLatLng = target;
        }
        setMapCenter(endLatLng);
        endMarker = aMap.addMarker(new MarkerOptions().position(endLatLng).
                title("目的地").snippet("").
                icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_end_maker)).
                anchor(1f, 1f));
        endMarker.showInfoWindow();
    }


    private void setMapCenter(LatLng latLng) {
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition(latLng, 15, 0, 0)), 300, null); //设置地图中心点
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }


    @Override
    public void onTouch(MotionEvent motionEvent) {
        Log.i("amap", "onTouch 关闭地图和小蓝点一起移动的模式");
        useMoveToLocationWithMapMode = false;
    }

    /**
     * 定位代码起始
     * <p>
     * 开启定位
     */
    public void startLocationMap() {
        mUiSettings.setMyLocationButtonEnabled(true);
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

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
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

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mLatlng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                setMapCenter(mLatlng);
                setStartMarker(mLatlng);
                layout_adress.setVisibility(View.VISIBLE);
                tv_adress.setText(amapLocation.getAddress());
                mlocationClient.stopLocation();
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }


    /**
     * 地图移动 定点获取位置监听
     *
     * @param cameraPosition
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        if (wichType == 0) {
            setStartMarker(cameraPosition.target);
        } else {
            setEndMarker(cameraPosition.target);
        }
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        if (wichType == 0) {
            setStartMarker(cameraPosition.target);
        } else {
            setEndMarker(cameraPosition.target);
        }
        getGeocodeSearch(cameraPosition.target);
    }

    //逆地理编码获取当前位置信息
    private void getGeocodeSearch(LatLng targe) {
        if (geocoderSearch == null) geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(targe.latitude, targe.longitude), 1000, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i != 1000) return;
        if (wichType == 0) {
            tv_start.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress());
        } else {
            tv_end.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress());
        }
        tv_adress.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress());
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }


    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(String keyWord, String type, String region) {
        currentPage = 0;
        query = new PoiSearch.Query(keyWord, type == null ? "" : type, region == null ? "" : region);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    if (poiItems != null && poiItems.size() > 0) {
//                        aMap.clear();// 清理之前的图标
                        PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
                        poiOverlay.removeFromMap();
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();
                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                    } else {
                        UIHelper.ToastMessage(mActivity, "无结果");
                    }
                }
            } else {
                UIHelper.ToastMessage(mActivity, "无结果");
            }
        } else {
            UIHelper.ToastMessage(mActivity, rCode);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
                    + cities.get(i).getCityCode() + "城市编码:"
                    + cities.get(i).getAdCode() + "\n";
        }
        UIHelper.ToastMessage(mActivity, infomation);

    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onArriveDestination(boolean b) {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onStopSpeaking() {

    }

    @Override
    public void onReCalculateRoute(int i) {

    }

    @Override
    public void onExitPage(int i) {

    }

    @Override
    public void onStrategyChanged(int i) {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onMapTypeChanged(int i) {

    }

    @Override
    public void onNaviDirectionChanged(int i) {

    }

    @Override
    public void onDayAndNightModeChanged(int i) {

    }

    @Override
    public void onBroadcastModeChanged(int i) {

    }

    @Override
    public void onScaleAutoChanged(boolean b) {

    }

    @Override
    public View getCustomMiddleView() {
        return null;
    }

    @Override
    public View getCustomNaviView() {
        return null;
    }

    @Override
    public View getCustomNaviBottomView() {
        return null;
    }
}