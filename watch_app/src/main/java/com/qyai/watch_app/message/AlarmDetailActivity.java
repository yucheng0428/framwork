package com.qyai.watch_app.message;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.UIHelper;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.message.bean.AlarmInfo;

import butterknife.BindView;

public class AlarmDetailActivity extends BaseHeadActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R2.id.iv_head)
    ImageView iv_head;
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

    @BindView(R2.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R2.id.radioButton1)
    RadioButton radioButton1;
    @BindView(R2.id.radioButton2)
    RadioButton radioButton2;
    @BindView(R2.id.spinner)
    Spinner spinner;
    @BindView(R2.id.layout_todo)
    RelativeLayout layout_todo;
    @BindView(R2.id.layout5)
    LinearLayout layout5;

    int type = 1;//1是显示详情 2显示处理
    AlarmInfo info;

    @Override
    public int layoutId() {
        return R.layout.activity_alarm_detail;
    }

    @Override
    protected void initUIData() {
        info = (AlarmInfo) getIntent().getSerializableExtra("info");
        type = getIntent().getIntExtra("type", 1);
        setTvTitle("处理详情");
        if (type == 1) {
            layout5.setVisibility(View.VISIBLE);
            layout_todo.setVisibility(View.GONE);
        } else {
            layout5.setVisibility(View.GONE);
            layout_todo.setVisibility(View.VISIBLE);
        }
        radioGroup.setOnCheckedChangeListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] languages = getResources().getStringArray(R.array.commonArray);
                UIHelper.ToastMessage(mActivity, languages[pos]);
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
            tv_title.setText(info.getTitle());
            tv_time.setText(info.getTime());
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R2.id.radioButton1:
                //这里是checkbox的值处理
                break;
            case R2.id.radioButton2:
                break;
        }
    }
}