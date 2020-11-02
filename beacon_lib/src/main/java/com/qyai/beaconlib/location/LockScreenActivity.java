package com.qyai.beaconlib.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.qyai.beaconlib.R;
import com.qyai.beaconlib.bean.OperatingController;
import com.qyai.beaconlib.utlis.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LockScreenActivity extends AppCompatActivity {


    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD); // 去掉系统锁屏页
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED); // 使Activity在锁屏时能够显示

        // 隐藏底部的导航栏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            if (v != null) {
                v.setSystemUiVisibility(View.GONE);
            }
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            if (decorView != null) {
                decorView.setSystemUiVisibility(uiOptions);
            }
        }

        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        getWindow().setStatusBarColor(0x703a72ed);
        setContentView(R.layout.activity_lock);
        textView = findViewById(R.id.lock_text);
        textView.setVisibility(View.GONE);

        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(OperatingController operatingController) {
        switch (operatingController.value) {
            case Constants.alarmIsComing:
                wakeUpAndUnlock(LockScreenActivity.this);
                textView.setVisibility(View.VISIBLE);
                if(operatingController.msg == null || "".equals(operatingController.msg)) {
                    textView.setText("收到警情命令！");
                } else {
                    textView.setText(operatingController.msg);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
    }

    private int mWidth = 0;
    private float mStartX = 0;
    private float mStartY = 0;

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float nx = event.getX();
        float ny = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mStartX = nx;
                mStartY = ny;
                break;
            case MotionEvent.ACTION_MOVE:
                handleMoveView(nx, ny);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                doMoveEvent(nx, ny);
                break;
        }
        return true;
    }

    private void handleMoveView(float nx, float ny) {
        if(Math.abs(nx - mStartX) > 50 || Math.abs(ny - mStartX) > 50) {
            finish();
        }
    }

    private void doMoveEvent(float nx, float ny) {
        if(Math.abs(nx - mStartX) > 50 || Math.abs(ny - mStartX) > 50) {
            finish();
        }
    }
//    屏幕点亮
    public static void wakeUpAndUnlock(Context context){
//        //屏锁管理器
//        KeyguardManager km= (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
//        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
//        //解锁
//        kl.disableKeyguard();
        //获取电源管理器对象
        PowerManager pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.SCREEN_DIM_WAKE_LOCK,"bright");
        //点亮屏幕
        wl.acquire();
        //释放
//        wl.release();
    }
}
