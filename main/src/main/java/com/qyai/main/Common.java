package com.qyai.main;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.widget.Toast;

public class Common {

    public final static String USER_DATA="user_data";
    public final static String USER_TOKEN="user_tonken";
    public final static String USER_NAME="userName";
    public final static String USER_PASSWORD="userpsw";
    public final static String BRACELET_MAC="bracelet_mac";
    public final static String REQ_URL_IP="http://124.71.150.36:16805/";
    public final static String UPLOADBRACELETINFO=REQ_URL_IP+"sign/insertSign";
    public final static String UPLOADLOCATION=REQ_URL_IP+"traceBack/insertTraceBack";

    public static final int GPS_REQUEST_CODE=13222;

    //打开GPS 设置
    public static void openGPSSEtting(Activity activity) {
        if (checkGpsIsOpen(activity)){
//            Toast.makeText(activity, "GPS定位已打开", Toast.LENGTH_SHORT).show();
        }else {
            new AlertDialog.Builder(activity).setTitle("打开GPS")
                    .setMessage("需要您打开GPS定位")
                    //  取消选项
                    .setNegativeButton("取消",new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(activity, "GPS未打开，定位功能会有影响", Toast.LENGTH_SHORT).show();
                            // 关闭dialog
                            dialogInterface.dismiss();
                        }
                    })
                    //  确认选项
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //跳转到手机原生设置页面
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            activity.startActivityForResult(intent,GPS_REQUEST_CODE);
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
    }
    //判断GPS是否打开
    private static boolean checkGpsIsOpen(Context context) {
        boolean isOpen;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isOpen;
    }


    public static final String[] permissionList = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
//            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH_ADMIN

    };
}
