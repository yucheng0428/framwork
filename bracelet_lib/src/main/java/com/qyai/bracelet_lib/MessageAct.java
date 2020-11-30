package com.qyai.bracelet_lib;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.baseUtils.permssion.PermissionCheckUtils;
import com.lib.common.recyclerView.RecyclerItemCallback;
import com.qyai.beaconlib.R;
import com.qyai.beaconlib.R2;
import com.qyai.beaconlib.location.SensorManageService;
import com.qyai.bracelet_lib.bean.MessageBean;
import com.qyai.bracelet_lib.bean.SyncBloodBean;
import com.qyai.bracelet_lib.bean.SyncHeartBean;
import com.qyai.bracelet_lib.bean.SyncHistiryBean;
import com.yucheng.ycbtsdk.YCBTClient;

import butterknife.BindView;


public class MessageAct extends BaseHeadActivity implements BraceletReceiver.ReceiverCallBack {
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
   static MessageAdapter adapter;
    Intent serviceIntent;
    @BindView(R2.id.sw_gps)
    Switch  sw_gps;
    @BindView(R2.id.sw_postion)
    Switch  sw_postion;
    @BindView(R2.id.sw_bracelet)
    Switch  sw_bracelet;
    public BraceletReceiver myReceiver;

    @Override
    public int layoutId() {
        return R.layout.activity_message_view;
    }

    @Override
    protected void initUIData() {
        setTvTitle("设备详情");
        hideTvRight(View.VISIBLE);
        setTvRightMsg("看地图");
        hideIvLeft(View.VISIBLE);
        serviceIntent = new Intent(this, BlueToothService.class);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new MessageAdapter(mActivity);
        recyclerView.setAdapter(adapter);
        adapter.setData(MessageBean.getMessgeList());
        adapter.setRecItemClick(new RecyclerItemCallback<MessageBean, MessageAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, MessageBean model, int tag, MessageAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                getDeviceMessage(model.getTyepName(), position, model);
                adapter.notifyDataSetChanged();
            }
        });
        swSetting();
    }


    public void swSetting(){
        sw_gps.setChecked(false);
        sw_bracelet.setChecked(true);
        sw_postion.setChecked(true);
        initService();
        SensorManageService.initService(mActivity);
        sw_gps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    GpsLactionUtils.getInstance(mActivity).startGps();
                }else {
                    GpsLactionUtils.getInstance(mActivity).stopGps();
                }
            }
        });
        sw_postion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SensorManageService.initService(mActivity);
                }else {
                    SensorManageService.stopService(mActivity);
                }
            }
        });
        sw_bracelet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    initService();
                }else {
                    stopService();
                }
            }
        });
    }

    @Override
    protected void setIvLeftOnclick() {
        super.setIvLeftOnclick();
        YCBTClient.disconnectBle();
    }

    @Override
    public void setOnClickTvRight() {
        super.setOnClickTvRight();
        ARouter.getInstance().build("/beacon/BeaconMessageAct")
                .navigation();
    }

    /**
     * 设备各种状态
     */
    public void getDeviceMessage(String type, int pos, MessageBean bean) {
        switch (type) {
            case "蓝牙连接状态":
                adapter.getDataSource().get(pos).setTypeValue(YCBTClient.connectState() + "");
                break;
            case "数据同步":
                if (bean.getTypeValue().equals("未开启")) {
                    bean.setTypeValue("已开启");
                    initService();
                }
                break;
            case "血压":
            case "体温":
            case "血氧":
            case "心率":
            case "呼吸率":
                UIHelper.ToastMessage(mActivity, bean.getTyepName() + bean.getTypeValue());
                break;
            case "断开设备":
                stopService();
                for (MessageBean messageBean : adapter.getDataSource()) {
                    if (messageBean.getTyepName().equals("数据同步")) {
                        messageBean.setTypeValue("未开启");
                    }
                }
                break;

        }
        adapter.getDataSource().get(cheackString("断开设备")).setTypeValue(YCBTClient.connectState() == 3 ? "未连接" : "已连接");
        adapter.notifyDataSetChanged();
    }


    public static void reshState(){
        adapter.getDataSource().get(cheackString("断开设备")).setTypeValue(YCBTClient.connectState() == 3 ? "未连接" : "已连接");
        adapter.notifyDataSetChanged();
    }
    private void initService() {
        Common.openGPSSEtting(mActivity);
        PermissionCheckUtils.requestPermissions(mActivity, Constants.REQUEST_CODE, Constants.permissionList); // 动态请求权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mActivity.startForegroundService(serviceIntent);
        } else {
            mActivity.startService(serviceIntent);
        }
        myReceiver = new BraceletReceiver();
        myReceiver.setCallBack(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BlueToothService.CHANNEL_ID_STRING);
        //注册广播
        mActivity.registerReceiver(myReceiver, intentFilter);
    }

    private void stopService() {
        mActivity.stopService(serviceIntent);
        SensorManageService.stopService(mActivity);
        try {
            mActivity.unregisterReceiver(myReceiver);
        }catch (IllegalArgumentException e){

        }
    }

    public static int cheackString(String type) {
        int pos = 0;
        for (int i = 0; i < adapter.getDataSource().size(); i++) {
            if (adapter.getDataSource().get(i).getTyepName().equals(type)) {
                pos = i;
            }
        }
        return pos;
    }

    /**
     * 模型数据转换
     *
     * @param bean
     */
    public void changeModle(SyncHistiryBean.DataBean bean) {
        for (MessageBean messageBean : adapter.getDataSource()) {
            switch (messageBean.getTyepName()) {
                case "血压":
                    messageBean.setTypeValue(bean.getDBPValue() + "/" + bean.getSBPValue());
                    messageBean.setTime(bean.getStartTime() + "");
                    break;
                case "体温":
                    messageBean.setTypeValue(bean.getTempIntValue() + "");
                    messageBean.setTime(bean.getStartTime() + "");
                    break;
                case "血氧":
                    messageBean.setTypeValue(bean.getOOValue() + "");
                    messageBean.setTime(bean.getStartTime() + "");
                    break;
                case "心率":
                    messageBean.setTypeValue(bean.getHeartValue() + "");
                    messageBean.setTime(bean.getStartTime() + "");
                    break;
                case "呼吸率":
                    messageBean.setTypeValue(bean.getRespiratoryRateValue() + "");
                    messageBean.setTime(bean.getStartTime() + "");
                    break;
            }
            adapter.notifyDataSetChanged();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService();
        YCBTClient.disconnectBle();
    }


    @Override
    public void onSuccessAll(String all) {
        SyncHistiryBean bean = JSON.parseObject(all, SyncHistiryBean.class);
        if (bean != null && bean.getData().size() > 0) {
            SyncHistiryBean.DataBean dataBean = bean.getData().get(bean.getData().size() - 1);
            changeModle(dataBean);
        }
        if (bean != null && bean.getData().size() > 10) {
            BraceletApi.getInstance().delectSyncHistoryData(0x0544);
        }
    }

    @Override
    public void onSuccessHeart(String heartValue) {
        SyncHeartBean heartBean = JSON.parseObject(heartValue, SyncHeartBean.class);
        if (heartBean != null && heartBean.getData().size() > 0) {
            SyncHeartBean.DataBean hBean = heartBean.getData().get(heartBean.getData().size() - 1);
            int indx = cheackString("心率");
            adapter.getDataSource().get(indx).setTypeValue(hBean.getHeartValue() + "");
            adapter.getDataSource().get(indx).setTime(hBean.getHeartStartTime() + "");
        }
        if (heartBean != null && heartBean.getData().size() > 10) {
            BraceletApi.getInstance().delectSyncHistoryData(0x0542);
        }
    }

    @Override
    public void onSuccessBlood(String blood) {
        SyncBloodBean bloodBean = JSON.parseObject(blood, SyncBloodBean.class);
        if (bloodBean != null && bloodBean.getData().size() > 0) {
            SyncBloodBean.DataBean bBean = bloodBean.getData().get(bloodBean.getData().size() - 1);
            int indx = cheackString("血压");
            adapter.getDataSource().get(indx).setTypeValue(bBean.getBloodDBP() + "/" + bBean.getBloodSBP());
            adapter.getDataSource().get(indx).setTime(bBean.getBloodStartTime() + "");
        }
        if (bloodBean != null && bloodBean.getData().size() > 10) {
            BraceletApi.getInstance().delectSyncHistoryData(0x0543);
        }
    }

    @Override
    public void onSuccessLocation(String str) {
        if(SPValueUtil.isEmpty(str)){
            int indx = cheackString("血压");
            adapter.getDataSource().get(indx).setTypeValue(str);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
