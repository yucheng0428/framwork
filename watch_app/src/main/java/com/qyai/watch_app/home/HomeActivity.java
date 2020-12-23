package com.qyai.watch_app.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.lib.common.base.BaseActivity;
import com.lib.common.base.ViewManager;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.IntentKey;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.dialog.LookBigPictureDialog;
import com.lib.common.widgt.MyDrawerLayout;
import com.lib.common.widgt.RoundImageView;
import com.qyai.watch_app.Common;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.bind.BindActivity;
import com.qyai.watch_app.contacts.ContactsActivity;
import com.qyai.watch_app.measure.MeasureActivity;
import com.qyai.watch_app.message.MessageActivity;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/watch/HomeActivity")
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
    @BindView(R2.id.layout_stat)
    RelativeLayout layout_stat;
    @BindView(R2.id.layout_no_bind)
    RelativeLayout layout_no_bind;
    @BindView(R2.id.tv_stat)
    TextView tv_stat;
    @BindView(R2.id.tv_power)
    TextView tv_power;

    @BindView(R2.id.layout_xy)
    View layout_xy;
    @BindView(R2.id.tv_resh_time_xy)
    TextView tv_resh_time_xy;
    @BindView(R2.id.tv_value_xy)
    TextView tv_value_xy;
    @BindView(R2.id.tv_value_unit_xy)
    TextView tv_value_unit_xy;

    @BindView(R2.id.layout_xl)
    View layout_xl;
    @BindView(R2.id.tv_resh_time_xl)
    TextView tv_resh_time_xl;
    @BindView(R2.id.tv_value_xl)
    TextView tv_value_xl;
    @BindView(R2.id.tv_value_unit_xl)
    TextView tv_value_unit_xl;

    @BindView(R2.id.tv_position)
    TextView tv_position;
    @BindView(R2.id.tv_contacts)
    TextView tv_contacts;

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
    public boolean isBind = true;
    private long mExitTime = 0;

    @Override
    public int layoutId() {
        return R.layout.activity_home_view;
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
        onLine(true);
        Glide.with(mActivity).load(Constants.imageUrl).placeholder(R.drawable.icon_loadings).skipMemoryCache(true).into(iv_head);
    }


    public void onLine(boolean online) {
        if(online){
            tv_stat.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.drawable.ruand_blue), null, null, null);
            tv_stat.setText("手表在线");
            isMesureData(true);
        }else {
            tv_stat.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.drawable.ruand_btn), null, null, null);
            tv_stat.setText("手表不在线");
            isMesureData(false);
        }
    }
    public void isMesureData(boolean flag) {
        if(flag){
            tv_value_xy.setText("正常");
            tv_value_xy.setTextColor(getResources().getColor(R.color.white));
            tv_value_xy.setBackground(getResources().getDrawable(R.mipmap.icon_xy_bg));
            tv_resh_time_xy.setVisibility(View.VISIBLE);
            tv_value_unit_xy.setVisibility(View.VISIBLE);
            tv_value_xl.setText("正常");
            tv_value_xl.setTextColor(getResources().getColor(R.color.white));
            tv_value_xl.setBackground(getResources().getDrawable(R.mipmap.icon_xy_bg));
            tv_resh_time_xl.setVisibility(View.VISIBLE);
            tv_value_unit_xl.setVisibility(View.VISIBLE);
        }else {
            tv_value_xy.setText("无数据");
            tv_value_xy.setTextColor(getResources().getColor(R.color.color_217AFF));
            tv_value_xy.setBackground(null);
            tv_resh_time_xy.setVisibility(View.GONE);
            tv_value_unit_xy.setVisibility(View.GONE);
            tv_value_xl.setText("无数据");
            tv_value_xl.setTextColor(getResources().getColor(R.color.color_217AFF));
            tv_value_xl.setBackground(null);
            tv_resh_time_xl.setVisibility(View.GONE);
            tv_value_unit_xl.setVisibility(View.GONE);
        }
    }

    public void onBind() {
        layout_stat.setVisibility(View.GONE);
        layout_no_bind.setVisibility(View.VISIBLE);
    }

    @OnClick({R2.id.layout_stat, R2.id.layout_no_bind, R2.id.layout_xy, R2.id.layout_xl,
            R2.id.tv_position, R2.id.tv_contacts, R2.id.ivLeft,
            R2.id.ivRight, R2.id.tv_unbind, R2.id.tv_about, R2.id.tv_out_login,R2.id.iv_head})
    public void onClick(View view) {
        Intent intent = new Intent();
        if (view.getId() == R.id.layout_no_bind) {
            intent.setClass(mActivity, BindActivity.class);
            startActivityForResult(intent, IntentKey.REQ_UPLAOD);

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
                ARouter.getInstance().build("/maplib/PostionActivity").navigation();
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
            UIHelper.ToastMessage(mActivity, "关于我们");
        } else if (view.getId() == R.id.tv_out_login) {
            UIHelper.ToastMessage(mActivity, "退出登录");
        }else if(view.getId()==R.id.iv_head){
            new LookBigPictureDialog(mActivity,Constants.imageUrl).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //两秒之内按返回键就会退出
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                UIHelper.ToastMessage(this, "再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                ViewManager.getInstance().exitApp(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}