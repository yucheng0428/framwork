package com.qyai.watch_app.message;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.baseUtils.baseModle.BaseResult;
import com.lib.common.dialog.IphoneDialog;
import com.lib.common.dialog.LookBigPictureDialog;
import com.lib.common.netHttp.HttpReq;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.OnHttpCallBack;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.message.bean.AlarmInfo;
import com.qyai.watch_app.message.bean.AlarmPushBean;
import com.qyai.watch_app.message.bean.CommonResult;
import com.qyai.watch_app.utils.OnlyUserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AlarmDetailActivity extends BaseHeadActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R2.id.iv_head)
    TextView iv_head;
    @BindView(R2.id.tv_title)
    TextView tv_title;
    @BindView(R2.id.tv_time)
    TextView tv_time;
    @BindView(R2.id.tv_alarm_person)
    TextView tv_alarm_person;
    @BindView(R2.id.tv_alarm_wl)
    TextView tv_alarm_wl;
    @BindView(R2.id.tv_alarm_qy)
    TextView tv_alarm_qy;
    @BindView(R2.id.tv_alarm_content)
    TextView tv_alarm_content;
    @BindView(R2.id.tv_alarm_result)
    TextView tv_alarm_result;
    @BindView(R2.id.tv_alarm_result_content)
    TextView tv_alarm_result_content;
    @BindView(R2.id.tv_alarm_result_dealUser)
    TextView tv_alarm_result_dealUser;
    @BindView(R2.id.tv_alarm_result_dealOpinion)
    TextView tv_alarm_result_dealOpinion;
    @BindView(R2.id.ed_remaks)
    EditText ed_remaks;
    @BindView(R2.id.tv_valueof_heartRate)
    TextView tv_valueof_heartRate;
    @BindView(R2.id.tv_valueof_temperature)
    TextView tv_valueof_temperature;
    @BindView(R2.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R2.id.radioButton1)
    RadioButton radioButton1;
    @BindView(R2.id.radioButton2)
    RadioButton radioButton2;
    @BindView(R2.id.radioButton3)
    RadioButton radioButton3;
    @BindView(R2.id.spinner)
    Spinner spinner;
    @BindView(R2.id.layout_todo)
    RelativeLayout layout_todo;
    @BindView(R2.id.layout5)
    LinearLayout layout5;
    @BindView(R2.id.bt_do)
    Button bt_do;
    @BindView(R2.id.bt_nodo)
    Button bt_nodo;
    AlarmPushBean info;
    List<CommonResult.DataBean> dataBeans = new ArrayList<>();
    CommonAdapter adapter;
    int option=1;
    @Override
    public int layoutId() {
        return R.layout.activity_alarm_detail;
    }

    @Override
    protected void initUIData() {
        info = (AlarmPushBean) getIntent().getSerializableExtra("info");
        //处理结果，1已处理，0未处理, 2忽略
        if(info!=null&&info.getDealStatus()==0){
            setTvTitle("告警处理");
            layout5.setVisibility(View.GONE);
            layout_todo.setVisibility(View.VISIBLE);
        }else {
            setTvTitle("告警详情");
            layout5.setVisibility(View.VISIBLE);
            layout_todo.setVisibility(View.GONE);
        }
        getCommon();
        radioGroup.setOnCheckedChangeListener(this);
        adapter = new CommonAdapter(mActivity);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                CommonResult.DataBean dataBean = (CommonResult.DataBean) adapter.getItem(pos);
                ed_remaks.setText(dataBean.getContent());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        setViewMsg();
    }

    public void setViewMsg() {
        if (info != null) {
            tv_title.setText(info.getFenceName());
            tv_time.setText(info.getCreateTime());
            tv_alarm_person.setText(info.getAuthorName());
            tv_alarm_wl.setText(info.getFenceName());
            tv_alarm_qy.setText(info.getAreaName());
            tv_alarm_content.setText(info.getContent());
            tv_alarm_result.setText(info.getDealStatusName());
            tv_alarm_result_content.setText(info.getDealContent());
            tv_alarm_result_dealOpinion.setText(info.getDealOpinionName());
            tv_alarm_result_dealUser.setText(info.getDealUserName()!=null?info.getDealUserName():info.getDealUser());
            iv_head.setText(info.getTypeName());
            if(SPValueUtil.isEmpty(info.getHeartRate())){
                tv_valueof_heartRate.setText("心率:"+info.getHeartRate());
            }else {
                tv_valueof_heartRate.setText("");
            }
            if(SPValueUtil.isEmpty(info.getTemperature())){
                tv_valueof_temperature.setText("体温:"+info.getTemperature());
            }else {
                tv_valueof_temperature.setText("");
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        if (checkedId == R.id.radioButton1) {
//            //这里是checkbox的值处理
//            option=1;
//        } else if (checkedId == R.id.radioButton2) {
//            option=2;
//        }else if(checkedId== R.id.radioButton3){
//            option=3;
//        }
    }

    @OnClick({ R2.id.bt_do, R2.id.bt_nodo,R2.id.radioButton3})
    public void onClick(View view) {
         if (view.getId() == R.id.bt_do) {
              handlerAlarm(1);
        } else if (view.getId() == R.id.bt_nodo) {
                handlerAlarm(2);
        }else if(view.getId()==R.id.radioButton3){
            IphoneDialog iphoneDialog=new IphoneDialog(mActivity, new IphoneDialog.DialogClickListener() {
                @Override
                public void onDialogClick(View view) {
                    radioButton1.setChecked(true);
                    radioButton3.setChecked(false);
                }
            }, new IphoneDialog.DialogClickListener() {
                @Override
                public void onDialogClick(View view) {
                    radioButton1.setChecked(false);
                    radioButton3.setChecked(true);
                    option=2;
                }
            },false,"提示","是否今日不再告警","取消","确定");
            iphoneDialog.show();
        }else if(view.getId()==R.id.radioButton1){
            radioButton1.setChecked(true);
            radioButton3.setChecked(false);
            option=0;
        }

    }

    public void getCommon() {
        Map<String, String> para = new HashMap();
        HttpServiec.getInstance().getFlowbleData(100, HttpReq.getInstence().getIp() + "/alarm/queryCommonWords", para, new OnHttpCallBack<CommonResult>() {
            @Override
            public void onSuccessful(int id, CommonResult result) {
                if (result != null && result.getData().size() > 0&&result.getCode().equals("000000")) {
                    adapter.setData(result.getData());
                    ed_remaks.setText(result.getData().get(0).getContent());
                }else if(result!=null&&result.getCode().equals(Common.CATCH_CODE)){
                    OnlyUserUtils.catchOut(mActivity,result.getMsg());
                }
            }

            @Override
            public void onFaild(int id, CommonResult o, String err) {

            }
        }, CommonResult.class);
    }

    public void handlerAlarm(int type) {
        if(!SPValueUtil.isEmpty( ed_remaks.getText().toString())){
            UIHelper.ToastMessage(mActivity,"请输入处理内容");
            return;
        }
        Map<Object, Object> parm = new HashMap<>();
        parm.put("id", info.getId());
        parm.put("dealContent", ed_remaks.getText().toString());
        parm.put("dealOpinion", option);

        if (type == 1) {
            parm.put("dealStatus", 1);
        } else {
            parm.put("dealStatus", 2);
        }
        HttpServiec.getInstance().postFlowableData(100, HttpReq.getInstence().getIp() + "/alarm/handleAlarm", parm, new OnHttpCallBack<BaseResult>() {
            @Override
            public void onSuccessful(int id, BaseResult result) {
                if(result!=null&&result.getCode().equals("000000")){
                    UIHelper.ToastMessage(mActivity,result.getMsg());
                    setResult(Constants.REQUEST_CODE);
                    mActivity.finish();
                }else if(result!=null&&result.getCode().equals(Common.CATCH_CODE)){
                    OnlyUserUtils.catchOut(mActivity,result.getMsg());
                }
            }

            @Override
            public void onFaild(int id, BaseResult o, String err) {
                UIHelper.ToastMessage(mActivity,err);
            }
        }, BaseResult.class);
    }


}