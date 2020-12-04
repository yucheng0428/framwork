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

    public MessageBean(String content, String titleMsg, String typeValue) {
        this.content = content;
        this.titleMsg = titleMsg;
        this.typeValue = typeValue;
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
        list.add(new MessageBean("蓝牙连接状态","奥术大师大所大所",1+""));
        list.add(new MessageBean("数据同步","奥术大师大所大所大所多",2+""));
        list.add(new MessageBean("血压","阿斯达苏打水大所大所多",2+""));
        list.add(new MessageBean("体温","自行车自行车自行车自行车自行车",1+""));
        list.add(new MessageBean("血氧","自行车在西安期望的期望的期望",1+""));
        list.add(new MessageBean("心率","房产税擦拭擦拭擦拭擦擦上传",2+""));
        list.add(new MessageBean("呼吸率","阿斯顿撒大所大所多撒大所大所多",1+""));
        list.add(new MessageBean("断开设备","奥术大师大所大所大所大所多",2+""));
        return  list;
    }
}
