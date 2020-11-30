package com.qyai.bracelet_lib;

import com.lib.common.netHttp.HttpReq;

public class Commons {
    public  static String UPLOADBRACELETINFO=HttpReq.getInstence().getIp()+"sign/insertSign";
    public  static String UPLOADLOCATION= HttpReq.getInstence().getIp() +"traceBack/insertTraceBack";


}
