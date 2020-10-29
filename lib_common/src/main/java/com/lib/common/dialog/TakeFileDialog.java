package com.lib.common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.lib.common.R;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.FileUtils;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.baseUtils.permssion.PermissionCheckUtils;
import com.lib.common.mediaTool.SelectPictureUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


public class TakeFileDialog extends Dialog implements View.OnClickListener {
    private Activity context;

    public TakeFileDialog(Activity context) {
        super(context, R.style.DialogTheme);
        setContentView(R.layout.dlg_share_save);
        this.context = context;
        setWindow();
        TextView tv_title = findViewById(R.id.tv_title);
        TextView btn_frist = findViewById(R.id.btn_frist);
        TextView btn_save = findViewById(R.id.btn_save);
        TextView btn_share = findViewById(R.id.btn_share);
        tv_title.setText("请选择");
        tv_title.setVisibility(View.VISIBLE);
        btn_frist.setText("选照片");
        btn_frist.setVisibility(View.VISIBLE);
        btn_save.setText("拍照");
        btn_share.setText("录视频");
        findViewById(R.id.btn_frist).setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_share).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    private void setWindow() {
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = window.getAttributes();
        p.width = d.getWidth();
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(p);
        PermissionCheckUtils.requestPermissions(context, Constants.REQUEST_CODE, Common.permissionList); // 动态请求权限
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_frist) {
            SelectPictureUtils.getByAlbum(context);
        } else if (i == R.id.btn_share) {
            // TODO: 2018/9/28 这里有一个shareSDK的封装
            UIHelper.ToastMessage(context, "还没写");
        } else if (i == R.id.btn_save) {
            SelectPictureUtils.getByCamera(context);
        } else if (i == R.id.btn_cancel) {
            dismiss();

        }
    }


}
