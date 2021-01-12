package com.qyai.watch_app.contacts;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.MyIntent;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.baseUtils.permssion.PermissionCheckUtils;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.contacts.bean.ContactsInfo;

import butterknife.BindView;
import butterknife.OnClick;

public class AddContactsActivity extends BaseHeadActivity {
    @BindView(R2.id.et_phone_no)
    EditText et_phone_no;
    @BindView(R2.id.iv_contacts)
    ImageView iv_contacts;
    @BindView(R2.id.et_name)
    EditText et_name;
    @BindView(R2.id.btn_ok)
    Button btn_ok;

    @Override
    public int layoutId() {
        return R.layout.activity_add_contacts;
    }

    @Override
    protected void initUIData() {
        setTvTitle("添加联系人");
    }

    @OnClick({R2.id.btn_ok,R2.id.iv_contacts})
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
        }else if(view.getId() == R.id.iv_contacts){
            PermissionCheckUtils.requestPermissions(mActivity,Constants.REQUEST_PERMISSION,new String[]{"android.Manifest.permission.READ_CONTACTS)"});
            startActivityForResult(MyIntent.getToContact(), Common.DECODE_SUCCEEDED);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Common.DECODE_SUCCEEDED) {
            if (data != null) {
                Uri uri = data.getData();
                String phoneNum = null;
                String contactName = null;
                // 创建内容解析者
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = null;
                if (uri != null) {
                    cursor = contentResolver.query(uri,
                            new String[]{"display_name","data1"}, null, null, null);
                }
                while (cursor.moveToNext()) {
                    contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                cursor.close();
                //  把电话号码中的  -  符号 替换成空格
                if (phoneNum != null) {
                    phoneNum = phoneNum.replaceAll("-", " ");
                    // 空格去掉  为什么不直接-替换成"" 因为测试的时候发现还是会有空格 只能这么处理
                    phoneNum= phoneNum.replaceAll(" ", "");
                }

                et_name.setText(contactName);
                et_phone_no.setText(phoneNum);
            }
        }
    }
}