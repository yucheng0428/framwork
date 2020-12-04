package com.qyai.watch_app.message;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.common.baseUtils.DateUtils;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.recyclerView.SimpleRecAdapter;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.message.bean.MessageBean;

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
        holder.tv_title.setText(bean.getTitleMsg() + ":");
        holder.tv_content.setText(bean.getContent());
        if (bean.getTime() != null) {
            holder.tv_time.setVisibility(View.VISIBLE);
            holder.tv_time.setText(""+ DateUtils.timedate(bean.getTime()));
        }else {
            holder.tv_time.setText( DateUtils.getCurrentTime_Today());
        }
        if(SPValueUtil.isEmpty(bean.getTypeValue())){
            if(bean.getTypeValue().equals("1")){
                holder.iv_icon.setImageResource(R.mipmap.icon_message_blue);
            }else if(bean.getTypeValue().equals("2")){
                holder.iv_icon.setImageResource(R.mipmap.icon_message_orange);
            }
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
        @BindView(R2.id.iv_icon)
        ImageView iv_icon;
        @BindView(R2.id.tv_time)
        TextView tv_time;
        @BindView(R2.id.tv_title)
        TextView tv_title;
        @BindView(R2.id.tv_content)
        TextView tv_content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
