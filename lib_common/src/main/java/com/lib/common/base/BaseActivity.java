package com.lib.common.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.common.R;
import com.lib.common.baseUtils.Utils;
import com.lib.common.baseUtils.permssion.EasyPermission;
import com.lib.common.baseUtils.permssion.PermissionCallBackM;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author yucheng
 * @date 2018/9/29 10:30
 * @Description
 */
public abstract class BaseActivity extends FragmentActivity {
    public Activity mActivity;
    public ProgressDialog mProgressDialog;
    Unbinder unbinder;

    public void showProgress() {//显示对话框
        if (null != mProgressDialog) {
            mProgressDialog.show();
        } else {
            mProgressDialog = new ProgressDialog(this, R.style.MainProgressDialogStyle);
            mProgressDialog.show();
        }

    }

    public void hindProgress() {         //隐藏进度条对话框
        if (null != mProgressDialog) {
            mProgressDialog.dismiss();
        }
    }

    protected final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mActivity = this;
        setTranslucentStatusColor(setColor());
        setScreenModel(screenModel());
        ARouter.getInstance().inject(this);
        ViewManager.getInstance().addActivity(this);
        setContentView(layoutId());
        unbinder = ButterKnife.bind(this);
        initData(bundle);
        initUIData(bundle);
    }


    public int screenModel() {
        return 1;
    }

    public int setColor() {
        return getResources().getColor(R.color.color_248bfe);
    }

    /**
     * 设置状态栏底色
     *
     * @return
     */
    public void setTranslucentStatusColor(int color) {
        //专门设置一下状态栏导航栏背景颜色为透明，凸显效果。
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(color);
        }
    }

    /**
     * 设置导航栏底色
     *
     * @return
     */
    public void setTranslucentNavigationColor(int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setNavigationBarColor(color);
        }
    }

    protected abstract int layoutId();

    protected abstract void initUIData(Bundle bundle);

    protected void initData(Bundle bundle) {
    }

    protected String getLogTag() {
        // return this class name
        return getClass().getName();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewManager.getInstance().finishActivity(this);
        if (unbinder != null) {
            unbinder.unbind();
        }
    }


    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        return super.onKeyDown(keyCode, event);
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    /**
     * 添加fragment
     *
     * @param fragment
     * @param frameId
     */
    protected void addFragment(BaseFragment fragment, @IdRes int frameId) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction().add(frameId, fragment, fragment.getClass().getSimpleName()).addToBackStack(fragment.getClass().getSimpleName()).commitAllowingStateLoss();

    }


    /**
     * 替换fragment
     *
     * @param fragment
     * @param frameId
     */
    protected void replaceFragment(BaseFragment fragment, @IdRes int frameId) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction().replace(frameId, fragment, fragment.getClass().getSimpleName()).addToBackStack(fragment.getClass().getSimpleName()).commitAllowingStateLoss();

    }


    /**
     * 隐藏fragment
     *
     * @param fragment
     */
    protected void hideFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction().hide(fragment).commitAllowingStateLoss();

    }


    /**
     * 显示fragment
     *
     * @param fragment
     */
    protected void showFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction().show(fragment).commitAllowingStateLoss();

    }


    /**
     * 移除fragment
     *
     * @param fragment
     */
    protected void removeFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();

    }


    /**
     * 弹出栈顶部的Fragment
     */
    protected void popFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    /**
     * 状态栏透明 View.SYSTEM_UI_FLAG_FULLSCREEN| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
     */
    public void setScreenModel(int model) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES; //设置绘图区域可以进入刘海屏区域
        getWindow().setAttributes(lp);
        View decorView = getWindow().getDecorView();
        int uiOptions = 0;
        switch (model) {
            //自定义
            case 0:
                uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                break;
            //不显示状态栏 显示导航栏
            case 1:
                uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                break;
            case 2:
                //全屏显示
                uiOptions =
                        View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                break;
            //沉浸显示状态栏 显示导航栏
            case 3:
                uiOptions = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                break;
        }
        decorView.setSystemUiVisibility(uiOptions);
    }

}