package com.qyai.beaconlib.utlis;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

//js调用方法
public class JSUtils {
  Context mContext;
  TextView textView;
    WebViewCallBack callBack;

    public JSUtils(Context context, TextView view){
              this.mContext=context;
              this.textView=view;
    }
    public JSUtils(Context context, TextView view, WebViewCallBack callBack){
        this.mContext=context;
        this.textView=view;
        this.callBack=callBack;
    }

    public void setCallBack(WebViewCallBack callBack) {
        this.callBack = callBack;
    }

    @JavascriptInterface
    public  void  setAndroidString(String str){
      callBack.isReady();

    }
    @JavascriptInterface
    public void isMapLoadingFinished(){
        callBack.isMapReady(true);
    }

    public  interface  WebViewCallBack{
        void isReady();
        void isMapReady(boolean b);
    }


}
