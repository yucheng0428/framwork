package com.lib.common.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.lib.common.R;
import com.lib.common.baseUtils.Utils;
import com.lib.common.baseUtils.ZTLUtils;
import com.lib.common.baseUtils.permssion.EasyPermission;
import com.lib.common.baseUtils.permssion.PermissionCallBackM;

import butterknife.ButterKnife;

/**
 * <p>类说明</p>
 * 简单的带标题 的基类 布局里带一个筛选框 这里不做操作
 * 如有需要可以在重写一个 针对筛选框的基类
 *
 * @author yucheng
 * @date 2018-9-25
 * @Description
 */
public abstract class BaseHeadActivity extends FragmentActivity {
    LinearLayout llContainer;
    public TextView tvTitle = null;//标题
    public ImageView ivLeft = null;//左上角图片
    public TextView tvLeft = null;//左上文字

    public ImageView ivRight = null;//右上图片
    TextView tvRight = null;//右上文字
    public LinearLayout headSearch_layout;
    public EditText contact_search;

    protected boolean setStatusBar = true;
    public Activity mActivity;
    private int mRequestCode;
    public ProgressDialog mProgressDialog;
    private static Bundle mBundle;

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
        setContentView(R.layout.act_head_baseview);
        if (setStatusBar) {
            new ZTLUtils(this).initSystemBar(this, R.color.white);
        }
        mActivity = this;
        ViewManager.getInstance().addActivity(this);
        llContainer = findViewById(R.id.llcontainer);
        View subView = LayoutInflater.from(this).inflate(layoutId(), null);
        llContainer.addView(subView);
        ButterKnife.bind(this, subView);
        tvTitle = findViewById(R.id.tvTitle);
        ivLeft = findViewById(R.id.ivLeft);
        tvLeft = findViewById(R.id.tvLeft);
        tvRight = findViewById(R.id.tvRight);
        ivRight = findViewById(R.id.ivRight);
        headSearch_layout = findViewById(R.id.headSearch_layout);
        contact_search = findViewById(R.id.contact_search);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setIvLeftOnclick();
            }
        });
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnClickTvRight();
            }
        });
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnClickIvRight();
            }
        });
        mBundle=bundle;
        initUIData();
    }

    protected void setIvLeftOnclick() {
        onBackPressed();
    }


    public abstract int layoutId();
    protected abstract void initUIData();

    public Bundle getBundle(){
        return mBundle;
    }
    protected String getLogTag() {
        return getClass().getName();
    }

    public void hideIvLeft(int code) {
        ivLeft.setVisibility(code);
    }

    /**
     * @param title 添加标题
     */
    public void setTvTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 点击右侧图片
     */
    public void setOnClickIvRight() {

    }

    public void hideIvRight(int code) {
        ivRight.setVisibility(code);
    }

    public void setIvLeftSrc(int drawable) {
        ivLeft.setImageResource(drawable);
    }

    public void setIvRightSrc(int drawable) {
        ivRight.setImageResource(drawable);
    }

    /**
     * 点击右侧文字
     */
    public void setOnClickTvRight() {

    }

    public void hideTvRight(int code) {
        tvRight.setVisibility(code);
    }

    public void setTvRightMsg(String msg) {
        tvRight.setText(msg);
    }

    public void setTvLeftMsg(String msg) {
        tvRight.setText(msg);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewManager.getInstance().finishActivity(this);
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

}
