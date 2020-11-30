package com.qyai.bracelet_lib;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.lib.common.baseUtils.LogUtil;
import com.lib.common.baseUtils.UIHelper;
import com.qyai.bracelet_lib.bean.BloodInfo;
import com.qyai.bracelet_lib.bean.DeviceBean;
import com.yucheng.ycbtsdk.Bean.ScanDeviceBean;
import com.yucheng.ycbtsdk.Constants;
import com.yucheng.ycbtsdk.Response.BleConnectResponse;
import com.yucheng.ycbtsdk.Response.BleDataResponse;
import com.yucheng.ycbtsdk.Response.BleDeviceToAppDataResponse;
import com.yucheng.ycbtsdk.Response.BleRealDataResponse;
import com.yucheng.ycbtsdk.Response.BleScanResponse;
import com.yucheng.ycbtsdk.YCBTClient;

import java.util.Calendar;
import java.util.HashMap;

/**
 * 手环API
 */
public class BraceletApi {
    final static String TAG = "BraceletApi";
    private static BraceletApi instance;

    public BraceletApi() {

    }

    public static BraceletApi getInstance() {
        if (instance == null) {
            instance = new BraceletApi();
        }
        return instance;
    }

    /**
     * 搜索手环设备
     */
    public void search(BraceletCallBack callBack) {
        YCBTClient.startScanBle(new BleScanResponse() {
            @Override
            public void onScanResponse(int code, ScanDeviceBean scanBean) {
                if (scanBean != null) {
                    DeviceBean bean = JSON.parseObject(JSON.toJSONString(scanBean), DeviceBean.class);
//                    BraceletApi.getInstance().connectBle(bean.getDeviceMac());
                    LogUtil.e(TAG, "search: " + JSON.toJSONString(bean));
                    callBack.onSuccess(bean);
                }
            }
        }, 5);
    }
    //结束蓝牙搜索 YCBTClient.stopScanBle();

    /**
     * 设备连接
     *
     * @param str 设备DeviceMac
     */
    public void connectBle(String str) {
        YCBTClient.connectBle(str, new BleConnectResponse() {
            @Override
            public void onConnectResponse(int code) {
                if (code == Constants.CODE.Code_OK) {
                    baseOrderSet();
                }
            }
        });
    }
/*******************************开关方法*************************************************/

    /**
     * heart 有效心率
     * dataResponse 0x00: 手环同步成功 0x01：手环同步失败
     *
     * @param heart
     * @param dataResponse
     */
    public static void appEffectiveHeart(int heart, BleDataResponse dataResponse) {
    }

    ;

    /**
     * 心率测试
     *
     * @param callBack
     */
    public void appEcgTestStart(BraceletCallBack callBack) {
        YCBTClient.appEcgTestStart(new BleDataResponse() {
            @Override
            public void onDataResponse(int code, float ratio, HashMap resultMap) {
                LogUtil.e(TAG, "onDataResponse: " + JSON.toJSONString(resultMap));
            }
        }, new BleRealDataResponse() {
            @Override
            public void onRealDataResponse(int dataType, HashMap dataMap) {
                LogUtil.e(TAG, "血压数据: " + dataType + "===>" + JSON.toJSONString(dataMap));
                callBack.onSuccess(dataMap);
                if (dataType == Constants.DATATYPE.Real_UploadHeart) {
                    //心率数据 dataMap
                    LogUtil.e(TAG, "心率数据: " + JSON.toJSONString(dataMap));
                } else if (dataType == Constants.DATATYPE.Real_UploadBlood) {
                    //血压数据 dataMap
                    if (dataMap != null) {
                        BloodInfo info = JSON.parseObject(JSON.toJSONString(dataMap), BloodInfo.class);
                    }
                } else if (dataType == Constants.DATATYPE.Real_UploadPPG) {
                    //实时PPG数据  dataMap
                    LogUtil.e(TAG, "实时PPG数据: " + JSON.toJSONString(dataMap));
                } else if (dataType == Constants.DATATYPE.Real_UploadECG) {
                    final byte[] tData = (byte[]) dataMap.get("data");
                    //一定要在主线程分析
                } else if (dataType == Constants.DATATYPE.Real_UploadECGHrv) {
                    float param = (float) dataMap.get("data");
                } else if (dataType == Constants.DATATYPE.Real_UploadECGRR) {
                    float param = (float) dataMap.get("data");
                }
            }
        });
    }

    /**
     * 停止ecg
     *
     * @param callBack
     */
    public void appEcgTestEnd(BraceletCallBack callBack) {
        YCBTClient.appEcgTestEnd(new BleDataResponse() {
            @Override
            public void onDataResponse(int i, float v, HashMap hashMap) {
                LogUtil.e(TAG, "onDataResponse: " + JSON.toJSONString(hashMap));
                callBack.onSuccess(hashMap);
            }
        });
    }

    /**
     * 读取手环实时数据
     *
     * @param type
     * @param callBack
     */
    public void appRealSportFromDevice(int type, BraceletCallBack callBack) {
        //读取手环实时数据 type 0x00: 关闭 0x01:开启
        YCBTClient.appRealSportFromDevice(type, new BleDataResponse() {
            @Override
            public void onDataResponse(int i, float v, HashMap hashMap) {
                LogUtil.e(TAG, i + "手环实时数据: " + JSON.toJSONString(hashMap));
                if (i == 0) {
                    callBack.onSuccess(hashMap);
                }
            }
        });
    }


    /**
     * 设备数据到App
     *
     * @param callBack
     */
    public void deviceToApp(BraceletCallBack callBack) {
        //数据到App
        YCBTClient.deviceToApp(new BleDeviceToAppDataResponse() {
            @Override
            public void onDataResponse(int i, HashMap hashMap) {
                LogUtil.e(TAG, i + "手环实时数据: " + JSON.toJSONString(hashMap));
                if (hashMap != null) {
                    callBack.onSuccess(hashMap);
                }
            }
        });
    }

    /*******************************设置方法*************************************************/


    public void settingBluetoothBroadcastInterval() {
        /**
         * 蓝牙广播间
         * time 广播间隔时间 单位ms
         *
         * dataResponse
         */
        YCBTClient.settingBluetoothBroadcastInterval(5, new BleDataResponse() {
            @Override
            public void onDataResponse(int i, float v, HashMap hashMap) {
                LogUtil.e(TAG, i + "蓝牙广播间: " + JSON.toJSONString(hashMap));
                if (i == 0) {
                    //success
                }
            }
        });
    }

    /**
     * 蓝牙发射功率
     *
     * @param m
     * @param callBack
     */
    public void settingBluetoothTransmittingPower(int m, BraceletCallBack callBack) {
        //蓝牙发射功率
        YCBTClient.settingBluetoothTransmittingPower(1, new BleDataResponse() {
            @Override
            public void onDataResponse(int i, float v, HashMap hashMap) {
                if (i == 0) {
                    //success
                    LogUtil.e(TAG, i + "蓝牙发射功率: " + JSON.toJSONString(hashMap));
                    callBack.onSuccess(hashMap);
                }
            }
        });
    }

    /**
     * param on 0x01: 开启 0x00: 关闭
     * <p>
     * param type 0x00: PPG 0x01: 加速度数据 0x02：ECG 0x03：温湿度 0x04：环境光 0x05：体温
     * <p>
     * param collectLong 每次采集时长(单位:秒) (关闭时填 0)
     * <p>
     * param collectInterval 采集间隔(单位:分钟) (关闭时填 0)
     * <p>
     * param dataResponse
     *
     * @param on
     * @param type
     * @param collectLong
     * @param collectInterval
     * @param dataResponse
     */
    public static void settingDataCollect(int on, int type, int collectLong, int collectInterval, BleDataResponse dataResponse) {

    }

    /**
     * PPG数据采集配置
     *
     * @param type 1开启 0关闭
     */
    public void settingPpgCollect(int type, BraceletCallBack callBack) {
        /**
         *  PPG数据采集配置
         *  param on 0x01: 开启 0x00: 关闭
         *
         * param collectLong 每次采集时长(单位:秒) (关闭时填 0)
         *
         * param collectInterval 采集间隔(单位:分钟) (关闭时填 0)
         *
         * param dataResponse
         */
        BleDataResponse response = new BleDataResponse() {
            @Override
            public void onDataResponse(int i, float v, HashMap hashMap) {
                String string = JSON.toJSONString(hashMap);
                LogUtil.e(TAG, i + "====>" + v + "=====>" + string);
                callBack.onSuccess(hashMap);
            }
        };
        if (type == 1) {
            YCBTClient.settingPpgCollect(0x01, 3000, 1 / 6, response);
        } else {
            YCBTClient.settingPpgCollect(0x00, 0, 0, response);
        }

    }

    public void settingHeartMonitor(BraceletCallBack callBack) {
        /****
         * 心率监测模式设置
         * @param mode 模式 0x00: 手动模式 0x01: 自动模式
         * @param intervalTime 自动模式下心率监测间隔(分)
         * @param dataResponse
         */
        YCBTClient.settingHeartMonitor(0x01, 1, new BleDataResponse() {
            @Override
            public void onDataResponse(int i, float v, HashMap hashMap) {
                LogUtil.e(TAG, JSON.toJSONString(hashMap));
                callBack.onSuccess(hashMap);
            }
        });
    }

    /**
     * 温度监测
     *
     * @param flag
     * @param callBack
     */
    public void settingTemperatureMonitor(boolean flag, BraceletCallBack callBack) {
        /**
         * 温度监测
         * param on_off true:打开 false:关闭
         *
         * param interval 间隔时间 分钟
         *
         * param dataResponse
         */
        YCBTClient.settingTemperatureMonitor(true, 1, new BleDataResponse() {
            @Override
            public void onDataResponse(int i, float v, HashMap hashMap) {
                String string = JSON.toJSONString(hashMap);
                LogUtil.e(TAG, i + "====>" + v + "=====>" + string);
                callBack.onSuccess(hashMap);
            }
        });
    }

    public void settingBloodOxygenModeMonitor(BraceletCallBack callBack) {
        /**
         * 血氧监测模式设置
         * param on_off true:打开 false:关闭
         *
         * param interval 间隔时间 分钟
         *
         * param dataResponse
         */
        YCBTClient.settingBloodOxygenModeMonitor(true, 1, new BleDataResponse() {
            @Override
            public void onDataResponse(int i, float v, HashMap hashMap) {
                Log.e("device", "血氧监测");
                callBack.onSuccess(hashMap);
            }
        });
    }

    /*******************************获取方法*************************************************/
    /**
     * 获取步数
     *
     * @param callBack
     */
    public void getElectrodeLocationInfo(BraceletCallBack callBack) {
        YCBTClient.getElectrodeLocationInfo(new BleDataResponse() {
            @Override
            public void onDataResponse(int code, float ratio, HashMap resultMap) {
                if (resultMap != null) {
                    LogUtil.e(TAG, JSON.toJSONString(resultMap));
                    callBack.onSuccess(resultMap);

                }
            }
        });
    }


    /**
     * 获取设备信息
     *
     * @param callBack
     */
    public void getDeviceInfo(BraceletCallBack callBack) {

        YCBTClient.getDeviceInfo(new BleDataResponse() {
            @Override
            public void onDataResponse(int code, float ratio, HashMap resultMap) {
                if (resultMap != null) {
                    LogUtil.e(TAG, JSON.toJSONString(resultMap));
                    callBack.onSuccess(resultMap);
                }
            }
        });
    }


    /**
     * 获取血氧信息
     *
     * @param callBack
     */
    public void getRealBloodOxygen(BraceletCallBack callBack) {
        YCBTClient.getRealBloodOxygen(new BleDataResponse() {
            @Override
            public void onDataResponse(int i, float v, HashMap hashMap) {
                if (i == 0 && hashMap != null) {
                    LogUtil.e(TAG, JSON.toJSONString(hashMap));
//                    callBack.onSuccess(hashMap);
                    int bloodOxygenIsTest = (int) hashMap.get("bloodOxygenIsTest");//0x00: 未测心氧 0x01: 正在测试心氧
                    int bloodOxygenValue = (int) hashMap.get("bloodOxygenValue");//血氧值 0-100
//                            textView.setText("未测心氧" + bloodOxygenIsTest + "\n血氧值" + bloodOxygenValue);
                }
            }
        });
    }

    /**
     * type 0x00: PPG 0x01: 加速度数据 0x02：ECG 0x03：温湿度 0x04：环境光 0x05：体温
     */
    public void getTemp() {
        YCBTClient.getSensorSamplingInfo(0x05, new BleDataResponse() {
            @Override
            public void onDataResponse(int i, float v, HashMap hashMap) {
                LogUtil.e(TAG, JSON.toJSONString(hashMap));
            }
        });
    }

    public void appRealSportFromDevice(int type) {
        /**
         * 读取手环实时数据
         * type 0x00: 关闭 0x01:开启
         *
         * dataResponse
         */
        YCBTClient.appRealSportFromDevice(type, new BleDataResponse() {
            @Override
            public void onDataResponse(int i, float v, HashMap hashMap) {
                LogUtil.e(TAG, "读取手环实时数据" + JSON.toJSONString(hashMap));
            }
        });
    }

    /**
     * 获取温度信息
     *
     * @param callBack
     */
    public void getCurrentAmbientTempAndHumidity(BraceletCallBack callBack) {
        YCBTClient.getCurrentAmbientTempAndHumidity(new BleDataResponse() {
            @Override
            public void onDataResponse(int i, float v, HashMap hashMap) {
                if (i == 0 && hashMap != null) {
                    LogUtil.e(TAG, JSON.toJSONString(hashMap));
                    callBack.onSuccess(hashMap);
                    int ambientTempAndHumidityIsTest = (int) hashMap.get("ambientTempAndHumidityIsTest");//0x00: 未测环境温湿度 0x01: 正在测试环境温湿度
                    float ambientTempValue = (float) hashMap.get("ambientTempValue");//温度
                    float ambientHumidityValue = (float) hashMap.get("ambientHumidityValue");//湿度
//                            textView.setText("湿度" + ambientHumidityValue + "\n温度" + ambientTempValue
//                                    + "\n0x00: 未测环境温湿度 0x01: 正在测试环境温湿度" + ambientTempAndHumidityIsTest
//                            );
                }
            }
        });
    }
/*******************************同步方法*************************************************/

    /**
     * dataType (步数 0x0502,睡眠0x0504,
     * 心率,0x0506,
     * 血压0x0508,
     * 0x0509同步所有的包括步数睡眠心率血压血氧hrvcvrr温度,
     * 血氧0x051A,
     * 温湿度0x051C,
     * 体温0x051E,
     * 环境光0x0520,
     * 手环脱落记录0x0529
     * 同步历史健康数据
     */
    public void syncHistoryDataAll(int type,BraceletCallBack callBack) {
        YCBTClient.healthHistoryData(type, new BleDataResponse() {
            @Override
            public void onDataResponse(int i, float v, HashMap hashMap) {
                if (hashMap != null) {
                    Log.e("history", getTpye(type) + "hashMap=" + JSON.toJSONString(hashMap));
                    callBack.onSuccess(JSON.toJSONString(hashMap));
                } else {
                    Log.e("history", "no... ..data...." + getTpye(type));
                }
            }
        });
    }

    public void syncHistoryData(int type) {
        YCBTClient.healthHistoryData(type, new BleDataResponse() {
            @Override
            public void onDataResponse(int i, float v, HashMap hashMap) {
                if (hashMap != null) {
                    Log.e("history", getTpye(type) + "hashMap=" + JSON.toJSONString(hashMap));
                } else {
                    Log.e("history", "no... ..data...." + getTpye(type));
                }
            }
        });
    }

    /**
     * (步数 0x0540,
     * 睡眠0x0541,
     * 心率,0x0542,
     * 血压0x0543,
     * 血氧和温度0x0544,
     * 血氧0x0545,
     * 温湿度0x0546,
     * 体温0x0547,
     * 环境光0x0548,
     * 手环脱落记录0x0549)
     * 删除历史健康数据
     */
    public void delectSyncHistoryData(int type) {
        YCBTClient.deleteHealthHistoryData(type, new BleDataResponse() {
            @Override
            public void onDataResponse(int i, float v, HashMap hashMap) {
                if (i == 0) {//delete success
                    Log.e("history", "历史记录删除成功");
                }
            }
        });
    }

    /***
     * 语言设置
     *   0x00:英语 0x01: 中文 0x02: 俄语 0x03: 德语 0x04: 法语
     * 0x05: 日语 0x06: 西班牙语 0x07: 意大利语 0x08: 葡萄牙文
     * 0x09: 韩文 0x0A: 波兰文 0x0B: 马来文 0x0C: 繁体中文 0xFF:其它
     * @param
     */
    //基础指令设置
    private void baseOrderSet() {


        /***
         * 语言设置
         * @param langType 0x00:英语 0x01: 中文 0x02: 俄语 0x03: 德语 0x04: 法语
         * 0x05: 日语 0x06: 西班牙语 0x07: 意大利语 0x08: 葡萄牙文
         * 0x09: 韩文 0x0A: 波兰文 0x0B: 马来文 0x0C: 繁体中文 0xFF:其它
         * @param dataResponse
         */
        YCBTClient.settingLanguage(0x01, new BleDataResponse() {
            @Override
            public void onDataResponse(int i, float v, HashMap hashMap) {
                Log.e("device", "同步语言结束");
            }
        });


        //心率采集
        settingHeartMonitor(new BraceletCallBack() {
            @Override
            public void onSuccess(Object o) {
                LogUtil.e(TAG, "心率监测模式设置" + o.toString());
            }
        });

        //无感检测
        settingPpgCollect(1, new BraceletCallBack() {
            @Override
            public void onSuccess(Object o) {
                LogUtil.e(TAG, "无感检测" + o.toString());
            }
        });

        //on 0x00:关闭 0x01: 打开 抬腕亮屏开关设置
        YCBTClient.settingRaiseScreen(0x01, new BleDataResponse() {
            @Override
            public void onDataResponse(int i, float v, HashMap hashMap) {
                Log.e("device", "抬腕亮屏开关设置");
            }
        });
        //温度监测
        settingTemperatureMonitor(true, new BraceletCallBack() {
            @Override
            public void onSuccess(Object o) {
                LogUtil.e(TAG, "温度监测" + o.toString());
            }
        });
        settingBloodOxygenModeMonitor(new BraceletCallBack() {
            @Override
            public void onSuccess(Object o) {
                LogUtil.e(TAG, "血氧监测模式" + o.toString());
            }
        });
//        syncHistoryData(0x0509);
//        //同步心率
//        syncHisHr();
//        //同步血压
//        syncHisBlood();
//        //同步血氧
//        syncHisBloodOxygen();
//        //同步体温
//        syncHisTemp();
//        //同步步数
//        syncHisStep();

    }


    private void setPhoneTime() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        int week2 = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);

        if (week == 1) {
            week += 5;
        } else {
            week -= 2;
        }


//        周 0-6(星期一~星期天)
        // 0 周一，1周二，2周三，3周四，4周五，5周六，6周日
        Log.e("device", "day of week jian=" + week);
        Log.e("device", "day of week week2=" + week2);


    }


    //同步历史心率

    /**
     * 0x0506
     * Constants.DATATYPE.Health_HistoryHeart
     */
    public void syncHisHr() {
        syncHistoryData(0x0506);
    }
    //同步历史血压

    /**
     * 0x0508
     * Constants.DATATYPE.Health_HistoryBlood
     */
    public void syncHisBlood() {
        syncHistoryData(0x0508);
    }
    //同步历史血氧

    /**
     * 0x051A
     * Constants.DATATYPE.Health_HistoryBloodOxygen
     */
    public void syncHisBloodOxygen() {
        syncHistoryData(0x051A);
    }
    //同步历史体温

    /**
     * 0x051E
     * Constants.DATATYPE.Health_HistoryTemp
     */
    public void syncHisTemp() {
        syncHistoryData(0x051E);
    }


    public boolean isOpenBlueTooth(Activity activity) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Log.e(TAG, "--------------- 不支持蓝牙");
            return false;
        }
        if (!bluetoothAdapter.isEnabled()) {
            boolean res = bluetoothAdapter.enable();
            if (res == true) {
                UIHelper.ToastMessage(activity, "蓝牙打开成功");
            } else {
                UIHelper.ToastMessage(activity, "蓝牙打开失败");
            }
            return res;
        } else if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            return true;
        } else {
            UIHelper.ToastMessage(activity, "蓝牙打开失败");
            return false;
        }
    }

    /**
     * dataType (步数 0x0502,睡眠0x0504,
     * * 心率,0x0506,
     * *  血压0x0508,
     * *  0x0509同步所有的包括步数睡眠心率血压血氧hrvcvrr温度,
     * *  血氧0x051A,
     * *  温湿度0x051C,
     * *  体温0x051E,
     * *  环境光0x0520,
     * *  手环脱落记录0x0529
     *
     * @param type
     * @return
     */
    public String getTpye(int type) {
        String string = "";
        switch (type) {
            case 0x0502:
                string = "步数";
                break;
            case 0x0504:
                string = "睡眠";
                break;
            case 0x0506:
                string = "心率";
                break;
            case 0x0508:
                string = "血压";
                break;
            case 0x0509:
                string = "同步所有的包括步数睡眠心率血压血氧hrvcvrr温度,";
                break;
            case 0x051A:
                string = "血氧";
                break;
            case 0x051C:
                string = "温湿度";
                break;
            case 0x051E:
                string = "体温";
                break;
            case 0x0520:
                string = "环境光";
                break;
            case 0x0529:
                string = "手环脱落记录";
                break;
        }
        return string;
    }
}
