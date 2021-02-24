package com.qyai.baidumap.postion;


import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.SPValueUtil;
import com.qyai.baidumap.R;
import com.qyai.baidumap.R2;
import com.qyai.baidumap.postion.bean.EnclosureInfo;



import butterknife.BindView;
import butterknife.OnClick;
@Route(path = "/maplib/PostionActivity")
public class PostionActivity extends BaseHeadActivity implements LocationSource,
        AMapLocationListener {

    @BindView(R2.id.mapView)
    MapView mapView;
    @BindView(R2.id.iv_postion)
    ImageView iv_postion;
    @BindView(R2.id.layout_enclosure)
    View layout_enclosure;
    boolean isEdit = false;
    boolean isShowing = false;
    @BindView(R2.id.et_enclosuer)
    EditText et_enclosuer;
    @BindView(R2.id.tv_adress)
    TextView tv_adress;
    @BindView(R2.id.tv_seekBar_value)
    TextView tv_seekBar_value;
    @BindView(R2.id.seekBar)
    SeekBar seekBar;
    @BindView(R2.id.layout_Search)
    LinearLayout layout_Search;
    @BindView(R2.id.ed_search)
    EditText ed_search;
    @BindView(R2.id.ryl_search)
    RecyclerView ryl_search;
    AddEnclosureAdapter addEnclosureAdapter;
    int number = 0;
    //初始化地图控制器对象
    AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);

    EnclosureInfo info;
    @Override
    public int layoutId() {
        return R.layout.activity_postion;
    }



    @Override
    protected void initUIData() {
        initViews(getBundle());
        setTvTitle(getIntent().getStringExtra("title")==null?"实时位置":getIntent().getStringExtra("title"));
        setTvRightMsg("编辑");
        info= (EnclosureInfo) getIntent().getSerializableExtra("bean");
        if(info!=null){
            isEdit=true;
            isShowing = true;
            layout_enclosure.setVisibility(View.VISIBLE);
            et_enclosuer.setText(info.getName());
            tv_adress.setText(info.getAdress());
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        ryl_search.setLayoutManager(layoutManager);
        addEnclosureAdapter = new AddEnclosureAdapter(mActivity);
        addEnclosureAdapter.setData(EnclosureInfo.getEnclosureInfoList());
        ryl_search.setAdapter(addEnclosureAdapter);
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (SPValueUtil.isEmpty(s.toString())) {
                    ryl_search.setVisibility(View.VISIBLE);
                } else {
                    ryl_search.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //改变数值
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                number = progress;
//                tv_seekBar_value.setText(progress + "");
                if(number==10){
                    tv_seekBar_value.setText(10 + "米");
                }else if(number==20){
                    tv_seekBar_value.setText(50 + "米");
                }else if(number==30){
                    tv_seekBar_value.setText(100 + "米");
                }else if(number==40){
                    tv_seekBar_value.setText(200 + "米");
                }else if(number==50){
                    tv_seekBar_value.setText(500 + "米");
                }else if(number==60){
                    tv_seekBar_value.setText(800 + "米");
                }else if(number==70){
                    tv_seekBar_value.setText(1000 + "米");
                }else if(number==80){
                    tv_seekBar_value.setText(1500 + "米");
                }else if(number==90){
                    tv_seekBar_value.setText(1800 + "米");
                }else if(number==100){
                    tv_seekBar_value.setText(2000 + "米");
                }
                //如果需要设置最小值，如下 (注：上面设置最大值现对应要减10：seekBar.setMax(70-10);)
//                progress += 10;
//                textView.setText(progress + "");
            }

            //开始拖动
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            //停止拖动
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    private void initViews(Bundle bundle) {
        mapView.onCreate(bundle);
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }

    }
    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setZoomControlsEnabled(false);//隐藏缩放按钮
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        setupLocationStyle();
    }

    private void setupLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.mipmap.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
    }
    @Override
    public void setOnClickTvRight() {
        super.setOnClickTvRight();
        if (!isEdit) {
            setTvRightMsg("保存");
            layout_Search.setVisibility(View.VISIBLE);
            et_enclosuer.setEnabled(true);
            et_enclosuer.setHint("请输入围栏名称");
            et_enclosuer.setText("");
            isEdit = true;
        } else {
            if (ryl_search.getVisibility() == View.VISIBLE) {
                tv_adress.setText(addEnclosureAdapter.getDataSource().get(0).getAdress());
                ryl_search.setVisibility(View.GONE);
                ed_search.setText("");
            } else {
                setTvRightMsg("编辑");
                layout_Search.setVisibility(View.GONE);
                isEdit = false;
                layout_enclosure.setVisibility(View.GONE);
                hideTvRight(View.GONE);
                isShowing = false;
            }
        }
    }

    @OnClick({R2.id.iv_postion})
    public void onClick(View view) {
        if (view.getId() == R.id.iv_postion) {
            if (!isShowing) {
                layout_enclosure.setVisibility(View.VISIBLE);
                et_enclosuer.setEnabled(false);
                hideTvRight(View.VISIBLE);
                isShowing = true;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        mapView = null;
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
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
}