package com.qyai.watch_app.home;

import android.content.Intent;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.navigation.NavigationView;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.IntentKey;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.widgt.CircularProgressView;
import com.lib.common.widgt.MyDrawerLayout;
import com.qyai.watch_app.Common;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.bind.BindActivity;
import com.qyai.watch_app.measure.MeasureActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/watch/homeView")
public class HomeActivity extends BaseHeadActivity {
    @BindView(R2.id.drawer_layout)
    MyDrawerLayout drawer_layout;
    @BindView(R2.id.nav_view)
    NavigationView nav_view;

    @BindView(R.id.layout_stat)
    LinearLayout layout_stat;
    @BindView(R.id.tv_stat)
    TextView tv_stat;
    @BindView(R.id.tv_power)
    TextView tv_power;
    @BindView(R.id.iv_watch)
    ImageView iv_watch;

    @BindView(R.id.layout_xy)
    RelativeLayout layout_xy;
    @BindView(R.id.tv_resh_time_xy)
    TextView tv_resh_time;
    @BindView(R.id.tv_value_xy)
    TextView tv_value_xy;
    @BindView(R.id.iv_value_xy)
    CircularProgressView iv_value_xy;

    @BindView(R.id.layout_xl)
    RelativeLayout layout_xl;
    @BindView(R.id.tv_resh_time_xl)
    TextView tv_resh_time_xl;
    @BindView(R.id.tv_value_xl)
    TextView tv_value_xl;
    @BindView(R.id.iv_value_xl)
    CircularProgressView iv_value_xl;

    @BindView(R.id.tv_position)
    TextView tv_position;
    @BindView(R.id.tv_contacts)
    TextView tv_contacts;

    public boolean isBind = false;

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUIData() {
        setTvTitle("首页");
        hideIvRight(View.VISIBLE);
        tvLeft.setVisibility(View.INVISIBLE);
        setIvLeftSrc(R.drawable.ic_menu_slideshow);
        setIvRightSrc(R.drawable.ic_menu_gallery);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }


    protected void setIvLeftOnclick() {
        if (drawer_layout.isOpen()) {
            drawer_layout.closeDrawers();
        } else {
            drawer_layout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void setOnClickIvRight() {
        super.setOnClickIvRight();
        UIHelper.ToastMessage(mActivity, "消息");
    }

    @OnClick({R.id.layout_stat, R.id.layout_xy, R.id.layout_xl, R.id.tv_position, R.id.tv_contacts})
    public void onClick(View view) {
        if (view.getId() == R.id.layout_stat) {
            if (!isBind) {
                UIHelper.ToastMessage(mActivity, "设备未绑定");
                Intent intent = new Intent(mActivity, BindActivity.class);
                startActivityForResult(intent, IntentKey.REQ_UPLAOD);
            }

        } else if (view.getId() == R.id.layout_xy) {
            if (isBind) {
                Intent intent = new Intent(mActivity, MeasureActivity.class);
                intent.putExtra("title",Common.MEASURE_XY);
                startActivity(intent);
            }
        } else if (view.getId() == R.id.layout_xl) {
            if (isBind) {
                Intent intent = new Intent(mActivity, MeasureActivity.class);
                intent.putExtra("title",Common.MEASURE_XL);
                startActivity(intent);
            }

        } else if (view.getId() == R.id.tv_position) {

        } else if (view.getId() == R.id.tv_contacts) {

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}