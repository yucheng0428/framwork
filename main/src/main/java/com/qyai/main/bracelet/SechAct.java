package com.qyai.main.bracelet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.IntentKey;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.recyclerView.RecyclerItemCallback;
import com.qyai.main.Common;
import com.qyai.main.R;
import com.qyai.main.R2;
import com.qyai.main.bracelet.bean.DeviceBean;
import com.yucheng.ycbtsdk.AITools;
import com.yucheng.ycbtsdk.YCBTClient;

import butterknife.BindView;

@Route(path = "/main/bracelet")
public class SechAct extends BaseHeadActivity {
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    DeviceAdapter adapter;


    @Override
    public int layoutId() {
        return R.layout.activity_sech_view;
    }

    @Override
    protected void initUIData() {
        setTvTitle("设备列表");
        setTvRightMsg("搜索");
        hideTvRight(View.VISIBLE);
        hideIvLeft(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DeviceAdapter(mActivity);
        recyclerView.setAdapter(adapter);
        adapter.setRecItemClick(new RecyclerItemCallback<DeviceBean, ItemAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, DeviceBean model, int tag, ItemAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                BraceletApi.getInstance().connectBle(model.getDeviceMac());
                SPValueUtil.saveStringValue(mActivity, Common.BRACELET_MAC,model.getDeviceMac());
                Intent intent=new Intent(mActivity,MessageAct.class);
                startActivity(intent);
//                adapter.clearData();
            }
        });
    }

    @Override
    public void setOnClickTvRight() {
        super.setOnClickTvRight();
        YCBTClient.disconnectBle();
        if (BraceletApi.getInstance().isOpenBlueTooth(mActivity)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    UIHelper.ToastMessage(mActivity,"连接状态"+YCBTClient.connectState());
                }
            });
                BraceletApi.getInstance().search(new BraceletCallBack() {
                    @Override
                    public void onSuccess(Object o) {
                        if (o != null) {
                            DeviceBean bean = (DeviceBean) o;
                            adapter.setData(new DeviceBean[]{bean});
                        }
                    }
                });

        }
    }
}
