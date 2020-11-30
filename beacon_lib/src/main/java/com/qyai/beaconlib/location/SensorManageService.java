package com.qyai.beaconlib.location;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.lib.common.baseUtils.Common;
import com.qyai.beaconlib.bean.PositioningResultBean;
import com.qyai.beaconlib.bean.SensorEventBean;
import com.qyai.beaconlib.login.bean.UserEvent;
import com.qyai.beaconlib.utlis.Constants;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.permssion.PermissionCheckUtils;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.OnHttpCallBack;
import com.qyai.beaconlib.Interface.OnBeaconUpdateListener;
import com.qyai.beaconlib.R;
import com.qyai.beaconlib.beaconSensor.DBleScanner;
import com.qyai.beaconlib.bean.Beacon;
import com.qyai.beaconlib.bean.OperatingController;
import com.qyai.beaconlib.bean.PositioningDataBean;
import com.qyai.beaconlib.bean.QYPositionBean;
import com.qyai.beaconlib.location.bean.ReqBeaconInfo;
import com.qyai.beaconlib.utlis.SensorManagerUtlis;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

public class SensorManageService extends Service {

    private DBleScanner dBleScanner;


    public static final String CHANNEL_ID_STRING = "service_01";
    private static final String TAG = "SensorManageService";
    private BroadcastReceiver receiver = null;

    private String userId = null;

    /**
     * 手机消息通知管理器，用于弹系统消息通知
     */
    private NotificationManager manager = null;
    private NotificationChannel notificationChannel = null;

    //震动
    private Vibrator vibrator = null;

    private String notificationText = null;

    private int beaconCount = 0;


    private String[] filterMajors = {"10092", "10270","20001"};//TODO以后信标有效范围应该是从数据库初始化
    private static final int rssiDataInerval = 1000;        //两组组rssi数据间隔时间 ms
    private static final int maxRssiCount = 20;            //每组rssi最大个数；
    /**
     * 定时检查蓝牙扫描情况，如果蓝牙关闭了，则进行启动
     */
    private Timer _beaconCheckTimer = null;
    /**
     * 蓝牙功能状态值
     */
    private boolean isBeaconSuccess = false;


    /**
     * 定时检查蓝牙扫描情况，如果蓝牙关闭了，则进行启动
     */
    private Handler cheackBeaconHandler = new Handler();

    Runnable cheackBeaconRunnable = new Runnable() {
        @Override
        public void run() {
            // 每7秒检测1次
            if (isBeaconSuccess == false) {
                int result = dBleScanner.start(null);
                if (result == -2) {
                    // result=-2代表没有打开蓝牙
                    //需要触发事件，启动蓝牙功能
                    EventBus.getDefault().post(new OperatingController(Constants.alarmIsComing, Constants.NOTIFICATION_BEACON));
                }
            }
            cheackBeaconHandler.postDelayed(cheackBeaconRunnable, 7000);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        /***适配8.0 解决报IllegalStateException  start***/
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID_STRING, getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);
            Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID_STRING).build();
            startForeground(1, notification);
        }
        /***适配8.0 解决报IllegalStateException  end***/
        Log.i("6666666666666", this.getClass().getName());
        //创建蓝牙信标信息扫描主程序
        dBleScanner = new DBleScanner(getApplicationContext());
        //获取手机通知对象
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Get instance of Vibrator from current Context
        //获取手机震动对象
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


//        this.createNotification(null);
        //获取手机陀螺仪
        SensorManagerUtlis.getInstance().initSensor(getApplicationContext());
        //监听锁屏，进行程序保活
        initBroadcastReceiver();

        //启动蓝牙信标扫描、处理等相关程序
        startBeaconScan();

    }


    /**
     * 服务启动入口，每次服务启动时都会调用该方法
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w(TAG, "SensorManageService.onStartCommand");

        //获取登录用户
        UserEvent.UserData data = JSONObject.parseObject(SPValueUtil.getStringValue(getApplicationContext(), Common.USER_DATA), UserEvent.UserData.class);
        if (!TextUtils.isEmpty(data.getUserInDeptDTO().getUserId())) {
            userId = data.getUserInDeptDTO().getUserId();
        }

        //启动蓝牙信标扫描器
        startBeaconScan();

        //启动定时任务，定时检查蓝牙扫描情况，如果蓝牙关闭了，则进行启动
        //TODO 这个JobSchedulerManager也是一个定时器 这里可能定时器重复
        JobSchedulerManager.getJobSchedulerInstance(this.getApplicationContext()).startJobScheduler();
        cheackBeaconHandler.postDelayed(cheackBeaconRunnable, 7000);
        return START_STICKY;
    }


    /**
     * 监听锁屏，进行程序保活
     */
    private void initBroadcastReceiver() {

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() == Intent.ACTION_SCREEN_OFF) {
                    Intent intent1 = new Intent(getApplicationContext(), LockScreenActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(receiver, filter);
    }


    /**
     * 信标信息上报的数据集
     */
    private PositioningDataBean dataSend = new PositioningDataBean();
    /**
     * 当前扫描到的待处理信标信息结果
     */
    private List<Beacon> beaconRssiList = new ArrayList<>();
    /**
     * 信标处理器
     */
    private Handler beaconHandler = new Handler();
    /**
     * 信标信息处理线程，被beaconHandler调用，调用间隔rssiDataInerval
     */
    private Runnable beaconRunnable = new Runnable() {
        @Override
        public void run() {
            beaconHandler.removeCallbacksAndMessages(null);
           prepareBeaconData(this);

        }
    };

    /**
     * 进行蓝牙信标扫描结果监听
     * 启动蓝牙扫描主程序
     */
    private void startBeaconScan() {
        Log.d(TAG, "蓝牙扫描开启");
        //进行蓝牙信标扫描结果监听
        dBleScanner.setOnBeaconUpdateListener(new OnBeaconUpdateListener() {
            @Override
            public void onBeaconUpdate(Beacon beacon) {
                boolean isValidMajor = isValidMajor(beacon.major);
                if (!isValidMajor) {
                    return;
                }
                Beacon tmp;
                synchronized (beaconRssiList) {

                    int changePosition = -1;
                    for (int i = 0; i < beaconRssiList.size(); i++) {
                        tmp = beaconRssiList.get(i);
                        if (tmp.major.equals(beacon.major) && tmp.minor.equals(beacon.minor)) {
                            changePosition = i;
                            break;
                        }
                    }
                    if (changePosition < 0) {
                        Log.v(TAG, "add beacon:" + beacon.toString());
                        beaconRssiList.add(beacon);
                    } else {
                        beaconRssiList.set(changePosition, beacon);
                    }
                }
            }
        });
        //启动蓝牙扫描主程序
        dBleScanner.start(null);
        //启动信标扫描结果的处理器
        startBeaconRunnable();

    }

    /**
     * 启动蓝牙信息扫描结果处理器，定时循环处理
     */
    private void startBeaconRunnable() {
        beaconHandler.removeCallbacksAndMessages(null);
        beaconHandler.postDelayed(beaconRunnable, 1000);
    }

    /**
     * 停止蓝牙信息扫描结果处理器
     */
    private void stopBeaconRunnable() {
        beaconHandler.removeCallbacksAndMessages(null);
        beaconRssiList.clear();
    }

    /**
     * 进行信标信息上报前的预处理
     * 上报前先进行信号量的排序
     * 调用上报服务
     * 进行信标扫描结果检查，如果超过3次没有正确的信标信息，则进行扫描程序检查
     *
     * @param runnable
     */
    void prepareBeaconData(Runnable runnable) {
        List<Beacon> bRssiList = null;
        synchronized (beaconRssiList) {
            Log.d(TAG, "信标预处理：" + beaconRssiList.size());
            if (beaconRssiList.size() > 0) {
                bRssiList = new ArrayList<>();
                bRssiList.addAll(beaconRssiList);
                isBeaconSuccess = true;
                beaconCount = 0;
            } else {
                beaconCount++;
                if (beaconCount >= 3) {
                    isBeaconSuccess = false;
                    EventBus.getDefault().post(new QYPositionBean());
                }
            }
            beaconRssiList.clear();
            beaconHandler.postDelayed(runnable, rssiDataInerval);
        }
        //TODO 尝试放弃信号强弱排序优先；以时间优先
        if (bRssiList != null && bRssiList.size() > 0) {
            Collections.sort(bRssiList, (o1, o2) -> o2.rssi - o1.rssi);
        }
        //根据时间戳进行排序
        if (bRssiList != null && bRssiList.size() > 0) {
            EventBus.getDefault().post(bRssiList);
            sendBeaconDataToQYServer(bRssiList);
        }
    }

    /**
     * 广播定位服务返回的位置信息
     *
     * @param position
     */
    void handlePosition(QYPositionBean position) {
        if (position != null) {
            Log.d(TAG, "返回的位置PositionEntity为:" + position.toString() + "----" + System.currentTimeMillis());
            EventBus.getDefault().post(position);
        } else {
            Log.d(TAG, "返回的位置PositionEntity为:" + position.toString() + "----" + System.currentTimeMillis());
//            EventBus.getDefault().post(new PositionEntity(position.buildId));
        }
    }

    /**
     * 检查信标信息是否在合法范围内
     * TODO 目标判断范围是写死的，以后需要实现动态从数据库初始化
     *
     * @param major
     * @return
     */
    private boolean isValidMajor(String major) {
        for (String m : filterMajors) {
            if (m.equals(major)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将扫描到的信标信息做预处理，然后上报给定位服务，进行坐标计算，计算结果会返回完整位置信息
     *
     * @param beaconList
     */
    void sendBeaconDataToQYServer(List<Beacon> beaconList) {
        Log.v(TAG, "蓝牙准备上报：" + beaconList.size());
        // 发送蓝牙数据到服务器获取定位
        if (userId == null || userId.equals("")) {
            return;
        }
        int size = beaconList.size();
        if (size == 0) {
            return;
        }
        ArrayList<Beacon> beaconListToSend = new ArrayList<Beacon>(maxRssiCount + 3);
        for (int i = 0; i < beaconList.size() && i < maxRssiCount; i++) {
            beaconListToSend.add(beaconList.get(i));
        }
        dataSend.addBeaconList(beaconListToSend);
        String code = SPValueUtil.getStringValue(getApplicationContext(), Common.BRACELET_MAC);
        if (SPValueUtil.isEmpty(code)) {
            // TODO: 2020/11/25  这里加手环id 
            dataSend.setDeviceId(code);
        }
        ArrayList<SensorEventBean> list = SensorManagerUtlis.getInstance().getSensorEventslist();
        synchronized (list) {
            Log.v(TAG, "uploadBeacon");
            dataSend.addGsensorEvents(list);//陀螺仪、运动检测相关
            int direction = SensorManagerUtlis.getInstance().calculateOrientation();
            dataSend.setDirection(direction);//计算方向信息
            HttpServiec.getInstance().postFlowableData(100, Constants.UPLOADBEACON, dataSend, new OnHttpCallBack<PositioningResultBean>() {
                @Override
                public void onSuccessful(int id, PositioningResultBean baseBean) {
                    QYPositionBean data = baseBean.getData();
                    Log.d("上传回调了====>", baseBean.toString());
                    //处理定位服务计算好的位置信息
                    if (Constants.RESULT_SUCCESS.equals(baseBean.getCode()) &&
                            data != null &&
                            SPValueUtil.isEmpty(data.getBuildId()) &&
                            SPValueUtil.isEmpty(data.getUserId())) {
                        data.setDirection(direction);
                        Log.d("蓝牙准备上报", data.getBuildId() + "");
                        data.setDirection(SensorManagerUtlis.getInstance().calculateOrientation());
                        handlePosition(data);//广播位置信息
                    }
                }

                @Override
                public void onFaild(int id, PositioningResultBean o, String err) {

                }
            }, PositioningResultBean.class);
            //向定位服务上报信标信息
        }
        list.clear();
        SensorManagerUtlis.getInstance().getSensorEventslist().clear();
        dataSend = new PositioningDataBean();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(OperatingController operatingController) {
        switch (operatingController.value) {
            //信标查询回调
            case 0:
                dBleScanner.start(operatingController.commandResult);
                break;
            case 1:
                dBleScanner.stop(operatingController.commandResult);
                break;
            //创建通知
            case 3:
                break;
            //地图是进入resume
            case 4:
                if (Constants.NOTIFICATION_ALARM.equals(notificationText)) {
                    EventBus.getDefault().post(new OperatingController(Constants.onMainAtyAlarmIsComing));
                } else if (Constants.NOTIFICATION_BEACON.equals(notificationText)) {
                }
                break;
            case 5:
                dBleScanner.unsetOnBeaconUpdateListener();
                dBleScanner.setScanTime();
                startBeaconScan();
                break;
        }
    }

    public static void initService(Activity activity) {
        Common.openGPSSEtting(activity);
        PermissionCheckUtils.requestPermissions(activity, Constants.REQUEST_CODE, Common.permissionList); // 动态请求权限
        Intent serviceIntent = new Intent(activity, SensorManageService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.startForegroundService(serviceIntent);
        } else {
            activity.startService(serviceIntent);
        }
    }

    public static void stopService(Activity activity) {
        Intent stopIntent = new Intent(activity, SensorManageService.class);
        activity.stopService(stopIntent);
    }

    @Override
    public Context getApplicationContext() {
        return getBaseContext();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        dBleScanner.destroy();
        SensorManagerUtlis.getInstance().stopSensor();
        stopBeaconRunnable();
        NotificationManager mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mManager.cancel(Constants.NOTICE_ID);
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        beaconHandler.removeCallbacksAndMessages(null);
        //解绑扫描
        JobSchedulerManager.getJobSchedulerInstance(this.getApplicationContext()).stopJobScheduler();
        cheackBeaconHandler.removeCallbacks(cheackBeaconRunnable);
        Log.e(TAG, "onDestroy()关闭服务");
    }

    public ReqBeaconInfo changeModle(Beacon beacon) {
        return new ReqBeaconInfo(beacon.major, beacon.minor, beacon.pow + "", SensorManagerUtlis.getInstance().calculateOrientation() + "", beacon.rssi + "");
    }



}

