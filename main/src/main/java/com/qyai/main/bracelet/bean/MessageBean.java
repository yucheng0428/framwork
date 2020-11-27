package com.qyai.main.bracelet.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MessageBean implements Serializable {
    String tyepName;
    String typeValue;
    String time;

    public MessageBean() {
    }

    public MessageBean(String tyepName, String typeValue) {
        this.tyepName = tyepName;
        this.typeValue = typeValue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTyepName() {
        return tyepName;
    }

    public void setTyepName(String tyepName) {
        this.tyepName = tyepName;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }



    public static List<MessageBean> getMessgeList(){
        List<MessageBean>list=new ArrayList<>();
        list.add(new MessageBean("蓝牙连接状态","3"));
//        list.add(new MessageBean("数据同步","未开启"));
        list.add(new MessageBean("血压","100/70"));
        list.add(new MessageBean("体温","36"));
        list.add(new MessageBean("血氧","136"));
        list.add(new MessageBean("心率","96"));
        list.add(new MessageBean("呼吸率","96"));
//        list.add(new MessageBean("断开设备","状态"));
        return  list;
    }
}
