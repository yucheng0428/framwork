package com.qyai.watch_app.home2;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.navigation.NavigationView;
import com.lib.common.base.BaseActivity;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.recyclerView.RecyclerItemCallback;
import com.lib.common.widgt.MyDrawerLayout;
import com.lib.common.widgt.RoundImageView;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.message.AlarmActivity;
import com.qyai.watch_app.message.AlarmAdapter;
import com.qyai.watch_app.message.MessageDetailAct;
import com.qyai.watch_app.message.bean.AlarmInfo;
import com.qyai.watch_app.xiaqu.XiaQuActivity;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/watch/HomeActivity2")
public class HomeActivity2 extends BaseActivity {
    @BindView(R2.id.drawer_layout)
    MyDrawerLayout drawer_layout;
    @BindView(R2.id.nav_view)
    NavigationView nav_view;
    @BindView(R2.id.tvTitle)
    TextView tvTitle;
    @BindView(R2.id.ivLeft)
    ImageView ivLeft;
    @BindView(R2.id.ivRight)
    ImageView ivRight;


    @BindView(R2.id.iv_head)
    RoundImageView iv_head;
    @BindView(R2.id.tv_name)
    TextView tv_name;
    @BindView(R2.id.tv_unbind)
    TextView tv_unbind;
    @BindView(R2.id.tv_about)
    TextView tv_about;
    @BindView(R2.id.tv_out_login)
    TextView tv_out_login;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    AlarmAdapter alarmAdapter;

    @BindView(R2.id.tv_xqsh)
    TextView tv_xqsh;

    @Override
    protected int layoutId() {
        return R.layout.activity_home_view2;
    }

    @Override
    protected void initUIData(Bundle bundle) {
        setScreenModel(3);
        setTranslucentStatusColor(mActivity.getResources().getColor(R.color.white));
        tvTitle.setText("首页");
        ivRight.setVisibility(View.VISIBLE);
        ivLeft.setImageResource(R.mipmap.cehua);
        ivRight.setImageResource(R.mipmap.message);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        tv_unbind.setVisibility(View.GONE);
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
                        Intent intent = new Intent(mActivity, MessageDetailAct.class);
                        startActivity(intent);
                        break;
                    case 2:
                        //2是点击处理按钮;
                        UIHelper.ToastMessage(mActivity, "点击处理按钮");
                        break;
                }
            }
        });
    }


    @OnClick({
            R2.id.ivLeft, R2.id.tv_xqsh,
            R2.id.ivRight, R2.id.tv_about, R2.id.tv_out_login})
    public void onClick(View view) {
        Intent intent = new Intent();
        if (view.getId() == R.id.ivLeft) {
            if (drawer_layout.isOpen()) {
                drawer_layout.closeDrawers();
            } else {
                drawer_layout.openDrawer(GravityCompat.START);
            }
        } else if (view.getId() == R.id.ivRight) {
            intent.setClass(mActivity, AlarmActivity.class);
            startActivity(intent);
            UIHelper.ToastMessage(mActivity, "消息");
        } else if (view.getId() == R.id.tv_xqsh) {
            intent.setClass(mActivity, XiaQuActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.tv_about) {
            UIHelper.ToastMessage(mActivity, "关于我们");
        } else if (view.getId() == R.id.tv_out_login) {
            UIHelper.ToastMessage(mActivity, "退出登录");
        }

    }
}