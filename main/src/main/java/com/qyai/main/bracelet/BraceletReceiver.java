package com.qyai.main.bracelet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class BraceletReceiver extends BroadcastReceiver {
    public ReceiverCallBack callBack;


    public void setCallBack(ReceiverCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
       if(intent!=null&&intent.getAction().equals(BlueToothService.CHANNEL_ID_STRING)){
           callBack.onSuccessAll(intent.getStringExtra("data"));
           callBack.onSuccessBlood(intent.getStringExtra("xueya"));
           callBack.onSuccessHeart(intent.getStringExtra("xinlv"));
           callBack.onSuccessLocation(intent.getStringExtra("locat"));
       }
    }


    public interface ReceiverCallBack{
        void onSuccessAll(String all);
        void onSuccessHeart(String heartValue);
        void onSuccessBlood(String blood);
        void  onSuccessLocation(String str);
    }

}
