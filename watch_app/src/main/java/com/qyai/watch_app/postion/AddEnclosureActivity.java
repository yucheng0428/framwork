package com.qyai.watch_app.postion;


import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.common.base.BaseHeadActivity;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.postion.bean.EnclosureInfo;

import butterknife.BindView;

/**
 * 添加围栏
 */
public class AddEnclosureActivity extends BaseHeadActivity {

    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    AddEnclosureAdapter addEnclosureAdapter;

    @Override
    public int layoutId() {
        return R.layout.activity_add_enclosure;
    }

    @Override
    protected void initUIData() {
        setTvTitle(getIntent().getStringExtra("title"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        addEnclosureAdapter = new AddEnclosureAdapter(mActivity);
        addEnclosureAdapter.setData(EnclosureInfo.getEnclosureInfoList());
        recyclerView.setAdapter(addEnclosureAdapter);
        setIvRightSrc(R.mipmap.icon_right_add);
        hideIvRight(View.VISIBLE);
    }

    @Override
    public void setOnClickIvRight() {
        super.setOnClickIvRight();
        Intent intent = new Intent(mActivity, PostionActivity.class);
        startActivity(intent);
    }
}