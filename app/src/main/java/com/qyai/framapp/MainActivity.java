package com.qyai.framapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.lib.common.base.BaseActivity;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.LogUtil;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.Utils;
import com.lib.common.baseUtils.permssion.PermissionCheckUtils;
import com.lib.common.bean.UserEvent;
import com.lib.common.netHttp.HttpReq;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.NetHeaderInterceptor;
import com.lib.common.netHttp.OnHttpCallBack;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

@Route(path = "/test/main")
public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_time)
    TextView text_time;
    boolean isLogin = false;
    private int count = 5;
    Timer timer= new Timer();
    TimerTask mTimerTask;
    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int count = msg.arg1;
            if(count==0){
                if (isLogin) {
                    if (Common.viewType == 1) {
                        ARouter.getInstance().build("/watch/HomeActivity").navigation();
                    } else {
                        ARouter.getInstance().build(Common.HOME_VIEW).navigation();
                    }
                } else {
                    ARouter.getInstance().build(Common.LOGIN_PATH)
                            .withInt("viewType", Common.viewType)
                            .navigation();
                }
                mActivity.finish();
            }else {
                text_time.setText(count+"");
            }
        }
    };
    @Override
    protected void initUIData(Bundle bundle) {
        setTranslucentNavigationColor(getResources().getColor(R.color.half_transparent));
        if(PermissionCheckUtils.requestPermissions(mActivity, Common.REQUEST_CODE, Common.permissionList)){
            LogUtil.e("Permission","11");
            adLoading();
        }
        mTimerTask=new TimerTask() {
            @Override
            public void run() {

            }
        };
    }

    public void adLoading() {
        if (SPValueUtil.isEmpty(SPValueUtil.getStringValue(mActivity, Common.USER_NAME)) && SPValueUtil.isEmpty(SPValueUtil.getStringValue(mActivity, Common.USER_PASSWORD))) {
            LoginReq();
        }
        mTimerTask=new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.arg1 = count;
                if(count!=-1){
                    count--;
                }else {
                    return;
                }
                handler.sendMessage(message);
            }
        };
        timer.schedule(mTimerTask,1000,1000);
    }

    public void LoginReq() {
        Map<Object, Object> para = new HashMap<>();
        para.put("account", SPValueUtil.getStringValue(mActivity, Common.USER_NAME));
        para.put("pwd", Utils.md5(SPValueUtil.getStringValue(mActivity, Common.USER_PASSWORD)));
        para.put("loginType", "phone");
        HttpServiec.getInstance().postFlowableMap(100, HttpReq.getInstence().getIp() + "login/login", para, new OnHttpCallBack<UserEvent>() {
            @Override
            public void onSuccessful(int id, UserEvent baseResult) {
                isLogin = true;
                Map<String, String> heard = new HashMap<>();
                heard.put("token", baseResult.getData().getUserInDeptDTO().getToken());
                NetHeaderInterceptor.getInterceptor().setHeaders(heard);
                SPValueUtil.saveStringValue(mActivity, Common.USER_DATA, JSON.toJSONString(baseResult.getData()));
                SPValueUtil.saveStringValue(mActivity, Common.USER_TOKEN, baseResult.getData().getUserInDeptDTO().getToken() + "");
                SPValueUtil.saveStringValue(mActivity, Common.USER_PASSWORD, SPValueUtil.getStringValue(mActivity, Common.USER_PASSWORD));
                SPValueUtil.saveStringValue(mActivity, Common.USER_NAME, SPValueUtil.getStringValue(mActivity, Common.USER_NAME));
            }

            @Override
            public void onFaild(int id, UserEvent baseResult, String err) {
                isLogin = false;
            }
        }, UserEvent.class);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    private void stopTimer(){
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        count = 0;
    }

    //获取权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Common.REQUEST_CODE) {
            LogUtil.e("Permission","22");
            adLoading();
        }
    }
}