package com.qyai.watch_app.utils;

import android.app.Activity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.dialog.IphoneDialog;
import com.lib.common.netHttp.HttpReq;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.NetHeaderInterceptor;
import com.lib.common.netHttp.OnHttpCallBack;
import com.qyai.baidumap.service.LocationService;
import com.qyai.watch_app.message.MessageService;

import java.util.HashMap;
import java.util.Map;

public class OnlyUserUtils {

    /**
     * 监听请求异常结果处理
     * @param activity
     * @param msg
     */
    public static void catchOut(Activity activity,String msg){
        IphoneDialog dialog =new IphoneDialog(activity, new IphoneDialog.IphoneListener() {
            @Override
            public void upContent(String string) {
                   loginOut(activity);
            }
        },false,"提示",msg,"确定");
        dialog.show();
    }

    /**
     * 退出登录
     * 清除用户名 用户操作记录
     * @param mActivity
     */
    public static void loginOut(Activity mActivity) {
        loginOutApp(mActivity);
        ARouter.getInstance().build("/main/login")
                .withString("userName", "")
                .withString("psw", "")
                .withInt("viewType", Common.viewType)
                .navigation();
        SPValueUtil.saveStringValue(mActivity,Common.USER_NAME,"");
        SPValueUtil.saveStringValue(mActivity,Common.JUSCLASSRESULT, "");
    }

    /**
     * 退出应用
     * @param mActivity
     */
    public static  void loginOutApp(Activity mActivity){
        HttpServiec.getInstance().getFlowbleData(100, HttpReq.getInstence().getIp() + "login/loginOut", new HashMap<>(), new OnHttpCallBack<String>() {
            @Override
            public void onSuccessful(int id, String o) {
                cleanMessage(mActivity);
            }

            @Override
            public void onFaild(int id, String o, String err) {
                cleanMessage(mActivity);
            }
        },String.class);
    }

    /**
     * 清理本地用户信息 token
     * @param mActivity
     */
    public static void cleanMessage(Activity mActivity){
        Map<String, String> heard = new HashMap<>();
        heard.put("token", "");
        NetHeaderInterceptor.getInterceptor().setHeaders(new HashMap<>());
        SPValueUtil.saveStringValue(mActivity, Common.USER_DATA, "");
        SPValueUtil.saveStringValue(mActivity, Common.USER_TOKEN, "");
        MessageService.stopService(mActivity);
        LocationService.stopService(mActivity);
        mActivity.finish();
    }
}
