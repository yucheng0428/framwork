package com.qyai.framapp;

import android.Manifest;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.common.base.BaseActivity;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.Utils;
import com.lib.common.baseUtils.permssion.PermissionCheckUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

@Route(path = "/test/main")
public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_time)
    TextView text_time;
    Disposable disposable;

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUIData(Bundle bundle) {
        setTranslucentNavigationColor(getResources().getColor(R.color.half_transparent));
        PermissionCheckUtils.requestPermissions(mActivity, Constants.REQUEST_CODE,  new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}); // 动态请求权限
        if (Utils.hasPermission(mActivity, android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            adLoading();
        }
    }


    public void adLoading() {
        disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return 5 - (aLong + 1);
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(final Long count) throws Exception {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text_time.setText(count + "");
                            }
                        });
                        if (count == 1) {
                            Thread.sleep(1000);
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ARouter.getInstance().build("/main/login")
                                            .withString("userName", "SH")
                                            .withString("psw", "888888")
                                            .withInt("viewType",2)
                                            .navigation();
                                }
                            });

                            if (disposable != null) {
                                disposable.dispose();
                            }
                            mActivity.finish();
                        }
                    }
                });
    }


    //获取权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==Constants.REQUEST_CODE){
            adLoading();
        }
    }
}