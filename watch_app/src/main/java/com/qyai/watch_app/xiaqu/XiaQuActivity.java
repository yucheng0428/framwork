package com.qyai.watch_app.xiaqu;

import android.content.Intent;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.recyclerView.RecyclerItemCallback;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;

import butterknife.BindView;

public class XiaQuActivity extends BaseHeadActivity {
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    XiaQuAdapter adapter;


    @Override
    public int layoutId() {
        return R.layout.activity_xiaqu;
    }

    @Override
    protected void initUIData() {
        setTvTitle("辖区守护");
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new XiaQuAdapter(mActivity);
        recyclerView.setAdapter(adapter);
        adapter.setData(XiaQuInfo.getContactsInfoList());
        adapter.setRecItemClick(new RecyclerItemCallback<XiaQuInfo, XiaQuAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, XiaQuInfo model, int tag, XiaQuAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (tag) {
                    case 1:
                        //1是点击整item;
                        Intent intent = new Intent(mActivity, PersonDetailActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        //2是点击打电话;
                        UIHelper.ToastMessage(mActivity, "点击打电话");
                        break;
                    case 3:
                        ARouter.getInstance().build("/maplib/MapActivity").navigation();
                        //2是点击打电话;
                        UIHelper.ToastMessage(mActivity, "点击定位");
                        break;
                    case 4:
                        UIHelper.ToastMessage(mActivity, "点击头像放大");
                        break;
                }
            }
        });

    }
}