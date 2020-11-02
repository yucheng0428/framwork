package com.qyai.beaconlib.beaconSensor;

import android.content.Context;

import com.qyai.beaconlib.Interface.PowCallBack;

import org.altbeacon.bluetooth.BluetoothCrashResolver;


public class CycledLeScannerForAndroidO1 extends CycledLeScannerForLollipop1 {
    public CycledLeScannerForAndroidO1(Context context, long scanPeriod, long betweenScanPeriod, boolean backgroundFlag, PowCallBack cycledLeScanCallback, BluetoothCrashResolver crashResolver) {
        super(context, scanPeriod, betweenScanPeriod, backgroundFlag, cycledLeScanCallback, crashResolver);
    }
}