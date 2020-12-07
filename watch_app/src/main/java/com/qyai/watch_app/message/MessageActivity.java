package com.qyai.watch_app.message;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.common.base.BaseHeadActivity;
import com.lib.common.recyclerView.RecyclerItemCallback;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.message.bean.MessageBean;

import butterknife.BindView;

public class MessageActivity extends BaseHeadActivity {
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    MessageAdapter adapter;

    @Override
    public int layoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initUIData() {
        setTvTitle("消息");
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MessageAdapter(mActivity);
        recyclerView.setAdapter(adapter);
        adapter.setData(MessageBean.getMessgeList());
        adapter.setRecItemClick(new RecyclerItemCallback<MessageBean, MessageAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, MessageBean model, int tag, MessageAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                Intent intent=new Intent(mActivity,MessageDetailAct.class);
                startActivity(intent);
            }
        });
    }
}