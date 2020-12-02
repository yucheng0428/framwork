package com.qyai.watch_app.message.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MessageBean implements Serializable {
    String content;
    String titleMsg;
    String typeValue;
    String time;
    String img_str;

    public MessageBean() {
    }

    public MessageBean(String content, String titleMsg) {
        this.content = content;
        this.titleMsg = titleMsg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitleMsg() {
        return titleMsg;
    }

    public void setTitleMsg(String titleMsg) {
        this.titleMsg = titleMsg;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getImg_str() {
        return img_str;
    }

    public void setImg_str(String img_str) {
        this.img_str = img_str;
    }

    public static List<MessageBean> getMessgeList(){
        List<MessageBean>list=new ArrayList<>();
        list.add(new MessageBean("蓝牙连接状态","3"));
        list.add(new MessageBean("数据同步","未开启"));
        list.add(new MessageBean("血压","100/70"));
        list.add(new MessageBean("体温","36"));
        list.add(new MessageBean("血氧","136"));
        list.add(new MessageBean("心率","96"));
        list.add(new MessageBean("呼吸率","96"));
        list.add(new MessageBean("断开设备","状态"));
        return  list;
    }
}
