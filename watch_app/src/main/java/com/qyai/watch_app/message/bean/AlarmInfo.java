package com.qyai.watch_app.message.bean;

import com.lib.common.baseUtils.DateUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AlarmInfo implements Serializable {
    String title;
    String time;
    String tyep;
    String imageUrl;

    public AlarmInfo(String title, String time, String tyep, String imageUrl) {
        this.title = title;
        this.time = time;
        this.tyep = tyep;
        this.imageUrl = imageUrl;
    }

    public AlarmInfo() {
    }

    public AlarmInfo(String title, String time, String tyep) {
        this.title = title;
        this.time = time;
        this.tyep = tyep;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTyep() {
        return tyep;
    }

    public void setTyep(String tyep) {
        this.tyep = tyep;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static List<AlarmInfo> getContactsInfoList() {
        List<AlarmInfo> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(new AlarmInfo("xxx离开了安全区，快去土城药店PK，打了爆装备", DateUtils.getCurrentTime_Today(), "1"));
        }
        return list;
    }
}
