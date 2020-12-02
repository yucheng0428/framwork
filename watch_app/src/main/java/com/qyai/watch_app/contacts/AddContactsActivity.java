package com.qyai.watch_app.contacts;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.qyai.watch_app.R;
import com.qyai.watch_app.contacts.bean.ContactsInfo;

import butterknife.BindView;
import butterknife.OnClick;

public class AddContactsActivity extends BaseHeadActivity {
    @BindView(R.id.et_phone_no)
    EditText et_phone_no;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.btn_ok)
    Button btn_ok;

    @Override
    public int layoutId() {
        return R.layout.activity_add_contacts;
    }

    @Override
    protected void initUIData() {
        setTvTitle("添加联系人");
    }

    @OnClick({R.id.btn_ok})
    public void onClick(View view) {
        if (view.getId() == R.id.btn_ok) {
            if (SPValueUtil.isEmpty(et_phone_no.getText().toString()) &&
                    SPValueUtil.isEmpty(et_name.getText().toString())) {
                Intent intent = new Intent();
                intent.putExtra("data", new ContactsInfo("", et_phone_no.getText().toString(), et_name.getText().toString()));
                setResult(Constants.REQUEST_CODE, intent);
                mActivity.finish();
            } else {
                UIHelper.ToastMessage(mActivity, "请输入完整信息");
            }
        }
    }
}