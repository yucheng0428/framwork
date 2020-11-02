package com.qyai.beaconlib.beaconSensor;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.lib.common.baseUtils.LogUtil;
import com.qyai.beaconlib.Interface.CommandResult;
import com.qyai.beaconlib.Interface.OnBeaconUpdateListener;
import com.qyai.beaconlib.Interface.PowCallBack;
import com.qyai.beaconlib.bean.Beacon;
import com.qyai.beaconlib.bean.BeaconStatus;
import com.qyai.beaconlib.bean.OperatingController;
import com.qyai.beaconlib.bean.SignalStatus;
import com.qyai.beaconlib.utlis.Constants;

import org.altbeacon.beacon.service.scanner.CycledLeScanner;
import org.altbeacon.bluetooth.BluetoothCrashResolver;
import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 蓝牙信标信息扫描主程序
 * 负责信标扫描
 * 信标信息解析封装
 * 封装好的信标信息广播
 * 监听手机蓝牙功能状态
 * 负责保持手机蓝牙功能处于开启
 * 进行蓝牙信标扫描异常检查
 */
public class DBleScanner {

    public final static String TAG="DBleScanner";
    Context _context = null;
    /**
     * 用于判断蓝牙信标扫描是否能正常扫描到信标
     */
    private int _signalStatus = 0;
    /**
     * 定时扫描检查是否能正常扫描到蓝牙信标
     */
    private int SIGNAL_INTERVAL = 6000;
    /**
     * 最后一次扫描蓝牙信标的时间
     */
    private long _lastSignalTime = 0;
    /**
     * 检查是否能正常扫描到蓝牙信标的定时器
     */
    private Timer _signalCheckTimer = new Timer();
    /**
     * 周期扫描信标的扫描器
     */
    private CycledLeScanner _shitScanner = null;

    /**
     * 扫描程序是否正在启动中
     */
    private boolean isStarting = false;
    /**
     * 扫描程序是否已经启动成功了
     */
    private boolean isStart = false;

    public DBleScanner(Context context) {
        this._context = context;
        // 设置1000毫秒的扫描时间+1毫秒的扫描间隔，相当于不间断地进行蓝牙扫描
        this._shitScanner = CycledLeScanner1.createScanner(this._context, Constants.BEACON_SCAN_TIME, Constants.BEACON_BETWEEN_SCAN_PERIOD, true, new PowCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void powValue(ScanResult pow) {
                processBleData(pow.getScanRecord().getBytes(), pow.getRssi(), pow.getTxPower());
            }

            @Override
            public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
//                Log.w("TXR",i+"");
//                processBleData(bytes, i,null);
            }

            @Override
            public void onCycleEnd() {

            }
        }, new BluetoothCrashResolver(this._context));

        //监听手机的蓝牙状态变化
        IntentFilter itf = new IntentFilter();
        itf.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        this._context.registerReceiver(_myBtStatusReceive, itf);
    }

    /**
     * 设置扫描时间
     */
    public void setScanTime() {
        this._shitScanner.setScanPeriods(Constants.BEACON_SCAN_TIME, Constants.BEACON_BETWEEN_SCAN_PERIOD, true);
    }

    public void setIsBackGround(boolean isBackGround) {
        // 熄屏时，可以使用一个boolean值来控制蓝牙扫描的功率降低为平衡(中等)扫描功率
        if (this._shitScanner instanceof CycledLeScannerForAndroidO1) {
            ((CycledLeScannerForAndroidO1) this._shitScanner).setIsBackGroundFlag(isBackGround);
        }
    }

    /**
     * 启动蓝牙扫描程序
     * @param commandResult
     * @return
     */
    public int start(CommandResult commandResult) {
        if (commandResult != null && isStart == true) {
            // DMap的startBeacon调用start()时，如果本来已经开启扫描，就不重复关闭和开启，直接返回success
            return 0;
        }
        if (isStarting == false) {
            isStarting = true;
        } else {
            return -1;
        }
        //启动前先停止
        LogUtil.w(TAG,"beacon start方法调用触发了stop方法的调用");
        stop(null);
        //检查蓝牙功能是否开启，如果没有则主动开启蓝牙扫描，终止当前此启动操作，待蓝牙功能成功启动后再次触发
        if (!checkBeaconOpen()) {
            startBeaconToOpenBeacon();
            isStarting = false;
            isStart = false;
            return -2;
        }
        addBeaconsListener(commandResult);
        return 0;
    }

    /**
     * 调用第三方库CycledLeScanner，进行蓝牙信标扫描
     * 开启扫描 获取信标信号
     * @param commandResult
     */
    private void addBeaconsListener(CommandResult commandResult) {
        LogUtil.w(TAG,"beacon start方法调用");
        LogUtil.w(TAG,"==================");
        _shitScanner.start();

        try {
            if (_signalCheckTimer != null) {
                _signalCheckTimer.cancel();
                _signalCheckTimer = null;
            }else {
            _signalCheckTimer = new Timer();
            _signalCheckTimer.schedule(new SignalCheckTask(), 0, SIGNAL_INTERVAL);
            }
            isStart = true;
        }catch (Exception e){
            Log.e("DBleScanner","定时任务强制退出");
        }

        if (commandResult == null) {
            isStarting = false;
            return;
        }
        isStarting = false;
    }

    /**
     * 检查蓝牙是否开启
     * @return
     */
    private boolean checkBeaconOpen() {
        // 检查蓝牙是否开启
        LogUtil.w(TAG,"checkBeaconOpen");
        BluetoothManager bluetoothManager = (BluetoothManager) _context.getSystemService(Context.BLUETOOTH_SERVICE);
        if (bluetoothManager == null) {
            LogUtil.w(TAG,"不支持蓝牙");
            return false;
        }
        BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                return false;
            } else {
                LogUtil.w(TAG,"蓝牙本来已经打开");
                return true;
            }
        } else {
            LogUtil.w(TAG,"mBluetoothAdapter == null");
        }
        return false;
    }

    /**
     * 停止扫描
     * @param commandResult
     */
    public void stop(CommandResult commandResult) {
        LogUtil.w(TAG,"beacon stop方法调用");
        if (commandResult != null) {
            EventBus.getDefault().post(new OperatingController(Constants.stopBeaconSearchSuccessFlag)); // emit到MainActivity
            return;
        }
        _shitScanner.stop();
        if (_signalCheckTimer != null) {
            _signalCheckTimer.cancel();
            _signalCheckTimer = null;
            _signalStatus = 0;
        }
        isStart = false;
    }


    public void destroy() {
        this._context.unregisterReceiver(_myBtStatusReceive);
        stop(null);
        _shitScanner.destroy();
    }

    /**
     * 指定的信标信息监听器，如果设置了，当扫描到的信标信息封装好后，会调用用它进行广播
     */
    private OnBeaconUpdateListener onBeaconListener = null;

    /**
     * 设置信标信息广播器
     * @param OnBeaconListener
     */
    public void setOnBeaconUpdateListener(OnBeaconUpdateListener OnBeaconListener) {
        this.onBeaconListener = OnBeaconListener;
    }
    public void unsetOnBeaconUpdateListener() {
        this.onBeaconListener = null;
    }

    /**
     * 解析封装信标信息，并进行广播
     * @param scanRecord
     * @param rssi
     */
    private void processBleData(byte[] scanRecord, int rssi,Integer pow) {
        // 解析获取的蓝牙数据
        if (scanRecord == null)
            return;

        synchronized (DBleScanner.this) {
            if (_signalStatus == 0) {
                EventBus.getDefault().post(new SignalStatus(0, 1));
                _signalStatus = 1;
            }
        }

        if (scanRecord.length < 30)
            return;

        int index = 4;
        // skip 4 bytes
        // check ibeacon prefix data fixed
        if (scanRecord[index] == 87 || scanRecord[index] == 57) {
            int xx = 0;
            xx++;
        }


        if (scanRecord[index] != -1
                || scanRecord[index + 1] != 76
                || scanRecord[index + 2] != 0
                || scanRecord[index + 3] != 2
                || scanRecord[index + 4] != 21) {
            int dd = 0;
            dd++;
            return;
        }
        boolean buuid = true;

        int n1 = scanRecord[index + 21] & 0x0FF;
        int n2 = scanRecord[index + 22] & 0x0FF;
        int n3 = scanRecord[index + 23] & 0x0FF;
        int n4 = scanRecord[index + 24] & 0x0FF;
        int batt = scanRecord[index + 25];

        int nminor = n3 * 256 + n4;
        int nmajor = n1 * 256 + n2;

        String strMajor = String.format("%d", nmajor);
        String strMinor = String.format("%d", nminor);

        this._lastSignalTime = System.currentTimeMillis();
        synchronized (DBleScanner.this) {
            Beacon beacon1 = new Beacon(strMajor, strMinor, rssi);
            if(pow!=null){
                beacon1.setPow(pow);
            }
            //将封装好的信标信息进行广播
            if (onBeaconListener != null) {
                onBeaconListener.onBeaconUpdate(beacon1);
            } else {
                EventBus.getDefault().post(beacon1);
            }
        }
    }

    /**
     * 监听手机的蓝牙状态变化, 状态变化广播器
     */
    private BroadcastReceiver _myBtStatusReceive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == BluetoothAdapter.ACTION_STATE_CHANGED) {
                int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                switch (blueState) {
                    case BluetoothAdapter.STATE_ON:
                        EventBus.getDefault().post(new BeaconStatus(0, 1));
                        blueOpenHandler.removeMessages(0);
                        break;
                    case BluetoothAdapter.STATE_OFF:
                        EventBus.getDefault().post(new BeaconStatus(1, 0));
                        blueOpenHandler.sendEmptyMessageDelayed(0, 2000);
                        break;
                    default:
                        break;
                }
            }
        }
    };

    /**
     * 触发强制开启蓝牙的定时器
     */
    private void startBeaconToOpenBeacon() {
        // 触发强制开启蓝牙的定时器
        blueOpenHandler.removeMessages(0);
        blueOpenHandler.sendEmptyMessageDelayed(0, 1000);
    }

    /**
     * 强制开启蓝牙功能的定时器
     */
    private Handler blueOpenHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            openBlueAdapter();
            return true;
        }
    });

    /**
     * 判断蓝牙是否启动
     */
    private void openBlueAdapter() {
        // 强制开启蓝牙
        BluetoothManager bluetoothManager = (BluetoothManager) _context.getSystemService(Context.BLUETOOTH_SERVICE);
        if (bluetoothManager == null) {
            return;
        }
        BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();
        if (!mBluetoothAdapter.enable()) {
           LogUtil.w(TAG,"蓝牙未启用");
        } else {
            LogUtil.w(TAG,"蓝牙启用");
        }
    }

    /**
     * 检查蓝牙信号，当开启了蓝牙，但是没收到蓝牙数据时(比如蓝牙内部发生错误)的信号变化
     * 记录信标时间  间隔大于SIGNAL_INTERVAL时  通知EventBus 更改状态
     */
    class SignalCheckTask extends TimerTask {
        public void run() {
            if (_lastSignalTime == 0) {
                _lastSignalTime = System.currentTimeMillis();
            }

            long time = System.currentTimeMillis();
            synchronized (DBleScanner.this) {
                if (time - _lastSignalTime > SIGNAL_INTERVAL) {
                    if (_signalStatus == 1) {
                        EventBus.getDefault().post(new SignalStatus(1, 0));
                        _signalStatus = 0;
                    }

                    synchronized (DBleScanner.this) {
                        _lastSignalTime = System.currentTimeMillis();
                    }
                }
            }

        }
    }

}
