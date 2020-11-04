package com.qyai.framapp;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.common.base.BaseActivity;
import android.widget.TextView;

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
        disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return 3 - (aLong + 1);
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(final Long count) throws Exception {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text_time.setText(count+"");
                            }
                        });
                        if (count == 0) {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ARouter.getInstance().build("/main/login")
                                            .withString("userName", "043640")
                                            .withString("psw", "888888")
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

}