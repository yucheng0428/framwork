package com.qyai.watch_app.utils;

import android.app.Activity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.dialog.IphoneDialog;
import com.lib.common.netHttp.NetHeaderInterceptor;
import com.qyai.watch_app.message.MessageService;

import java.util.HashMap;

public class OnlyUserUtils {

    public static void catchOut(Activity activity,String msg){
        IphoneDialog dialog =new IphoneDialog(activity, new IphoneDialog.IphoneListener() {
            @Override
            public void upContent(String string) {
                   loginOut(activity);
            }
        },false,"提示",msg,"确定");
        dialog.show();
    }

    public static void loginOut(Activity mActivity) {
        SPValueUtil.saveStringValue(mActivity, Common.USER_DATA, "");
        SPValueUtil.saveStringValue(mActivity, Common.USER_TOKEN, "");
        SPValueUtil.saveStringValue(mActivity, Common.USER_PASSWORD, "");
//        SPValueUtil.saveStringValue(mActivity, Common.USER_NAME, "");
        SPValueUtil.saveStringValue(mActivity, Common.JUSCLASSRESULT, "");
        MessageService.stopService(mActivity);
        NetHeaderInterceptor.getInterceptor().setHeaders(new HashMap<>());
        mActivity.finish();
        ARouter.getInstance().build("/main/login")
                .withString("userName", "")
                .withString("psw", "")
                .withInt("viewType", 2)
                .navigation();
    }
}
