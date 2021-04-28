package com.lib.common.baseUtils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by yucheng on 2017-10-31.
 */

public class Common {

    public static final String CAMERA_TEMP_PATH = "camera_temp_path";
    public static String STORAGE_PICTURE = "/pic";// 相关图片存放路径
    public static String STORAGE_VIDEO = "/video";// 相关视频存放路径
    public static String STORAGE_FILE = "/file";// 相关文件存放路径

    public static final String DATE_PATTERN_Y_M_D = "yyyy-MM-dd";//日期类型-年月日
    public static final String DATE_PATTERN_Y_M_D_H = "yyyy-MM-dd HH:00:00";//日期类型-年月日时分

    /**
     * Aouter跳转 路径
     */
    public final static String LOGIN_PATH = "/login/loginView";//登录页
    public final static String REGISTER_VIEW = "/register/view";//注册页
    public final static String FORGET_VIEW = "/forget/view";//忘记密码页
    public final static String CHANGER_PWS = "/changepsw/view";//修改密码
    public final static String ABOUT_VIEW = "/about/view";//关于页面
    public final static String MAP_LOCTION = "/map/lcation";//地图定位
    public final static String NAVI_MAP = "/navimap/view";//导航地图
    public final static String HOME_VIEW = "/home/homeView";//首页
    public final static String ADD_ENCLOSURE = "/map/AddEnclosure";//添加围栏
    public final static String MAP_POSTION="/map/PostionActivity";//围栏位置信息


    public final static int viewType = 2;
    public final static String USER_DATA = "user_data";
    public final static String USER_TOKEN = "user_tonken";
    public final static String USER_ID = "user_id";
    public final static String USER_NAME = "userName";
    public final static String USER_PASSWORD = "userpsw";
    public final static String BRACELET_MAC = "bracelet_mac";
    public final static String VIEWTYPE = "viewType";
    public final static String JUSCLASSRESULT = "JusClassResult";
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

    public final static String CATCH_CODE = "601";
    public final static int REQUEST_CODE = 10001;
    public final static int REQUEST_PERMISSION = 11111;
    public final static int DECODE = 1;
    public final static int DECODE_FAILED = 2;
    public final static int DECODE_SUCCEEDED = 3;
    public final static int QUIT = 5;
    public final static int RESTART_PREVIEW = 6;
    public final static int RETURN_SCAN_RESULT = 7;
    public final static int FLASH_OPEN = 8;
    public final static int FLASH_CLOSE = 9;
    public final static int REQUEST_IMAGE = 10;
    public final static int CHECK_SUCCESSFUL = 15;
    public final static String INTENT_ZXING_CONFIG = "coded";

    public final static int OVERLAY_PERMISSION_REQ_CODE = 16;
    public final static String CODED_CONTENT = "codedContent";
    public final static String APP_KEY = "appkey";
    public final static int REQ_LIST = 1001;
    public final static int PAGE_SIZE = 10;
    public static final int GPS_REQUEST_CODE = 10101;


    public static final String[] permissionList1 = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH_ADMIN

    };
    public static final String[] permissionList = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH_ADMIN

    };

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

    public static void initARouter(Application application) {
        if (Constants.isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application); // 尽可能早，推荐在Application中初始化
    }

    public static void destroyARouter() {
        ARouter.getInstance().destroy();
    }
}
