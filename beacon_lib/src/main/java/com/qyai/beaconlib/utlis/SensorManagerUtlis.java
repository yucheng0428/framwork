package com.qyai.beaconlib.utlis;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qyai.beaconlib.bean.SensorEventBean;

import java.util.ArrayList;

/**
 * 陀螺仪获取方向工具
 */
public class SensorManagerUtlis {
    private SensorManager sensorManager;

    /**
     * 陀螺仪参数
     */
    private float[] accelerometerValues = new float[3]; //data of acclerometer sensor
    private float[] magneticFieldValues = new float[3]; //data of magnetic field sensor
    private Sensor accelerometer;
    private Sensor magneticField;
    private static final int positioningInterval = 1000;   //调定位接口间隔时间 ms
    private ArrayList<SensorEventBean> sensorEventslist = new ArrayList<>();
    public SensorManagerUtlis() {

    }
    public static SensorManagerUtlis sensorManagerUtlis;
    public static SensorManagerUtlis getInstance(){
        if(sensorManagerUtlis==null){
            sensorManagerUtlis=new SensorManagerUtlis();
        }
        return  sensorManagerUtlis;
    }

    /**
     * 陀螺仪，方向计算相关
     */
    SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accelerometerValues = sensorEvent.values;
                synchronized (sensorEventslist) {
                    long timeStamp = System.currentTimeMillis();
                    if (sensorEventslist.size() >= positioningInterval / 50) {
                        sensorEventslist.remove(0);
                    }
                    sensorEventslist.add(new SensorEventBean(accelerometerValues, timeStamp));
                }
            }
            if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                magneticFieldValues = sensorEvent.values;
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public ArrayList<SensorEventBean> getSensorEventslist(){
        return sensorEventslist;
    }
    /**
     * 在现实世界的正交坐标系中，磁北极是固定方向的。想要构造一个指南针应用，
     * 最直观的思考是获取到设备朝向和所处位置磁北极方向的夹角。这个时候，直观的思考就是正确的思考，
     * 如何获取这样一个夹角呢。通过上面介绍的 API 来看，getRotationMatrix()方法，能够获得设备的旋转矩阵和倾斜矩阵。
     * 旋转矩阵是一个33或者44的矩阵，通过getOrientation()方法就能直接获得设备关于正交系的三轴旋转数据。
     * 设备的指向必然是设备关于z轴的旋转角度。
     */
    public void initSensor(Context context) {
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//运动检测（摇动，倾斜等）,测量设备在三个物理轴(x,y,z)上的加速度（m/s²），包括重力
        magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);//创建指南针，检测沿三个物理轴(x,y,z)的磁场强度μT
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(sensorEventListener, magneticField, SensorManager.SENSOR_DELAY_UI);
    }

    /**
     * 停止监听陀螺仪
     */
    public  void stopSensor() {
        sensorManager.unregisterListener(sensorEventListener, accelerometer);
        sensorManager.unregisterListener(sensorEventListener, magneticField);
    }


    /**
     * 计算方向
     */
    public int calculateOrientation() {
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R, null, accelerometerValues,
                magneticFieldValues);
        SensorManager.getOrientation(R, values);
        values[0] = (float) Math.toDegrees(values[0]);
//        System.out.println("角度= " + values[0]);
        //0度为北 -90度为西 90度为东 180度或-180度为南
        if (values[0] >= -5 && values[0] < 5) {
//            return "北";
            return 0;
        } else if (values[0] >= 5 && values[0] < 85) {
//            return "东北";
            return 45;
        } else if (values[0] >= 85 && values[0] <= 95) {
//            return "东";
            return 90;
        } else if (values[0] >= 95 && values[0] < 175) {
//            return "东南";
            return 135;
        } else if ((values[0] >= 175 && values[0] <= 180)
                || (values[0]) >= -180 && values[0] < -175) {
//            return "南";
            return 180;
        } else if (values[0] >= -175 && values[0] < -95) {
//            return "西南";
            return 225;
        } else if (values[0] >= -95 && values[0] < -85) {
//            return "西";
            return 270;
        } else if (values[0] >= -85 && values[0] < -5) {
//            return "西北";
            return 315;
        }
        return 0;
    }

}
