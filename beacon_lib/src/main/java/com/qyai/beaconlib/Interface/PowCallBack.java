package com.qyai.beaconlib.Interface;

import android.bluetooth.le.ScanResult;

import org.altbeacon.beacon.service.scanner.CycledLeScanCallback;

public interface PowCallBack extends CycledLeScanCallback {
    void powValue(ScanResult pow);
}
