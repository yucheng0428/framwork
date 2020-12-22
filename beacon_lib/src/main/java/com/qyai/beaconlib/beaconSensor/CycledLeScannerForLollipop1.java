package com.qyai.beaconlib.beaconSensor;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.qyai.beaconlib.Interface.PowCallBack;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.logging.LogManager;
import org.altbeacon.beacon.service.DetectionTracker;
import org.altbeacon.beacon.service.scanner.CycledLeScanner;
import org.altbeacon.beacon.service.scanner.ScanFilterUtils;
import org.altbeacon.bluetooth.BluetoothCrashResolver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CycledLeScannerForLollipop1 extends CycledLeScanner {
    private static String TAG = "CycledLeScannerForLollipop1";
    private static final long BACKGROUND_L_SCAN_DETECTION_PERIOD_MILLIS = 10000L;
    private BluetoothLeScanner mScanner;
    private ScanCallback leScanCallback;
    private long mBackgroundLScanStartTime = 0L;
    private long mBackgroundLScanFirstDetectionTime = 0L;
    private boolean mMainScanCycleActive = false;
    private final BeaconManager mBeaconManager;
    private final PowerManager mPowerManager;
    private boolean isBackground = true;
    private PowCallBack callBack;
    public CycledLeScannerForLollipop1(Context context, long scanPeriod, long betweenScanPeriod, boolean backgroundFlag, PowCallBack cycledLeScanCallback, BluetoothCrashResolver crashResolver) {
        super(context, scanPeriod, betweenScanPeriod, backgroundFlag, cycledLeScanCallback, crashResolver);
        this.mBeaconManager = BeaconManager.getInstanceForApplication(this.mContext);
        this.mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        this.callBack=cycledLeScanCallback;
    }
    public CycledLeScannerForLollipop1(Context context, long scanPeriod, long betweenScanPeriod, boolean backgroundFlag, PowCallBack cycledLeScanCallback, BluetoothCrashResolver crashResolver,PowCallBack callBack) {
        super(context, scanPeriod, betweenScanPeriod, backgroundFlag, cycledLeScanCallback, crashResolver);
        this.mBeaconManager = BeaconManager.getInstanceForApplication(this.mContext);
        this.mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        this.callBack=cycledLeScanCallback;
    }

    protected void stopScan() {
        this.postStopLeScan();
    }

    protected boolean deferScanIfNeeded() {
        long millisecondsUntilStart = this.mNextScanCycleStartTime - SystemClock.elapsedRealtime();
        boolean deferScan = millisecondsUntilStart > 0L;
        boolean scanActiveBefore = this.mMainScanCycleActive;
        this.mMainScanCycleActive = !deferScan;
        if (deferScan) {
            long secsSinceLastDetection = SystemClock.elapsedRealtime() - DetectionTracker.getInstance().getLastDetectionTime();
            if (scanActiveBefore) {
                if (secsSinceLastDetection > 10000L) {
                    this.mBackgroundLScanStartTime = SystemClock.elapsedRealtime();
                    this.mBackgroundLScanFirstDetectionTime = 0L;
                    LogManager.d(TAG, "This is Android L. Preparing to do a filtered scan for the background.");
                    if (this.mBetweenScanPeriod > 6000L) {
                        this.startScan();
                    } else {
                        LogManager.d(TAG, "Suppressing scan between cycles because the between scan cycle is too short.");
                    }
                } else {
                    LogManager.d(TAG, "This is Android L, but we last saw a beacon only %s ago, so we will not keep scanning in background.", new Object[]{secsSinceLastDetection});
                }
            }

            if (this.mBackgroundLScanStartTime > 0L && DetectionTracker.getInstance().getLastDetectionTime() > this.mBackgroundLScanStartTime) {
                if (this.mBackgroundLScanFirstDetectionTime == 0L) {
                    this.mBackgroundLScanFirstDetectionTime = DetectionTracker.getInstance().getLastDetectionTime();
                }

                if (SystemClock.elapsedRealtime() - this.mBackgroundLScanFirstDetectionTime >= 10000L) {
                    LogManager.d(TAG, "We've been detecting for a bit.  Stopping Android L background scanning");
                    this.stopScan();
                    this.mBackgroundLScanStartTime = 0L;
                } else {
                    LogManager.d(TAG, "Delivering Android L background scanning results");
                    this.mCycledLeScanCallback.onCycleEnd();
                }
            }

            LogManager.d(TAG, "Waiting to start full Bluetooth scan for another %s milliseconds", new Object[]{millisecondsUntilStart});
            if (scanActiveBefore && this.mBackgroundFlag) {
                this.setWakeUpAlarm();
            }

            this.mHandler.postDelayed(new Runnable() {
                @MainThread
                public void run() {
                    CycledLeScannerForLollipop1.this.scanLeDevice(true);
                }
            }, millisecondsUntilStart > 1000L ? 1000L : millisecondsUntilStart);
        } else if (this.mBackgroundLScanStartTime > 0L) {
            this.stopScan();
            this.mBackgroundLScanStartTime = 0L;
        }

        return deferScan;
    }

    public void setIsBackGroundFlag(boolean flag) {
        Log.d(TAG, "setIsBackGroundFlag is:" + flag);
        this.isBackground = flag;
    }

    protected void startScan() {
        if (!this.isBluetoothOn()) {
            Log.d(TAG, "Not starting scan because bluetooth is off");
        } else {
            List<ScanFilter> filters = new ArrayList();
            ScanSettings settings = null;
            if (!this.mMainScanCycleActive) {
                Log.d(TAG, "starting a scan in SCAN_MODE_LOW_POWER");
                settings = (new ScanSettings.Builder()).setScanMode(0).build();
                filters = (new ScanFilterUtils()).createScanFiltersForBeaconParsers(this.mBeaconManager.getBeaconParsers());
            } else {
                // 降低功率为中等功率扫描模式，因为现实测试发现，不能持续一直扫描，比如10秒会有1.2秒的时间突然间获取不到蓝牙数据，因此还是保留之前的高等功率扫描模式
//                if(this.isBackground) {
//                    Log.d(TAG, "starting a scan in SCAN_MODE_BALANCE");
//                    settings = (new ScanSettings.Builder()).setScanMode(1).build();
//                } else {
                Log.d(TAG, "starting a scan in SCAN_MODE_LOW_LATENCY");
                settings = (new ScanSettings.Builder()).setScanMode(2).build();
//                }

                if (VERSION.SDK_INT >= 27) {
                    if (Build.MANUFACTURER.equalsIgnoreCase("samsung") && !this.mPowerManager.isInteractive()) {
                        Log.d(TAG, "Using a non-empty scan filter since this is Samsung 8.1+");
                        filters = (new ScanFilterUtils()).createScanFiltersForBeaconParsers(this.mBeaconManager.getBeaconParsers());
                    } else {
                        Log.d(TAG, "Using an empty scan filter since this is 8.1+ on Non-Samsung");
                        filters = (new ScanFilterUtils()).createWildcardScanFilters();
                    }
                } else {
                    Log.d(TAG, "Using no scan filter since this is pre-8.1");
                    //兼容8.1版本 不然会报错
                    filters = (new ScanFilterUtils()).createScanFiltersForBeaconParsers(this.mBeaconManager.getBeaconParsers());
                }
            }

            if (settings != null) {
                this.postStartLeScan((List) filters, settings);
            }

        }
    }

    protected void finishScan() {
        Log.d(TAG, "Stopping scan");
        this.stopScan();
        this.mScanningPaused = true;
    }

    private void postStartLeScan(final List<ScanFilter> filters, final ScanSettings settings) {
        final BluetoothLeScanner scanner = this.getScanner();
        if (scanner != null) {
            final ScanCallback scanCallback = this.getNewLeScanCallback(this);
            this.mScanHandler.removeCallbacksAndMessages((Object) null);
            this.mScanHandler.post(new Runnable() {
                @WorkerThread
                public void run() {
                    try {
                        scanner.startScan(filters, settings, scanCallback);
                    } catch (IllegalStateException var2) {
                        Log.w(TAG, "Cannot start scan. Bluetooth may be turned off." + var2.toString());
                    } catch (NullPointerException var3) {
                        Log.e(TAG, "Cannot start scan. Unexpected NPE." + var3.toString());
                    } catch (SecurityException var4) {
                        Log.e(TAG, "Cannot start scan.  Security Exception" + var4.toString());
                    }

                }
            });
        }
    }

    private void postStopLeScan() {
        if (!this.isBluetoothOn()) {
            Log.d(TAG, "Not stopping scan because bluetooth is off");
        } else {
            final BluetoothLeScanner scanner = this.getScanner();
            if (scanner != null) {
                final ScanCallback scanCallback = this.getNewLeScanCallback(this);
                this.mScanHandler.removeCallbacksAndMessages((Object) null);
                this.mScanHandler.post(new Runnable() {
                    @WorkerThread
                    public void run() {
                        try {
                            Log.d(TAG, "Stopping LE scan on scan handler");
                            scanner.stopScan(scanCallback);
                        } catch (IllegalStateException var2) {
                            Log.w(TAG, "Cannot stop scan. Bluetooth may be turned off." + var2.toString());
                        } catch (NullPointerException var3) {
                            Log.e(TAG, "Cannot stop scan. Unexpected NPE." + var3.toString());
                        } catch (SecurityException var4) {
                            Log.e(TAG, "Cannot stop scan.  Security Exception" + var4.toString());
                        }

                    }
                });
            }
        }
    }

    private boolean isBluetoothOn() {
        try {
            BluetoothAdapter bluetoothAdapter = this.getBluetoothAdapter();
            if (bluetoothAdapter != null) {
                return bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON;
            }

            Log.w(TAG, "Cannot get bluetooth adapter");
        } catch (SecurityException var2) {
            Log.w(TAG, "SecurityException checking if bluetooth is on");
        }

        return false;
    }

    private BluetoothLeScanner getScanner() {
        try {
            if (this.mScanner == null) {
                Log.d(TAG, "Making new Android L scanner");
                BluetoothAdapter bluetoothAdapter = this.getBluetoothAdapter();
                if (bluetoothAdapter != null) {
                    this.mScanner = this.getBluetoothAdapter().getBluetoothLeScanner();
                }

                if (this.mScanner == null) {
                    Log.w(TAG, "Failed to make new Android L scanner");
                }
            }
        } catch (SecurityException var2) {
            Log.w(TAG, "SecurityException making new Android L scanner");
        }

        return this.mScanner;
    }

    private int count = 0;

    private ScanCallback getNewLeScanCallback(final CycledLeScannerForLollipop1 parentMain) {

        if (this.leScanCallback == null) {
            this.leScanCallback = new ScanCallback() {
                @MainThread
                public void onScanResult(int callbackType, ScanResult scanResult) {
                    callBack.onLeScan(scanResult.getDevice(), scanResult.getRssi(), scanResult.getScanRecord().getBytes());
                    callBack.powValue(scanResult);
                    if (CycledLeScannerForLollipop1.this.mBackgroundLScanStartTime > 0L) {
                        Log.d(TAG, "got a filtered scan result in the background.");
                    }
                      count=0;
                }

                @MainThread
                public void onBatchScanResults(List<ScanResult> results) {
                    Log.d(TAG, "got batch records");
                    count=0;
                    Iterator var2 = results.iterator();

                    while (var2.hasNext()) {
                        ScanResult scanResult = (ScanResult) var2.next();
                        callBack.onLeScan(scanResult.getDevice(), scanResult.getRssi(), scanResult.getScanRecord().getBytes());
                        callBack.powValue(scanResult);
                    }

                    if (CycledLeScannerForLollipop1.this.mBackgroundLScanStartTime > 0L) {
                        Log.d(TAG, "got a filtered batch scan result in the background.");
                    }

                }

                @MainThread
                public void onScanFailed(int errorCode) {
                    Intent intent = new Intent("onScanFailed");
                    intent.putExtra("errorCode", errorCode);
                    LocalBroadcastManager.getInstance(CycledLeScannerForLollipop1.this.mContext).sendBroadcast(intent);
                    count++;
                    switch (errorCode) {
                        case 1:
                            Log.e(TAG, "Scan failed: a BLE scan with the same settings is already started by the app");
                            break;
                        case 2:
                            Log.e(TAG, "Scan failed: app cannot be registered");
                            break;
                        case 3:
                            Log.e(TAG, "Scan failed: internal error");
                            break;
                        case 4:
                            Log.e(TAG, "Scan failed: power optimized scan feature is not supported");
                            break;
                        default:
                            Log.e(TAG, "Scan failed with unknown error (errorCode=" + errorCode + ")");
                    }
                    //进行蓝牙功能的停用。重试
                    if (count > 10) {
                        BluetoothAdapter bluetoothAdapter = parentMain.getBluetoothAdapter();
                        bluetoothAdapter.disable();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {

                        }
                    }
                }
            };
        }

        return this.leScanCallback;
    }
}
