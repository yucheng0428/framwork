package com.qyai.watch_app.home;

import com.qyai.watch_app.contacts.bean.ContactsInfo;
import com.qyai.watch_app.message.bean.MessageBean;

import java.util.ArrayList;
import java.util.List;

public class MyWatchInfo {
    String name;
    String isunbind;
    String isDoing;

    public MyWatchInfo(String name, String isunbind, String isDoing) {
        this.name = name;
        this.isunbind = isunbind;
        this.isDoing = isDoing;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsunbind() {
        return isunbind;
    }

    public void setIsunbind(String isunbind) {
        this.isunbind = isunbind;
    }

    public String getIsDoing() {
        return isDoing;
    }

    public void setIsDoing(String isDoing) {
        this.isDoing = isDoing;
    }

    public static List<MyWatchInfo> getMyWatchInfo(){
        List<MyWatchInfo>list=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new MyWatchInfo("第" + i + "顺位", "1896565655" + i, "xx" + i));
        }
        return  list;
    }
}
