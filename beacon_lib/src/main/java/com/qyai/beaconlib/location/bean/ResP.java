package com.qyai.beaconlib.location.bean;

import java.util.List;

public class ResP {
    List<RusPostionInfo> data;

    public ResP(List<RusPostionInfo> data) {
        this.data = data;
    }

    public List<RusPostionInfo> getData() {
        return data;
    }

    public void setData(List<RusPostionInfo> data) {
        this.data = data;
    }

    public ResP() {
    }
}
