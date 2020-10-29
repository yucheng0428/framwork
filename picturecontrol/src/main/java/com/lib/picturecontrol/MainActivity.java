package com.lib.picturecontrol;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;


import com.lib.common.base.BaseActivity;
import com.lib.picturecontrol.views.AttachView;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R2.id.attachview)
    AttachView attachView;
    /**
     * 附件数量
     */
    @BindView(R2.id.num)
     TextView num;
    @Override
    protected int layoutId() {
        return R.layout.act_main;
    }


    /**
     * attachView.getOnLinePics() 获取集合
     * @param bundle
     */
    @Override
    protected void initUIData(Bundle bundle) {
        attachView.setEditAble(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        attachView.notifyAttachResult(requestCode,resultCode,data);
    }

}
