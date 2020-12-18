package com.lib.common.baseUtils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by yucheng on 2017-10-31.
 */

public class Common {
    public final static String USER_DATA = "user_data";
    public final static String USER_TOKEN = "user_tonken";
    public final static String USER_NAME = "userName";
    public final static String USER_PASSWORD = "userpsw";
    public final static String BRACELET_MAC = "bracelet_mac";
    public final static String VIEWTYPE = "viewType";
    /**
     * 帮助文档
     */
    public final static String WEBVIEW_LOADURL = "webview_url";

    public final static String WEBVIEW_TITLE = "webview_title";

    public final static String WEBVIEW_ADVERTISEMENT = "webview_open";
    // 日志
    public static boolean LOGD_ENABLE = true;
    //是否打印到sd卡上
    public static boolean LOG_SDCARD_ENABLE = true;

    public static String LOG_FILE_NAME = "superEnntrance/log.txt";


    public final static int PERMISSIONS_REQUEST = 199;//权限获取返回
    public final static int DECODE = 1;
    public final static int DECODE_FAILED = 2;
    public final static int DECODE_SUCCEEDED = 3;
    public final static int QUIT = 5;
    public final static int RESTART_PREVIEW = 6;
    public final static int RETURN_SCAN_RESULT = 7;
    public final static int FLASH_OPEN = 8;
    public final static int FLASH_CLOSE = 9;
    public final static int REQUEST_IMAGE = 10;
    public final static String INTENT_ZXING_CONFIG = "coded";


    public final static int OVERLAY_PERMISSION_REQ_CODE = 111;
    public final static String CODED_CONTENT = "codedContent";
    public final static String APP_KEY = "appkey";
    public final static int REQ_LIST = 1001;
    public final static int PAGE_SIZE = 10;
    public static final int GPS_REQUEST_CODE = 13222;
    public static final String[] permissionList1 = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
//            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH_ADMIN

    };
    public static final String[] permissionList = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
//            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,

    };
    int ADD_PIC = 102;

    //打开GPS 设置
    public static void openGPSSEtting(final Activity activity) {
        if (checkGpsIsOpen(activity)) {
//            Toast.makeText(activity, "GPS定位已打开", Toast.LENGTH_SHORT).show();
        } else {
            new AlertDialog.Builder(activity).setTitle("打开GPS")
                    .setMessage("需要您打开GPS定位")
                    //  取消选项
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {

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
                            activity.startActivityForResult(intent, GPS_REQUEST_CODE);
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


}
