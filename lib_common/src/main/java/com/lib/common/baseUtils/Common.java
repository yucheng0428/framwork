package com.lib.common.baseUtils;

import android.Manifest;

/**
 * Created by yucheng on 2017-10-31.
 */

public interface Common {

    /**
     * 帮助文档
     */
    String WEBVIEW_LOADURL = "webview_url";

    String WEBVIEW_TITLE = "webview_title";

    String WEBVIEW_ADVERTISEMENT = "webview_open";
    // 日志
    public static boolean LOGD_ENABLE = true;
    //是否打印到sd卡上
    public static boolean LOG_SDCARD_ENABLE = true;

    public static String LOG_FILE_NAME = "superEnntrance/log.txt";



    int PERMISSIONS_REQUEST = 199;//权限获取返回
    int DECODE = 1;
    int DECODE_FAILED = 2;
    int DECODE_SUCCEEDED = 3;
    int QUIT = 5;
    int RESTART_PREVIEW = 6;
    int RETURN_SCAN_RESULT = 7;
    int FLASH_OPEN = 8;
    int FLASH_CLOSE = 9;
    int REQUEST_IMAGE = 10;
    String INTENT_ZXING_CONFIG = "coded";


    int OVERLAY_PERMISSION_REQ_CODE = 111;
    String CODED_CONTENT =  "codedContent";
    String APP_KEY = "appkey";
    int REQ_LIST =1001 ;
    int PAGE_SIZE =10 ;

    public static final String[] permissionList = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
//            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,

    };
    int ADD_PIC = 102;
}
