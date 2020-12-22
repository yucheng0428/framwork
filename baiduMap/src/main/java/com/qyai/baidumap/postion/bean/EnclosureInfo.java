package com.qyai.baidumap.postion.bean;

import java.util.ArrayList;
import java.util.List;

public class EnclosureInfo {
    public String name;
    public String adress;

    public EnclosureInfo(String name, String adress) {
        this.name = name;
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public static List<EnclosureInfo> getEnclosureInfoList() {
        List<EnclosureInfo> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new EnclosureInfo("围栏" + i, "湖北省武汉市洪山区关山大道" + i));
        }
        return list;
    }
}
