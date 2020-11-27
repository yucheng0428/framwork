package com.qyai.main.bracelet;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.common.baseUtils.DateUtils;
import com.lib.common.recyclerView.SimpleRecAdapter;
import com.qyai.main.R;
import com.qyai.main.R2;
import com.qyai.main.bracelet.bean.MessageBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageAdapter extends SimpleRecAdapter<MessageBean, MessageAdapter.ViewHolder> {
    public MessageAdapter(Context context) {
        super(context);
    }

    @Override
    public MessageAdapter.ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_message;
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder holder, int position) {
        MessageBean bean = data.get(position);
        holder.tv_name.setText(bean.getTyepName() + ":");
        holder.tv_value.setText(bean.getTypeValue());
        if (bean.getTime() != null) {
            holder.tv_time.setVisibility(View.VISIBLE);
            holder.tv_time.setText(""+ DateUtils.timedate(bean.getTime()));
        }else {
            holder.tv_time.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    getRecItemClick().onItemClick(position, data.get(position), 1, holder);
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.tv_Name)
        TextView tv_name;
        @BindView(R2.id.tv_Value)
        TextView tv_value;
        @BindView(R2.id.tv_Time)
        TextView tv_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
