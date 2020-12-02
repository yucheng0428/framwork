package com.qyai.watch_app.contacts.bean;

import com.qyai.watch_app.message.bean.MessageBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactsInfo implements Serializable {
    public String sort;
    public String phoneNo;
    public String name;
    public int type;

    public ContactsInfo(String sort, String phoneNo, String name) {
        this.sort = sort;
        this.phoneNo = phoneNo;
        this.name = name;
    }

    public ContactsInfo(String sort, String phoneNo, String name, int type) {
        this.sort = sort;
        this.phoneNo = phoneNo;
        this.name = name;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ContactsInfo() {
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getName() {
        return name;
    }

    public static List<ContactsInfo> getContactsInfoList() {
        List<ContactsInfo> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new ContactsInfo("第" + i + "顺位", "1896565655" + i, "xx" + i));
        }
        list.add(new ContactsInfo("第顺位", "1896565655", "xx", 1));
        return list;
    }
}
