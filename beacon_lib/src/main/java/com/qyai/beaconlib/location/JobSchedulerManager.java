package com.qyai.beaconlib.location;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

public class JobSchedulerManager {
    private static final int JOB_ID = 1;
    private static JobSchedulerManager mJobManager;
    private JobScheduler mJobScheduler;
    private static Context mContext;

    private JobSchedulerManager(Context ctxt) {
            this.mContext = ctxt;
            mJobScheduler = (JobScheduler) ctxt.getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }

    public final static JobSchedulerManager getJobSchedulerInstance(Context ctxt) {
        if(ctxt == null) {
            ctxt = mContext;
        }
        if (mJobManager == null && ctxt != null) {
            mJobManager = new JobSchedulerManager(ctxt);
        }
        return mJobManager;
    }

    public void startJobScheduler() {
        // 如果JobService已经启动或API<21，返回
        if (AliveJobService.isJobServiceAlive() || isBelowLOLLIPOP()) {
            return;
        }
        // 构建JobInfo对象，传递给JobSchedulerService
        int id = JOB_ID;
        mJobScheduler.cancel(id);
        JobInfo.Builder builder = new JobInfo.Builder(id, new ComponentName(mContext, AliveJobService.class));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setOverrideDeadline(20000);  //执行的最长延时时间
            builder.setMinimumLatency(10000);
            builder.setBackoffCriteria(3000, JobInfo.BACKOFF_POLICY_LINEAR);//线性重试方案
        } else {
            builder.setPeriodic(10000);
        }
        builder.setRequiresCharging(false);//是否在只有插入充电器的时候执行
        builder.setRequiresDeviceIdle(false);//是否手机系统处于空闲状态下执行
        builder.setPersisted(false);  // 设置设备重启时，执行该任务
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);
        JobInfo info = builder.build();
        mJobScheduler.schedule(info); //开始定时执行该系统任务
    }

    public void stopJobScheduler() {
        if (isBelowLOLLIPOP())
            return;
        mJobScheduler.cancelAll();
    }

    private boolean isBelowLOLLIPOP() {
        // API< 21
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
    }
}