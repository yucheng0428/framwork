package com.qyai.watch_app.xiaqu;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.FileUtils;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.dialog.LookBigPictureDialog;
import com.lib.common.netHttp.HttpReq;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.OnHttpCallBack;
import com.lib.common.widgt.CircularProgressView;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.contacts.ContactsDialog;
import com.qyai.watch_app.contacts.bean.ContactsInfo;
import com.qyai.watch_app.utils.OnlyUserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    List<ContactsInfo> contactsInfos;

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
            ARouter.getInstance().build(Common.MAP_LOCTION).
                    withString("personId", personId + "").navigation();
        } else if (view.getId() == R.id.iv_phone) {
            ContactsDialog dialog = new ContactsDialog(mActivity, contactsInfos);
            dialog.show();
        } else if (view.getId() == R.id.iv_head) {
            if (SPValueUtil.isEmpty(head_img)) {
                new LookBigPictureDialog(mActivity, FileUtils.base64ChangeBitmap(head_img)).show();
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
                    if (SPValueUtil.isEmpty(result.getData().getPersonDTO().getcAddressName())) {
                        tv_now_adress.setText(result.getData().getPersonDTO().getcAddressName() + result.getData().getPersonDTO().getCurrentAddress());
                    }
                    String addressName = result.getData().getPersonDTO().getpAddressName() == null ? "" : result.getData().getPersonDTO().getpAddressName();
                    String permanetAddress = result.getData().getPersonDTO().getPermanentAddress() == null ? "" : result.getData().getPersonDTO().getPermanentAddress();

                    tv_hj_adress.setText(addressName + permanetAddress);

                    if (result.getData().getSignNowDTO() != null) {
                        if (SPValueUtil.isEmpty(result.getData().getSignNowDTO().getTemperature())) {
                            tv_mesure_value.setText(result.getData().getSignNowDTO().getTemperature());
                        }
                        if (SPValueUtil.isEmpty(result.getData().getSignNowDTO().getHeartRate())) {
                            tv_mesure_value2.setText(result.getData().getSignNowDTO().getHeartRate());
                        }
                    }
                    head_img = result.getData().getPersonDTO().getImg();
                    Glide.with(mActivity).load(FileUtils.base64ChangeBitmap(head_img)).placeholder(R.mipmap.icon_head).skipMemoryCache(true).into(iv_head);
                    if (result.getData().getSignNowDTO() != null) {
                        tv_result_1.setText(result.getData().getSignNowDTO().getTemperatureState() == null ? "" : result.getData().getSignNowDTO().getTemperatureState());
                        tv_result_2.setText(result.getData().getSignNowDTO().getHeartRateState() == null ? "" : result.getData().getSignNowDTO().getHeartRateState());
                        String[] arr1 = getZ(result.getData().getPersonDetailDTO().getAlarmOxygen());
                        if (result.getData().getSignNowDTO().getBloodPressureLow() != null && result.getData().getSignNowDTO().getBloodPressureHigh() != null) {
                            cpv.setProgress((int) ((Float.valueOf(result.getData().getSignNowDTO().getTemperature()) / Float.valueOf(result.getData().getSignNowDTO().getBloodPressureHigh()) * 100)), 2000);
                        }
                        if (arr1 != null && arr1.length > 0 && result.getData().getSignNowDTO().getHeartRate() != null) {
                            cpv2.setProgress((int) ((Float.valueOf(result.getData().getSignNowDTO().getHeartRate()) / Float.valueOf(arr1[1])) * 100), 2000);
                        }
                    }
                    contactsInfos = changeList(result.getData().getPersonDTO());
                } else if (result != null && result.getCode().equals(Common.CATCH_CODE)) {
                    OnlyUserUtils.catchOut(mActivity, result.getMsg());
                }
            }

            @Override
            public void onFaild(int id, PersonDetailResult o, String err) {

            }
        }, PersonDetailResult.class);
    }


    public String[] getZ(String str) {
        if (SPValueUtil.isEmpty(str)) {
            return str.split(",");
        }
        return new String[]{};
    }

    public List<ContactsInfo> changeList(PersonDetailResult.DataBean.PersonDTOBean dtoBean) {
        List<ContactsInfo> infos = new ArrayList<>();
        ContactsInfo contactsInfo = new ContactsInfo("当前联系人", dtoBean.getPhone(), dtoBean.getName());
        infos.add(contactsInfo);
        if (dtoBean != null) {
            String arr[] = dtoBean.getEmergencyMan().split(",");
            String phone[] = dtoBean.getEmergencyPhone().split(",");
            if (arr.length > 0 && phone.length > 0 && arr.length == phone.length) {
                for (int i = 0; i < arr.length; i++) {
                    switch (i) {
                        case 0:
                            infos.add(new ContactsInfo("第一紧急联系人", phone[i], arr[i]));
                            break;
                        case 1:
                            infos.add(new ContactsInfo("第二紧急联系人", phone[i], arr[i]));
                            break;
                        case 2:
                            infos.add(new ContactsInfo("第三紧急联系人", phone[i], arr[i]));
                            break;
                        case 3:
                            infos.add(new ContactsInfo("第四紧急联系人", phone[i], arr[i]));
                            break;
                        case 4:
                            infos.add(new ContactsInfo("第五紧急联系人", phone[i], arr[i]));
                            break;
                        case 5:
                            infos.add(new ContactsInfo("第六紧急联系人", phone[i], arr[i]));
                            break;
                        default:
                            infos.add(new ContactsInfo("第" + (i + 1) + "紧急联系人", phone[i], arr[i]));
                            break;
                    }
                }
            }
        }
        return infos;
    }
}