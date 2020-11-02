package com.qyai.beaconlib.beaconSensor;

import android.content.Context;
import android.os.Build;


import com.qyai.beaconlib.Interface.PowCallBack;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.logging.LogManager;
import org.altbeacon.beacon.service.scanner.CycledLeScannerForJellyBeanMr2;
import org.altbeacon.bluetooth.BluetoothCrashResolver;

public class CycledLeScanner1 {

    public static org.altbeacon.beacon.service.scanner.CycledLeScanner createScanner(Context context, long scanPeriod, long betweenScanPeriod, boolean backgroundFlag, PowCallBack cycledLeScanCallback, BluetoothCrashResolver crashResolver) {
        boolean useAndroidLScanner = false;
        boolean useAndroidOScanner = false;
        if (Build.VERSION.SDK_INT < 18) {
            LogManager.w("CycledLeScanner", "Not supported prior to API 18.", new Object[0]);
            return null;
        } else {
            if (Build.VERSION.SDK_INT < 21) {
                LogManager.i("CycledLeScanner", "This is pre Android 5.0.  We are using old scanning APIs", new Object[0]);
                useAndroidLScanner = false;
            } else if (Build.VERSION.SDK_INT < 26) {
                if (BeaconManager.isAndroidLScanningDisabled()) {
                    LogManager.i("CycledLeScanner", "This is Android 5.0, but L scanning is disabled. We are using old scanning APIs", new Object[0]);
                    useAndroidLScanner = false;
                } else {
                    LogManager.i("CycledLeScanner", "This is Android 5.0.  We are using new scanning APIs", new Object[0]);
                    useAndroidLScanner = true;
                }
            } else {
                LogManager.i("CycledLeScanner", "Using Android O scanner", new Object[0]);
                useAndroidOScanner = true;
            }

            //根据版本判断 蓝牙扫描方式
            //这里是android-10的处理 与其他的版本处理有差异
            if (useAndroidOScanner) {
                return new CycledLeScannerForAndroidO1(context, scanPeriod, betweenScanPeriod, backgroundFlag, cycledLeScanCallback, crashResolver);
            } else {
                return (org.altbeacon.beacon.service.scanner.CycledLeScanner)(useAndroidLScanner ? new CycledLeScannerForLollipop1(context, scanPeriod, betweenScanPeriod, backgroundFlag, cycledLeScanCallback, crashResolver) : new CycledLeScannerForJellyBeanMr2(context, scanPeriod, betweenScanPeriod, backgroundFlag, cycledLeScanCallback, crashResolver));
            }
        }
    }
}
