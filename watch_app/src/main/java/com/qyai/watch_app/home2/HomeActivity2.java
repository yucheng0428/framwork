package com.qyai.watch_app.home2;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.lib.common.base.BaseActivity;
import com.lib.common.base.ViewManager;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.FileUtils;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.dialog.IphoneDialog;
import com.lib.common.dialog.LookBigPictureDialog;
import com.lib.common.netHttp.HttpReq;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.NetHeaderInterceptor;
import com.lib.common.netHttp.OnHttpCallBack;
import com.lib.common.recyclerView.RecyclerItemCallback;
import com.lib.common.widgt.MyDrawerLayout;
import com.lib.common.widgt.RoundImageView;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.message.AlarmActivity;
import com.qyai.watch_app.message.AlarmAdapter;
import com.qyai.watch_app.message.AlarmDetailActivity;
import com.qyai.watch_app.message.JusClassAdapter;
import com.qyai.watch_app.message.MessageService;
import com.qyai.watch_app.message.bean.AlarmCountResult;
import com.qyai.watch_app.message.bean.AlarmPushBean;
import com.qyai.watch_app.message.bean.AlarmResult;
import com.qyai.watch_app.message.bean.JusClassResult;
import com.qyai.watch_app.message.websocket.AlamListenser;
import com.qyai.watch_app.utils.GpsLactionUtils;
import com.qyai.watch_app.xiaqu.XiaQuActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/watch/HomeActivity2")
public class HomeActivity2 extends BaseActivity implements AlamListenser {
    @BindView(R2.id.drawer_layout)
    MyDrawerLayout drawer_layout;
    @BindView(R2.id.nav_view)
    NavigationView nav_view;
    @BindView(R2.id.tvTitle)
    Spinner tvTitle;
    @BindView(R2.id.ivLeft)
    ImageView ivLeft;
    @BindView(R2.id.ivRight)
    ImageView ivRight;

    @BindView(R2.id.tv_all)
    TextView tv_all;//总人数
    @BindView(R2.id.tv_alarm_all)
    TextView tv_alarm_all;//告警总数
    @BindView(R2.id.tv_nodo)
    TextView tv_nodo;//未处理告警
    @BindView(R2.id.tv_online)
    TextView tv_online;//在线人数
    @BindView(R2.id.tv_today_alarm_all)
    TextView tv_today_alarm_all;//今日告警总数
    @BindView(R2.id.tv_today_nodo)
    TextView tv_today_nodo;//今日未处理告警
    @BindView(R2.id.iv_head)
    RoundImageView iv_head;
    @BindView(R2.id.tv_name)
    TextView tv_name;
    @BindView(R2.id.tv_phoenNo)
    TextView tv_phoenNo;
    @BindView(R2.id.tv_project)
    TextView tv_project;
    @BindView(R2.id.tv_user)
    TextView tv_user;
    @BindView(R2.id.tv_changePassword)
    TextView tv_changePassword;
    @BindView(R2.id.tv_about)
    TextView tv_about;
    @BindView(R2.id.tv_out_login)
    TextView tv_out_login;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    AlarmAdapter alarmAdapter;

    @BindView(R2.id.tv_xqsh)
    TextView tv_xqsh;
    long mExitTime = 1000;
    UserEvent.UserData userData;
    JusClassAdapter jusClassAdapter;
    int classId = 0;

    @Override
    protected int layoutId() {
        return R.layout.activity_home_view2;
    }

    @Override
    protected void initUIData(Bundle bundle) {
        setScreenModel(3);
        userData = JSONObject.parseObject(SPValueUtil.getStringValue(mActivity, Common.USER_DATA), UserEvent.UserData.class);
        if (userData != null) {
            tv_name.setText(userData.getUserInDeptDTO().getName());
            tv_phoenNo.setText("手机号码 : " + userData.getUserInDeptDTO().getMobilePhone());
            tv_project.setText("所属机构 : " + userData.getUserInDeptDTO().getDeptFullName());
            if (SPValueUtil.isEmpty(userData.getUserInDeptDTO().getRoleName())) {
                tv_user.setVisibility(View.VISIBLE);
                tv_user.setText("职位 : " + userData.getUserInDeptDTO().getRoleName());
            } else {
                tv_user.setVisibility(View.GONE);
            }
            if (SPValueUtil.isEmpty(userData.getUserInDeptDTO().getIcon())) {
                Glide.with(mActivity).load(FileUtils.base64ChangeBitmap(userData.getUserInDeptDTO().getIcon())).placeholder(R.mipmap.icon_head).skipMemoryCache(true).into(iv_head);
            }
        }
        setTranslucentStatusColor(mActivity.getResources().getColor(R.color.white));
        ivRight.setVisibility(View.VISIBLE);
        ivLeft.setImageResource(R.mipmap.cehua);
        ivRight.setImageResource(R.mipmap.message);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        alarmAdapter = new AlarmAdapter(mActivity);
        recyclerView.setAdapter(alarmAdapter);
        alarmAdapter.setRecItemClick(new RecyclerItemCallback<AlarmPushBean, AlarmAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, AlarmPushBean model, int tag, AlarmAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                Intent intent = new Intent(mActivity, AlarmDetailActivity.class);
                intent.putExtra("info", model);
                switch (tag) {
                    case 1:
                        startActivityForResult(intent, Constants.REQUEST_CODE);
                        break;
                    case 2:
                        //2是点击处理按钮;
                        startActivityForResult(intent, Constants.REQUEST_CODE);
                        break;
                    case 3:
                        ARouter.getInstance().build("/maplib/GMapActivity").navigation();
                        break;
                }
            }
        });
        jusClassAdapter = new JusClassAdapter(mActivity);
        tvTitle.setAdapter(jusClassAdapter);
        tvTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (pos == 0 && classId == 0) {
                    if (SPValueUtil.isEmpty(SPValueUtil.getStringValue(mActivity, Common.JUSCLASSRESULT))) {
                        JusClassResult.DataBean dataBean = JSONObject.parseObject(SPValueUtil.getStringValue(mActivity, Common.JUSCLASSRESULT), JusClassResult.DataBean.class);
                        if (dataBean != null) {
                            classId = dataBean.getId();
                            int ps = 0;
                            for (int i = 0; i < jusClassAdapter.getData().size(); i++) {
                                if (dataBean.getId() == jusClassAdapter.getData().get(i).getId()) {
                                    ps = i;
                                }
                            }
                            tvTitle.setSelection(ps, true);

                        }
                    } else {
                        JusClassResult.DataBean dataBean = (JusClassResult.DataBean) jusClassAdapter.getItem(pos);
                        SPValueUtil.saveStringValue(mActivity, Common.JUSCLASSRESULT, JSONObject.toJSONString(dataBean));
                        classId = dataBean.getId();
                    }
                } else {
                    JusClassResult.DataBean dataBean = (JusClassResult.DataBean) jusClassAdapter.getItem(pos);
                    SPValueUtil.saveStringValue(mActivity, Common.JUSCLASSRESULT, JSONObject.toJSONString(dataBean));
                    classId = dataBean.getId();
                }
                reshData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        MessageService.initService(mActivity);
        MessageService.setAlamListenser(this);
        getAllClass();
        GpsLactionUtils.getInstance(mActivity).startGps();
    }


    @OnClick({
            R2.id.ivLeft, R2.id.tv_xqsh,
            R2.id.ivRight, R2.id.tv_about, R2.id.tv_out_login, R2.id.tv_changePassword, R2.id.iv_head,})
    public void onClick(View view) {
        Intent intent = new Intent();
        if (view.getId() == R.id.ivLeft) {
            if (drawer_layout.isOpen()) {
                drawer_layout.closeDrawers();
            } else {
                drawer_layout.openDrawer(GravityCompat.START);
            }
        } else if (view.getId() == R.id.ivRight) {
            intent.setClass(mActivity, AlarmActivity.class);
            intent.putExtra("classId", classId);
            startActivity(intent);
        } else if (view.getId() == R.id.tv_xqsh) {
            intent.setClass(mActivity, XiaQuActivity.class);
            intent.putExtra("classId", classId);
            startActivity(intent);
        } else if (view.getId() == R.id.tv_changePassword) {
            ARouter.getInstance().build("/main/changePsw").navigation();
        } else if (view.getId() == R.id.tv_about) {
            ARouter.getInstance().build("/main/about").navigation();
        } else if (view.getId() == R.id.tv_out_login) {
            IphoneDialog iphoneDialog = new IphoneDialog(mActivity, new IphoneDialog.IphoneListener() {
                @Override
                public void upContent(String string) {
                    loginOut();
                }
            }, false, "提示", "是否要退出登录");
            iphoneDialog.show();
        } else if (view.getId() == R.id.iv_head) {
            if (SPValueUtil.isEmpty(userData.getUserInDeptDTO().getIcon())) {
                new LookBigPictureDialog(mActivity, FileUtils.base64ChangeBitmap(userData.getUserInDeptDTO().getIcon())).show();
            }
        }
    }

    public void loginOut() {
        SPValueUtil.saveStringValue(mActivity, Common.USER_DATA, "");
        SPValueUtil.saveStringValue(mActivity, Common.USER_TOKEN, "");
        SPValueUtil.saveStringValue(mActivity, Common.USER_PASSWORD, "");
        SPValueUtil.saveStringValue(mActivity, Common.USER_NAME, "");
        SPValueUtil.saveStringValue(mActivity, Common.JUSCLASSRESULT, "");
        MessageService.stopService(mActivity);
        NetHeaderInterceptor.getInterceptor().setHeaders(new HashMap<>());
        HomeActivity2.this.finish();
        ARouter.getInstance().build("/main/login")
                .withString("userName", "")
                .withString("psw", "")
                .withInt("viewType", 2)
                .navigation();
//        Map<Object, Object> para = new HashMap<>();
//        HttpServiec.getInstance().postFlowableMap(100, Common.LOGIN_OUT,para, new OnHttpCallBack<String>() {
//            @Override
//            public void onSuccessful(int id, String baseResult) {
//                BaseResult event= JSONObject.parseObject((String) baseResult,BaseResult.class);
//                if(event!=null&&event.getCode().equals("000000")){
//                }
//            }
//
//            @Override
//            public void onFaild(int id, String baseResult, String err) {
//                UIHelper.ToastMessage(HomeActivity2.this,err);
//            }
//        },String.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MessageService.stopService(mActivity);
        GpsLactionUtils.getInstance(mActivity).stopGps();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //两秒之内按返回键就会退出
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                UIHelper.ToastMessage(this, "再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                ViewManager.getInstance().exitApp(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //查询告警
    public void getAlarmList(int clas) {
        HashMap req = new HashMap();
        req.put("dealStatus", "0");
        req.put("page", "-1");
        List<String> count = new ArrayList<>();
        count.add(clas + "");
        req.put("classId", count);
        //查询告警
        HttpServiec.getInstance().postFlowableData(100, HttpReq.getInstence().getIp() + "alarm/queryAlarm", req, new OnHttpCallBack<AlarmResult>() {
            @Override
            public void onSuccessful(int id, AlarmResult result) {
                alarmAdapter.clearData();
                if (result != null && result.getData().size() > 0) {
                    alarmAdapter.setData(result.getData());
                }
            }

            @Override
            public void onFaild(int id, AlarmResult o, String err) {
                UIHelper.ToastMessage(HomeActivity2.this, err);
            }
        }, AlarmResult.class);
    }


    //获取告警统计信息
    public void getCount(int clas) {
        HashMap req = new HashMap();
        List<String> count = new ArrayList<>();
        count.add(clas + "");
        req.put("classId", count);
        HttpServiec.getInstance().postFlowableData(100, HttpReq.getInstence().getIp() + "alarm/queryAlarmCount", req, new OnHttpCallBack<AlarmCountResult>() {
            @Override
            public void onSuccessful(int id, AlarmCountResult result) {
                if (result != null && result.getData() != null) {
                    tv_all.setText("总人数:" + result.getData().getPersonCount());
                    tv_alarm_all.setText("告警总数:" + result.getData().getAlarmCount());
                    tv_nodo.setText("未处理告警:" + result.getData().getPendingAlarmCount());
                    tv_online.setText("在线人数:" + result.getData().getPersonOnLineCount());
                    tv_today_alarm_all.setText("今日告警总数:" + result.getData().getToDayAlarmCount());
                    tv_today_nodo.setText("今日未处理告警:" + result.getData().getToDayPendingAlarmCount());
                }
            }

            @Override
            public void onFaild(int id, AlarmCountResult o, String err) {

            }
        }, AlarmCountResult.class);
    }

    //获取辖区分类
    public void getAllClass() {
        HashMap req = new HashMap();
        HttpServiec.getInstance().getFlowbleData(100, HttpReq.getInstence().getIp() + "jurisdiction/queryAllClass/userClass", req, new OnHttpCallBack<JusClassResult>() {
            @Override
            public void onSuccessful(int id, JusClassResult result) {
                if (result != null && result.getData() != null) {
                    jusClassAdapter.setData(result.getData());
                }
            }

            @Override
            public void onFaild(int id, JusClassResult o, String err) {

            }
        }, JusClassResult.class);
    }

    //更新列表
    public void reshData() {
        if (classId != 0) {
            getCount(classId);
            getAlarmList(classId);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        reshData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REQUEST_CODE) {
            reshData();
        }
    }

    @Override
    public void reshAlamList() {
        reshData();
    }

    @Override
    public void pushMsgReshList(AlarmPushBean bean) {
        if (bean != null && bean.getDealStatus() == 0) {
//            alarmAdapter.getDataSource().add(0, bean);
//            alarmAdapter.notifyDataSetChanged();
            reshData();
        }
    }
}