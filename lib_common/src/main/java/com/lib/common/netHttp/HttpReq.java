package com.lib.common.netHttp;

public class HttpReq {
    public static HttpReq instence;
    public String ip;

    public static HttpReq getInstence() {
        if (instence == null) {
            instence = new HttpReq();
        }
        return instence;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
