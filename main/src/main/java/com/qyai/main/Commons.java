package com.qyai.main;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.widget.Toast;

import com.lib.common.netHttp.HttpReq;

public class Commons {
    public  static String UPLOADBRACELETINFO=HttpReq.getInstence().getIp()+"sign/insertSign";
    public  static String UPLOADLOCATION= HttpReq.getInstence().getIp() +"traceBack/insertTraceBack";


}
