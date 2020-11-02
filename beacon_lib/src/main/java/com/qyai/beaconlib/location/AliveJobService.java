package com.qyai.beaconlib.location;

import android.app.ActivityManager;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import com.lib.common.baseUtils.LogUtil;

import java.util.List;

public class AliveJobService extends JobService {
    private final static String TAG = "KeepAliveService";
    private volatile static Service mKeepAliveService = null;

    public static boolean isJobServiceAlive() {
        return mKeepAliveService != null;
    }

    private static final int MESSAGE_ID_TASK = 0x01;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (isServiceWork(getApplicationContext(), SensorManageService.class.getName())) {
                LogUtil.d(TAG, "KeepAliveService----->mHandler:服务仍然没死掉...");
            } else {
                try {
                    Intent intent = new Intent(getApplicationContext(), SensorManageService.class);
                    startService(intent);
                    LogUtil.d(TAG, "KeepAliveService----->mHandler:服务死掉了，重启中...");
                } catch (Exception e) {
                    LogUtil.d(TAG, "KeepAliveService----->mHandler:服务死掉了，重启失败:" + e.getMessage());
                }
            }
            // 通知系统任务执行结束
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // Android 7.0以上
                mKeepAliveService = null;
                jobFinished((JobParameters) msg.obj, false); // 不能为true,因为它这个线性重试是会不断拉大时间的，即无法保证定时轮询
                if (JobSchedulerManager.getJobSchedulerInstance(null) != null) {
                    JobSchedulerManager.getJobSchedulerInstance(null).startJobScheduler();
                }
            } else {
                mKeepAliveService = null;
                jobFinished((JobParameters) msg.obj, false);
            }
            return true;
        }
    });

    @Override
    public boolean onStartJob(JobParameters params) {
        LogUtil.d(TAG, "KeepAliveService----->JobService服务被启动...");
        mKeepAliveService = this;
        // 返回false，系统假设这个方法返回时任务已经执行完毕；
        // 返回true，系统假定这个任务正要被执行
        Message msg = Message.obtain(mHandler, MESSAGE_ID_TASK, params);
        mHandler.sendMessage(msg);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        LogUtil.d(TAG, "KeepAliveService----->JobService服务被关闭");
        return true;
    }

    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}