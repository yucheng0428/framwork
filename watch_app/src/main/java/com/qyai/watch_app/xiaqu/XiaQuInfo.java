package com.qyai.watch_app.xiaqu;

import com.lib.common.baseUtils.DateUtils;
import com.qyai.watch_app.message.bean.AlarmInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class XiaQuInfo implements Serializable {
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

    public XiaQuInfo(String sex, String name, String stat, String phoneNo,String imageUrl) {
        this.sex = sex;
        this.name = name;
        this.stat = stat;
        this.phoneNo = phoneNo;
        this.phoneNo=phoneNo;
        this.imageUrl=imageUrl;
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
                list.add(new XiaQuInfo("男", "大傻" + i, "2","13877888xxx","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1289666865,1142307765&fm=26&gp=0.jpg"));
            } else {
                list.add(new XiaQuInfo("男", "大傻" + i, "1","13877888xxx","https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F201805%2F24%2F20180524130203_naqhm.thumb.400_0.jpg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611215672&t=9584cdd1d602a448f54d7d225def3771"));
            }
        }
        return list;
    }
}
