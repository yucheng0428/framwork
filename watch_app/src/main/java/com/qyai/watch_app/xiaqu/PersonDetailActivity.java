package com.qyai.watch_app.xiaqu;


import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.PhoneUtils;
import com.lib.common.dialog.LookBigPictureDialog;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonDetailActivity extends BaseHeadActivity {
    @BindView(R2.id.iv_head)
    ImageView iv_head;
    @BindView(R2.id.iv_phone)
    ImageView iv_phone;
    @BindView(R2.id.iv_postion)
    ImageView iv_postion;
    XiaQuInfo model;
    @Override
    public int layoutId() {
        return R.layout.activity_person_detail;
    }

    @Override
    protected void initUIData() {
        setTvTitle("人员详情");
        model= (XiaQuInfo) getIntent().getSerializableExtra("info");
        if(model!=null){
            Glide.with(mActivity).load(model.imageUrl).placeholder(R.drawable.icon_loadings).skipMemoryCache(true).into(iv_head);
        }
    }

    @OnClick({R2.id.iv_phone, R2.id.iv_postion,R2.id.iv_head})
    public void onClcik(View view) {
        if (view.getId() == R.id.iv_postion) {
            ARouter.getInstance().build("/maplib/MapActivity").navigation();
        } else if (view.getId() == R.id.iv_phone) {
            PhoneUtils.dialPhone(mActivity,"18327307472");
        }else if(view.getId()==R.id.iv_head){
            new LookBigPictureDialog(mActivity,model.imageUrl).show();
        }

    }
}