package com.qyai.watch_app.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.navigation.NavigationView;
import com.lib.common.base.BaseActivity;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.IntentKey;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.widgt.CircularProgressView;
import com.lib.common.widgt.MyDrawerLayout;
import com.lib.common.widgt.RoundImageView;
import com.qyai.watch_app.Common;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.bind.BindActivity;
import com.qyai.watch_app.contacts.ContactsActivity;
import com.qyai.watch_app.measure.MeasureActivity;
import com.qyai.watch_app.message.MessageActivity;
import com.qyai.watch_app.postion.PostionActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/watch/homeView")
public class HomeActivity extends BaseActivity {
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
    @BindView(R.id.tv_stat)
    TextView tv_stat;
    @BindView(R.id.tv_power)
    TextView tv_power;

    @BindView(R.id.layout_xy)
    View layout_xy;
    @BindView(R.id.tv_resh_time_xy)
    TextView tv_resh_time_xy;
    @BindView(R.id.tv_value_xy)
    TextView tv_value_xy;
    @BindView(R.id.tv_value_unit_xy)
    TextView tv_value_unit_xy;

    @BindView(R.id.layout_xl)
    View layout_xl;
    @BindView(R.id.tv_resh_time_xl)
    TextView tv_resh_time_xl;
    @BindView(R.id.tv_value_xl)
    TextView tv_value_xl;
    @BindView(R.id.tv_value_unit_xl)
    TextView tv_value_unit_xl;

    @BindView(R.id.tv_position)
    TextView tv_position;
    @BindView(R.id.tv_contacts)
    TextView tv_contacts;

    @BindView(R.id.iv_head)
    RoundImageView iv_head;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_unbind)
    TextView tv_unbind;
    @BindView(R.id.tv_about)
    TextView tv_about;
    @BindView(R.id.tv_out_login)
    TextView tv_out_login;
    public boolean isBind = true;

    @Override
    public int layoutId() {
        return R.layout.activity_main;
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
    }


    @OnClick({R.id.layout_stat, R.id.layout_xy, R.id.layout_xl,
            R.id.tv_position, R.id.tv_contacts, R.id.ivLeft,
            R.id.ivRight,R.id.tv_unbind,R.id.tv_about,R.id.tv_out_login})
    public void onClick(View view) {
        Intent intent = new Intent();
        if (view.getId() == R.id.layout_stat) {
            if (isBind) {
                UIHelper.ToastMessage(mActivity, "设备未绑定");
                intent.setClass(mActivity, BindActivity.class);
                startActivityForResult(intent, IntentKey.REQ_UPLAOD);
            }

        } else if (view.getId() == R.id.ivLeft) {
            if (drawer_layout.isOpen()) {
                drawer_layout.closeDrawers();
            } else {
                drawer_layout.openDrawer(GravityCompat.START);
            }
        } else if (view.getId() == R.id.ivRight) {
            intent.setClass(mActivity, MessageActivity.class);
            startActivity(intent);
            UIHelper.ToastMessage(mActivity, "消息");
        } else if (view.getId() == R.id.layout_xy) {
            if (isBind) {
                intent.setClass(mActivity, MeasureActivity.class);
                intent.putExtra("title", Common.MEASURE_XY);
                startActivity(intent);
            }
        } else if (view.getId() == R.id.layout_xl) {
            if (isBind) {
                intent.setClass(mActivity, MeasureActivity.class);
                intent.putExtra("title", Common.MEASURE_XL);
                startActivity(intent);
            }

        } else if (view.getId() == R.id.tv_position) {
            if (isBind) {
                intent.setClass(mActivity, PostionActivity.class);
                intent.putExtra("title", "位置");
                startActivity(intent);
            }
        } else if (view.getId() == R.id.tv_contacts) {
            if (isBind) {
                intent.setClass(mActivity, ContactsActivity.class);
                intent.putExtra("title", "紧急联系人");
                startActivity(intent);
            }
        } else if (view.getId() == R.id.tv_unbind) {
            intent.setClass(mActivity, MyWatchActivity.class);
            intent.putExtra("title", "我的设备");
            startActivity(intent);
        } else if (view.getId() == R.id.tv_about) {
            UIHelper.ToastMessage(mActivity,"关于我们");
        } else if (view.getId() == R.id.tv_out_login) {
            UIHelper.ToastMessage(mActivity,"退出登录");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}