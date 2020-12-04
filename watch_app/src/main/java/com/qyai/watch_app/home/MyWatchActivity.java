package com.qyai.watch_app.home;


import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.recyclerView.RecyclerItemCallback;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.bind.BindActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MyWatchActivity extends BaseHeadActivity {
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    MyWatchAdapter adapter;
    @BindView(R2.id.btn_ok)
    Button btn_ok;

    @Override
    public int layoutId() {
        return R.layout.activity_my_watch;
    }

    @Override
    protected void initUIData() {
        setTvTitle(getIntent().getStringExtra("title"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyWatchAdapter(mActivity);
        recyclerView.setAdapter(adapter);
        adapter.setData(MyWatchInfo.getMyWatchInfo());
        adapter.setRecItemClick(new RecyclerItemCallback<MyWatchInfo, MyWatchAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, MyWatchInfo model, int tag, MyWatchAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (tag) {
                    case 1:
                        UIHelper.ToastMessage(mActivity, "1是解绑");
                        break;
                    case 2:
                        UIHelper.ToastMessage(mActivity, "2是切换");
                        break;
                }
            }
        });
    }

    @OnClick({R2.id.btn_ok})
    public void onClick(View view) {
        if(view.getId()==R.id.btn_ok){
            Intent intent=new Intent(mActivity, BindActivity.class);
            startActivity(intent);
            mActivity.finish();
        }
    }
}