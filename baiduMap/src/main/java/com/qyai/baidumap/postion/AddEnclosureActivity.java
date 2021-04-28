package com.qyai.baidumap.postion;


import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.Common;
import com.lib.common.recyclerView.RecyclerItemCallback;
import com.qyai.baidumap.R;
import com.qyai.baidumap.R2;
import com.qyai.baidumap.postion.bean.EnclosureInfo;

import butterknife.BindView;

/**
 * 添加围栏
 */
@Route(path = Common.ADD_ENCLOSURE)
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
        setTvTitle("智能围栏");
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        addEnclosureAdapter = new AddEnclosureAdapter(mActivity);
        addEnclosureAdapter.setData(EnclosureInfo.getEnclosureInfoList());
        recyclerView.setAdapter(addEnclosureAdapter);
        setIvRightSrc(R.mipmap.icon_right_add);
        hideIvRight(View.VISIBLE);
        addEnclosureAdapter.setRecItemClick(new RecyclerItemCallback<EnclosureInfo, AddEnclosureAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, EnclosureInfo model, int tag, AddEnclosureAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                Intent intent=new Intent(mActivity,PostionActivity.class);
                intent.putExtra("bean",model);
                startActivity(intent);

            }
        });
    }

    @Override
    public void setOnClickIvRight() {
        super.setOnClickIvRight();
        Intent intent = new Intent(mActivity, PostionActivity.class);
        startActivity(intent);
    }
}