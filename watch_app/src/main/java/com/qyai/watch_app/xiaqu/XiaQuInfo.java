package com.qyai.watch_app.xiaqu;

import com.lib.common.baseUtils.DateUtils;
import com.qyai.watch_app.message.bean.AlarmInfo;

import java.util.ArrayList;
import java.util.List;

public class XiaQuInfo {
    String imageUrl;
    String sex;
    String name;
    String stat;
    String phoneNo;
    String postion;

    public XiaQuInfo(String sex, String name, String stat) {
        this.sex = sex;
        this.name = name;
        this.stat = stat;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPostion() {
        return postion;
    }

    public void setPostion(String postion) {
        this.postion = postion;
    }

    public static List<XiaQuInfo> getContactsInfoList() {
        List<XiaQuInfo> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            if (i % 2 == 0) {
                list.add(new XiaQuInfo("男", "大傻" + i, "2"));
            } else {
                list.add(new XiaQuInfo("男", "大傻" + i, "1"));
            }
        }
        return list;
    }
}
