package com.qyai.beaconlib.bean;

import java.io.Serializable;

public class PositioningResultBean implements Serializable {
    private String code;
    private String msg;
    private QYPositionBean data;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public QYPositionBean getData() {
        return data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(QYPositionBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PositioningResultBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
