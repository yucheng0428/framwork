package com.qyai.main.bracelet;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.baseUtils.permssion.PermissionCheckUtils;
import com.lib.common.recyclerView.RecyclerItemCallback;
import com.qyai.main.R;
import com.qyai.main.R2;
import com.qyai.main.bracelet.bean.MessageBean;
import com.qyai.main.bracelet.bean.SyncBloodBean;
import com.qyai.main.bracelet.bean.SyncHeartBean;
import com.qyai.main.bracelet.bean.SyncHistiryBean;
import com.yucheng.ycbtsdk.YCBTClient;

import butterknife.BindView;


public class MessageAct extends BaseHeadActivity implements BraceletReceiver.ReceiverCallBack {
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    MessageAdapter adapter;
    Intent serviceIntent;
    SyncHistiryBean bean;
    public BraceletReceiver myReceiver;


    @Override
    public int layoutId() {
        return R.layout.activity_sech_view;
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        myReceiver = new BraceletReceiver();
        myReceiver.setCallBack(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BlueToothService.CHANNEL_ID_STRING);
        //注册广播
       mActivity.registerReceiver(myReceiver, intentFilter);
    }

    @Override
    protected void setIvLeftOnclick() {
        super.setIvLeftOnclick();
        YCBTClient.disconnectBle();
    }

    @Override
    public void setOnClickTvRight() {
        super.setOnClickTvRight();
        ARouter.getInstance().build("/beacon/HtmlMapAct")
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
        adapter.getDataSource().get(cheackString("断开设备")).setTypeValue(YCBTClient.connectState()==3?"未连接":"已连接");
        adapter.notifyDataSetChanged();
    }


    private void initService() {
        PermissionCheckUtils.requestPermissions(mActivity, Constants.REQUEST_CODE, Constants.permissionList); // 动态请求权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mActivity.startForegroundService(serviceIntent);
        } else {
            mActivity.startService(serviceIntent);
        }
    }

    private void stopService() {
        mActivity.stopService(serviceIntent);
    }

    public int cheackString(String type) {
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
        mActivity.unregisterReceiver(myReceiver);
    }


    @Override
    public void onSuccessAll(String all) {
        SyncHistiryBean bean = JSON.parseObject(all, SyncHistiryBean.class);
        if (bean != null && bean.getData().size() > 0) {
            SyncHistiryBean.DataBean dataBean = bean.getData().get(bean.getData().size() - 1);
            changeModle(dataBean);
        }
        if(bean!=null&&bean.getData().size()>10){
            BraceletApi.getInstance().delectSyncHistoryData(0x0544);
        }
    }

    @Override
    public void onSuccessHeart(String heartValue) {
        SyncHeartBean heartBean = JSON.parseObject(heartValue, SyncHeartBean.class);
        if (heartBean != null && heartBean.getData().size() > 0) {
            SyncHeartBean.DataBean hBean = heartBean.getData().get(heartBean.getData().size() - 1);
            int indx=cheackString("心率");
            adapter.getDataSource().get(indx).setTypeValue(hBean.getHeartValue()+"");
            adapter.getDataSource().get(indx).setTime(hBean.getHeartStartTime()+"");
        }
        if(heartBean!=null&&heartBean.getData().size()>10){
            BraceletApi.getInstance().delectSyncHistoryData(0x0542);
        }
    }

    @Override
    public void onSuccessBlood(String blood) {
        SyncBloodBean bloodBean = JSON.parseObject(blood, SyncBloodBean.class);
        if (bloodBean != null && bloodBean.getData().size() > 0) {
            SyncBloodBean.DataBean bBean = bloodBean.getData().get(bloodBean.getData().size() - 1);
            int indx=cheackString("血压");
            adapter.getDataSource().get(indx).setTypeValue(bBean.getBloodDBP()+"/"+bBean.getBloodSBP());
            adapter.getDataSource().get(indx).setTime(bBean.getBloodStartTime()+"");
        }
        if(bloodBean!=null&&bloodBean.getData().size()>10){
            BraceletApi.getInstance().delectSyncHistoryData(0x0543);
        }
    }
}
