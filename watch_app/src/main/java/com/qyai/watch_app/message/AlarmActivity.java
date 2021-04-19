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

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.lib.common.base.BaseActivity;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.LogUtil;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.netHttp.HttpReq;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.OnHttpCallBack;
import com.lib.common.recyclerView.RecyclerItemCallback;
import com.lib.common.widgt.RefreshAllLayout;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.message.bean.AlarmPushBean;
import com.qyai.watch_app.message.bean.AlarmResult;
import com.qyai.watch_app.utils.OnlyUserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    @BindView(R2.id.swipeRefreshLayout)
    RefreshAllLayout swipeRefreshLayout;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    AlarmAdapter alarmAdapter;
    int itemType = 2;
    int type=0;
    String classId = "";

    @Override
    protected int layoutId() {
        return R.layout.activity_alarm;
    }

    @Override
    protected void initUIData(Bundle bundle) {
        setScreenModel(3);
        classId=getIntent().getStringExtra("classId");

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
                            startActivityForResult(intent, Common.REQUEST_CODE);
                        } else {
                            //2是点击处理按钮;
                            intent.putExtra("type", 2);
                            startActivityForResult(intent, Common.REQUEST_CODE);
                        }
                        break;
                    case 2:
                        //2是点击处理按钮;
                        intent.putExtra("type", 2);
                        startActivityForResult(intent, Common.REQUEST_CODE);
                        break;
                    case 3:
                        ARouter.getInstance().build("/maplib/GMapActivity").
                                withString("model", JSON.toJSONString(model)).
                                navigation();
                        break;
                }
            }
        });
        initRefreshLayout();
        changeItem();
    }

    private void initRefreshLayout() {
        swipeRefreshLayout.setCanRefresh(false);
        swipeRefreshLayout.setCanLoadMore(false);
        swipeRefreshLayout.setLoadListener(new RefreshAllLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                getAlarmList(type);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new RefreshAllLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAlarmList(type);
            }
        });
    }

    public void changeItem() {
        //处理结果，1已处理，0未处理, 2忽略
        if (itemType == 1) {
            tv_do.setTextColor(getResources().getColor(R.color.color_248bfe));
            tv_do.setBackgroundResource(R.drawable.down_line);
            tv_no_do.setBackgroundResource(0);
            tv_no_do.setTextColor(getResources().getColor(R.color.color_999999));
            alarmAdapter.clearData();
            type=1;
            itemType = 2;
        } else {
            alarmAdapter.clearData();
            itemType = 1;
            type=0;
            tv_no_do.setTextColor(getResources().getColor(R.color.color_248bfe));
            tv_no_do.setBackgroundResource(R.drawable.down_line);
            tv_do.setBackgroundResource(0);
            tv_do.setTextColor(getResources().getColor(R.color.color_999999));
        }
        getAlarmList(type);
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
        if(!SPValueUtil.isEmpty(classId)){
            return;
        }
        HashMap req = new HashMap();
        if(type==0){
            req.put("dealStatus", type + "");
        }else {
            List<String> count = new ArrayList<>();
            count.add("1");
            count.add("2");
            req.put("dealStatusList", count);
        }
        req.put("type","-1");
        req.put("page", "-1");
        List<String> count = new ArrayList<>();
        count.add(classId + "");
        req.put("classId", count);
        //查询告警
        HttpServiec.getInstance().postFlowableData(100, HttpReq.getInstence().getIp() + "alarm/queryAlarm", req, new OnHttpCallBack<AlarmResult>() {
            @Override
            public void onSuccessful(int id, AlarmResult result) {
                alarmAdapter.clearData();
                if (result != null && result.getData().size() > 0&&result.getCode().equals("000000")) {
                    alarmAdapter.setData(result.getData());
                }else if(result != null &&result.getCode().equals(Common.CATCH_CODE)){
                    OnlyUserUtils.catchOut(mActivity,result.getMsg());
                }
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setLoading(false);
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
        if (resultCode == Common.REQUEST_CODE) {
            changeItem();
        }
    }
}