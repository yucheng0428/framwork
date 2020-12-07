package com.qyai.watch_app.bind;


import android.Manifest;
import android.content.Intent;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.IntentKey;
import com.lib.common.baseUtils.LogUtil;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.baseUtils.permssion.PermissionCheckUtils;
import com.lib.common.netHttp.HttpServiec;
import com.lib.common.netHttp.OnHttpCallBack;
import com.lib.common.scanning.android.CaptureActivity;
import com.lib.common.scanning.bean.ZxingConfig;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/watch/bindView")
public class BindActivity extends BaseHeadActivity {
    @BindView(R2.id.et_no)
    EditText et_no;
    @BindView(R2.id.iv_scan)
    ImageView iv_scan;
    @BindView(R2.id.btn_ok)
    Button btn_ok;
    String[] permissions= new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
    @Override
    public int layoutId() {
        return R.layout.activity_bind;
    }

    @Override
    protected void initUIData() {
       setTvTitle("绑定设备");
    }

    @OnClick({R2.id.iv_scan, R2.id.btn_ok})
    public void onClick(View view) {
        if (view.getId() == R.id.iv_scan) {
            if(!PermissionCheckUtils.lacksPermission(mActivity, "android.permission.CAMERA")){
                Intent intent = new Intent(mActivity, CaptureActivity.class);
                /*ZxingConfig是配置类
                 *可以设置是否显示底部布局，闪光灯，相册，
                 * 是否播放提示音  震动
                 * 设置扫描框颜色等
                 * 也可以不传这个参数
                 * */
                ZxingConfig config = new ZxingConfig();
                config.setPlayBeep(true);//是否播放扫描声音 默认为true
                config.setShake(true);//是否震动  默认为true
                config.setDecodeBarCode(false);//是否扫描条形码 默认为true
                config.setReactColor(R.color.white);//设置扫描框四个角的颜色 默认为淡蓝色
                config.setFrameLineColor(R.color.white);//设置扫描框边框颜色 默认无色
                config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                intent.putExtra(Common.INTENT_ZXING_CONFIG, config);
                startActivityForResult(intent, IntentKey.SCAN_CODE);
            }else {
                PermissionCheckUtils.requestPermissions(mActivity, Constants.REQUEST_CODE,permissions); // 动态请求权限
            }
        } else if (view.getId() == R.id.btn_ok) {
            if (SPValueUtil.isEmpty(et_no.getText().toString())) {
                bindWatch(et_no.getText().toString());
            } else {
                UIHelper.ToastMessage(mActivity, "请输入正确编码");
            }
        }
    }

    public void bindWatch(String str) {
        Map<Object, Object> para = new HashMap<>();
        para.put("codeId", str);
        HttpServiec.getInstance().postFlowableMap(100, "url", para, new OnHttpCallBack<String>() {
            @Override
            public void onSuccessful(int id, String o) {

            }

            @Override
            public void onFaild(int id, String o, String err) {

            }
        }, String.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == IntentKey.SCAN_CODE) {
            if (data != null) {
                String content = data.getStringExtra(Common.CODED_CONTENT);
                LogUtil.e("Common.REQUEST_CODE_SCAN", content);
                if (SPValueUtil.isEmpty(content)) {
                    UIHelper.ToastMessage(mActivity, "二维码回调处理" + content);
                } else {
                    UIHelper.ToastMessage(mActivity, "此功能无法受理！");
//                    showErrMsg(content);
                }
            }
        }
    }
}