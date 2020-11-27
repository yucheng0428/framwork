package com.lib.common.baseUtils;

import android.Manifest;
import android.os.Environment;

/**
 * Created by yucheng on 2017-10-31.
 */

public class Constants {

    public static final String CAMERA_TEMP_PATH ="camera_temp_path";
    public static boolean isDebug = true;
    public static boolean isShowLog = true;
    public static String STORAGE_PICTURE = "/pic";// 相关图片存放路径
    public static String STORAGE_VIDEO ="/video";// 相关视频存放路径
    public static String STORAGE_FILE ="/file";// 相关文件存放路径

    public static final String DATE_PATTERN_Y_M_D = "yyyy-MM-dd";//日期类型-年月日
    public static final String DATE_PATTERN_Y_M_D_H = "yyyy-MM-dd HH:00:00";//日期类型-年月日时分

    public final static String TAG="lib_baseUtils";
    public static final int REQUEST_CODE = 10001;

    public static final String fileUploadUrl="";
    public  static  final  String REQ_URL_IP="192.168.10.130:16810/";

    /**
     * 请求地址
     */
    public static boolean isDebug() {
        return isDebug;
    }

    public static final String[] permissionList = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH_ADMIN

    };


}
