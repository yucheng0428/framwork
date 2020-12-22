package com.qyai.watch_app.message;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.common.base.BaseActivity;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.recyclerView.RecyclerItemCallback;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.message.bean.AlarmInfo;

import butterknife.BindView;
import butterknife.OnClick;

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
    int itemType=1;

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
        alarmAdapter.setData(AlarmInfo.getContactsInfoList());
        alarmAdapter.setRecItemClick(new RecyclerItemCallback<AlarmInfo, AlarmAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, AlarmInfo model, int tag, AlarmAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (tag) {
                    case 1:
                        //1是点击整item;
                        Intent intent = new Intent(mActivity, AlarmDetailActivity.class);
                        if(itemType==2){
                            intent.putExtra("type",1);
                        }else {
                            intent.putExtra("type",2);
                        }
                        intent.putExtra("info",model);
                        startActivity(intent);
                        break;
                    case 2:
                        //2是点击处理按钮;
                        UIHelper.ToastMessage(mActivity, "点击处理按钮");
                        break;
                }
            }
        });
        changeItem();
    }


    public void changeItem(){
        if(itemType==1){
            tv_do.setTextColor(getResources().getColor(R.color.color_248bfe));
            tv_do.setBackgroundResource(R.drawable.down_line);
            tv_no_do.setBackgroundResource(0);
            tv_no_do.setTextColor(getResources().getColor(R.color.color_999999));
            alarmAdapter.setType(itemType);
            alarmAdapter.clearData();
            alarmAdapter.addData(AlarmInfo.getContactsInfoList());
            itemType=2;
        }else {
            alarmAdapter.setType(itemType);
            alarmAdapter.clearData();
            alarmAdapter.addData(AlarmInfo.getContactsInfoList());
            itemType=1;
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
          UIHelper.ToastMessage(mActivity,"未处理");
        } else if (view.getId() == R.id.tv_do) {
            changeItem();
            UIHelper.ToastMessage(mActivity,"已处理");
        }
    }
}