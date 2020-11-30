package com.qyai.bracelet_lib;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.baseUtils.permssion.PermissionCheckUtils;
import com.lib.common.recyclerView.ItemAdapter;
import com.lib.common.recyclerView.RecyclerItemCallback;
import com.qyai.bracelet_lib.bean.DeviceBean;
import com.yucheng.ycbtsdk.YCBTClient;

import butterknife.BindView;

@Route(path = "/bracelet/SechAct")
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
        Common.openGPSSEtting(mActivity);
        PermissionCheckUtils.requestPermissions(mActivity, Constants.REQUEST_CODE, Common.permissionList); // 动态请求权限
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
        adapter.clearData();
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
