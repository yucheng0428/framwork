package com.qyai.watch_app.message;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.common.base.BaseActivity;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.netHttp.HttpReq;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.OnHttpCallBack;
import com.lib.common.recyclerView.RecyclerItemCallback;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.home2.HomeActivity2;
import com.qyai.watch_app.message.bean.AlarmInfo;
import com.qyai.watch_app.message.bean.AlarmPushBean;
import com.qyai.watch_app.message.bean.AlarmResult;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 消息告警界面
 */
public class AlarmActivity extends BaseActivity {
    @BindView(R2.id.ivLeft)
    ImageView ivLeft;
    @BindView(R2.id.tv_do)
    TextView tv_do;
    @BindView(R2.id.tv_no_do)
    TextView tv_no_do;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    AlarmAdapter alarmAdapter;
    int itemType = 1;

    @Override
    protected int layoutId() {
        return R.layout.activity_alarm;
    }

    @Override
    protected void initUIData(Bundle bundle) {
        setScreenModel(3);
        setTranslucentStatusColor(mActivity.getResources().getColor(R.color.white));
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        alarmAdapter = new AlarmAdapter(mActivity);
        recyclerView.setAdapter(alarmAdapter);
        alarmAdapter.setRecItemClick(new RecyclerItemCallback<AlarmPushBean, AlarmAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, AlarmPushBean model, int tag, AlarmAdapter.ViewHolder holder) {
                //1是点击整item;
                Intent intent = new Intent(mActivity, AlarmDetailActivity.class);
                intent.putExtra("info", model);
                super.onItemClick(position, model, tag, holder);
                switch (tag) {
                    //处理结果，1已处理，0未处理, 2忽略
                    case 1:
                        if (model.getDealStatus() == 1) {
                            intent.putExtra("type", 1);
                            startActivityForResult(intent, Constants.REQUEST_CODE);
                        } else {
                            //2是点击处理按钮;
                            intent.putExtra("type", 2);
                            startActivityForResult(intent, Constants.REQUEST_CODE);
                        }
                        break;
                    case 2:
                        //2是点击处理按钮;
                        intent.putExtra("type", 2);
                        startActivityForResult(intent, Constants.REQUEST_CODE);
                        break;
                }
            }
        });
        changeItem();
    }


    public void changeItem() {
        //处理结果，1已处理，0未处理, 2忽略
        if (itemType == 1) {
            tv_do.setTextColor(getResources().getColor(R.color.color_248bfe));
            tv_do.setBackgroundResource(R.drawable.down_line);
            tv_no_do.setBackgroundResource(0);
            tv_no_do.setTextColor(getResources().getColor(R.color.color_999999));
            alarmAdapter.clearData();
            getAlarmList(1);
            itemType = 2;
        } else {
            alarmAdapter.clearData();
            getAlarmList(0);
            itemType = 1;
            tv_no_do.setTextColor(getResources().getColor(R.color.color_248bfe));
            tv_no_do.setBackgroundResource(R.drawable.down_line);
            tv_do.setBackgroundResource(0);
            tv_do.setTextColor(getResources().getColor(R.color.color_999999));
        }
    }

    @OnClick({R2.id.ivLeft, R2.id.tv_no_do, R2.id.tv_do})
    public void onClick(View view) {
        if (view.getId() == R.id.ivLeft) {
            onBackPressed();
        } else if (view.getId() == R.id.tv_no_do) {
            changeItem();
        } else if (view.getId() == R.id.tv_do) {
            changeItem();
        }
    }

    //查询告警 0=未处理 1=已处理
    public void getAlarmList(int type) {
        HashMap req = new HashMap();
        req.put("dealStatus", type + "");
        req.put("page", "-1");

        //查询告警
        HttpServiec.getInstance().postFlowableData(100, HttpReq.getInstence().getIp() + "alarm/queryAlarm", req, new OnHttpCallBack<AlarmResult>() {
            @Override
            public void onSuccessful(int id, AlarmResult result) {
                if (result != null && result.getData().size() > 0) {
                    alarmAdapter.setData(result.getData());
                }
            }

            @Override
            public void onFaild(int id, AlarmResult o, String err) {
                UIHelper.ToastMessage(AlarmActivity.this, err);
            }
        }, AlarmResult.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REQUEST_CODE) {
            changeItem();
        }
    }
}