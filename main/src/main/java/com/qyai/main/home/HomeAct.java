package com.qyai.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSONObject;
import com.lib.common.BaseMvp.BaseMvpAct;
import com.lib.common.BaseMvp.factory.CreateMvpPresenter;
import com.lib.common.base.ViewManager;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.baseUtils.ZTLUtils;
import com.lib.common.baseUtils.permssion.PermissionCheckUtils;
import com.qyai.main.Common;
import com.qyai.main.MainFrag;
import com.qyai.main.MeFrag;
import com.qyai.main.R;
import com.qyai.main.R2;
import com.qyai.main.login.bean.UserEvent;


import butterknife.BindView;
import butterknife.OnClick;


/**
 * 作者:yucheng
 * 时间:2018/9/19 0019 上午 8:59
 * 功能描述：首页界面
 */
@CreateMvpPresenter(HomePersenter.class)
@Route(path = "/main/home")
public class HomeAct extends BaseMvpAct<HomeIView, HomePersenter> implements HomeIView {
    @BindView(R2.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R2.id.rb_work)
    TextView rbWork;
    @BindView(R2.id.rb_mine)
    TextView rbManage;
    UserEvent.UserData userData;
    private MainFrag mapFragment;
    private MeFrag mineFragment;
    long mExitTime = 1000;

    @Override
    protected int layoutId() {
        return R.layout.act_home;
    }


    @Override
    protected void initUIData(Bundle bundle) {
        setTranslucentNavigationColor(getResources().getColor(R.color.color_248bfe));
        setScreenModel(3);
        userData = JSONObject.parseObject(SPValueUtil.getStringValue(mActivity, Common.USER_DATA), UserEvent.UserData.class);
        Common.openGPSSEtting(HomeAct.this);
        PermissionCheckUtils.requestPermissions(HomeAct.this, Constants.REQUEST_CODE, Common.permissionList); // 动态请求权限
        changeBtn(0);
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

    /**
     * 设置沉浸式状态栏颜色
     *
     * @param color
     */
    public void setColorTitle(int color) {
        ZTLUtils.setStatusBarColor(HomeAct.this, color);
    }

    @Override
    public void showLodingDialog() {
        showProgress();
    }

    @Override
    public void hidLodingDialog() {
        hindProgress();
    }

    @Override
    public void showErrMsg(String msg) {

    }

    @OnClick({R2.id.rb_work, R2.id.rb_mine})
    public void onClick(View v) {
        if (v.getId() == R.id.rb_work) {
            changeBtn(0);
        }
        if (v.getId() == R.id.rb_mine) {
            changeBtn(1);
        }
    }

    public void changeBtn(int type) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        rbManage.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.mipmap.main_mine_normal, 0, 0);
        rbManage.setTextColor(getResources().getColor(R.color.color_6));
        rbWork.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.mipmap.main_work_normal, 0, 0);
        rbWork.setTextColor(getResources().getColor(R.color.color_6));
        switch (type) {
            case 0:
                setScreenModel(2);
                rbWork.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.mipmap.main_work_checked, 0, 0);
                rbWork.setTextColor(getResources().getColor(R.color.color_circle_blue));
                if (mineFragment != null) {
                    fragmentTransaction.hide(mineFragment);
                }
                if (mapFragment == null) {
                    mapFragment = new MainFrag();
                    fragmentTransaction.add(R.id.frameLayout, mapFragment, mapFragment.getClass().getName());
                } else {
                    fragmentTransaction.show(mapFragment);
                }
                break;
            case 1:
                setScreenModel(2);
                rbManage.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.mipmap.main_mine_checked, 0, 0);
                rbManage.setTextColor(getResources().getColor(R.color.color_circle_blue));
                fragmentTransaction.hide(mapFragment);
                if (mineFragment == null) {
                    mineFragment = new MeFrag();
                    fragmentTransaction.add(R.id.frameLayout, mineFragment, mineFragment.getClass().getName());
                }
                fragmentTransaction.show(mineFragment);
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }
}
