package com.qyai.watch_app.xiaqu;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.LogUtil;
import com.lib.common.baseUtils.PhoneUtils;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.dialog.LookBigPictureDialog;
import com.lib.common.netHttp.HttpReq;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.OnHttpCallBack;
import com.lib.common.widgt.CircularProgressView;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonDetailActivity extends BaseHeadActivity {
    @BindView(R2.id.iv_head)
    ImageView iv_head;
    @BindView(R2.id.iv_phone)
    ImageView iv_phone;
    @BindView(R2.id.iv_postion)
    ImageView iv_postion;
    @BindView(R2.id.tv_name)
    TextView tv_name;
    @BindView(R2.id.tv_mz)
    TextView tv_mz;
    @BindView(R2.id.tv_dy)
    TextView tv_dy;
    @BindView(R2.id.tv_sex)
    TextView tv_sex;
    @BindView(R2.id.tv_now_adress)
    TextView tv_now_adress;
    @BindView(R2.id.tv_hj_adress)
    TextView tv_hj_adress;
    @BindView(R2.id.tv_mesure_value)
    TextView tv_mesure_value;
    @BindView(R2.id.tv_mesure_value2)
    TextView tv_mesure_value2;
    @BindView(R2.id.cpv)
    CircularProgressView cpv;
    @BindView(R2.id.cpv2)
    CircularProgressView cpv2;
    @BindView(R2.id.tv_result_1)
    TextView tv_result_1;
    @BindView(R2.id.tv_result_2)
    TextView tv_result_2;
    int personId;
    String head_img;

    @Override
    public int layoutId() {
        return R.layout.activity_person_detail;
    }

    @Override
    protected void initUIData() {
        setTvTitle("人员详情");
        personId = getIntent().getIntExtra("personId", 0);
        if (personId != 0) {
            getPersonDetail();
        }
    }

    @OnClick({R2.id.iv_phone, R2.id.iv_postion, R2.id.iv_head})
    public void onClcik(View view) {
        if (view.getId() == R.id.iv_postion) {
            ARouter.getInstance().build("/maplib/GMapActivity").navigation();
        } else if (view.getId() == R.id.iv_phone) {
            PhoneUtils.dialPhone(mActivity, "12131");
        } else if (view.getId() == R.id.iv_head) {
            if (SPValueUtil.isEmpty(head_img)) {
                new LookBigPictureDialog(mActivity, Constants.imageUrl).show();
            }
        }
    }


    public void getPersonDetail() {
        Map<String, String> map = new HashMap<>();
        HttpServiec.getInstance().getFlowbleData(100, HttpReq.getInstence().getIp() + "person/queryPersonDetail/" + personId, map, new OnHttpCallBack<PersonDetailResult>() {
            @Override
            public void onSuccessful(int id, PersonDetailResult result) {
                if (result != null && result.getCode().equals("000000")) {
                    tv_dy.setText(result.getData().getPersonDTO().getPoliticalStatusName());
                    tv_mz.setText(result.getData().getPersonDTO().getNationName());
                    tv_name.setText(result.getData().getPersonDTO().getName());
                    tv_sex.setText(result.getData().getPersonDTO().getSexName());
                    tv_now_adress.setText(result.getData().getPersonDTO().getCurrentAddress());
                    tv_hj_adress.setText(result.getData().getPersonDTO().getPermanentAddress());
                    tv_mesure_value.setText(changeD(result.getData().getPersonDetailDTO().getAlarmBloodPressureHigh()));
                    tv_mesure_value2.setText(changeD(result.getData().getPersonDetailDTO().getAlarmHeartRate()));
                    Glide.with(mActivity).load(result.getData().getPersonDTO().getImg()).placeholder(R.mipmap.icon_head).skipMemoryCache(true).into(iv_head);
                    head_img = result.getData().getPersonDTO().getImg();
                    tv_result_1.setText(result.getData().getSignNowDTO().getBloodPressureState()==null?"":result.getData().getSignNowDTO().getBloodPressureState());
                    tv_result_2.setText(result.getData().getSignNowDTO().getHeartRateState()==null?"":result.getData().getSignNowDTO().getHeartRateState());
                    String[] arr = getZ(result.getData().getPersonDetailDTO().getAlarmHeartRate());
                    String[] arr1 = getZ(result.getData().getPersonDetailDTO().getAlarmOxygen());
                    if (arr != null && arr.length > 0) {
                        cpv.setProgress((int) ((Float.valueOf(arr[0]) / Float.valueOf(arr[1])) * 100),2000);
                    }
                    if (arr1 != null && arr1.length > 0) {
                        cpv2.setProgress((int) ((Float.valueOf(arr1[0]) / Float.valueOf(arr1[1])) * 100),2000);
                    }
                }
            }

            @Override
            public void onFaild(int id, PersonDetailResult o, String err) {

            }
        }, PersonDetailResult.class);
    }

    public String changeD(String str) {
        return str.replace(",", "/");
    }

    public String[] getZ(String str) {
        return str.split(",");
    }
}