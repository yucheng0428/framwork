package com.qyai.beaconlib.utlis;

public class Constants {
    public static boolean isDebug = true;
    public static long BEACON_SCAN_TIME = 10000L;
    public static long BEACON_BETWEEN_SCAN_PERIOD = 2L;
    public static final int REQUEST_CODE = 10001;
    public static final int NOTICE_ID = 10001;
    public static final int stopBeaconSearchSuccessFlag = 10;
    public static String REQUSET_IP;
    public static String UPLOADBEACON;
    public static String ADDBLUETOOTHINFO;//新增蓝牙信标信息
    public static String DELECTBLUETOOTHINFO;//删除蓝牙信标
    public static String QUERYBLUETOOTHINFO;//查询蓝牙信标信息
    public static String UPDATEBLUETOOTHINFO;//修改蓝牙信标信息
    public static final int alarmIsComing = 26;
    public static final int onMainAtyAlarmIsComing = 27;
    public static final String NOTIFICATION_BEACON = "蓝牙连接中断！请亮屏同意MapDemo的蓝牙打开申请！";
    public static final String NOTIFICATION_ALARM = "有警情消息！请点击信息列表查看";
    public static String MAP_URL = "file:///android_asset/index.html";//本地路劲
    public static String LOGIN_URL;
    public static final String RESULT_SUCCESS ="000000" ;
    static {
        if (isDebug) {
            /**
             * 测试服务器
             */
            REQUSET_IP="http://192.168.5.218:16800/";
            LOGIN_URL=REQUSET_IP+"login/login";
            UPLOADBEACON=REQUSET_IP+"beacon/receivePosition";
        } else {
            /**
             * 服务器
             */
            REQUSET_IP="http://cjrk.hbcic.net.cn";
        }
    }
}
