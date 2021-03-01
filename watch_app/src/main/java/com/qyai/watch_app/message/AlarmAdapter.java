package com.qyai.watch_app.message;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.recyclerView.SimpleRecAdapter;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.message.bean.AlarmInfo;
import com.qyai.watch_app.message.bean.AlarmPushBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlarmAdapter extends SimpleRecAdapter<AlarmPushBean,AlarmAdapter.ViewHolder> {

    public AlarmAdapter(Context context) {
        super(context);
    }


    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new AlarmAdapter.ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_alarm_view;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AlarmPushBean info=data.get(position);
        holder.tv_time.setText(info.getCreateTime());
        holder.tv_title.setText(info.getContent());
        holder.iv_icon.setText(info.getTypeName());
        //处理结果，1已处理，0未处理, 2忽略
            if(info.getDealStatus()==0){
                holder.tv_do.setVisibility(View.VISIBLE);
            }else if(info.getDealStatus()==1){
                holder.tv_do.setVisibility(View.INVISIBLE);
            }
        holder.tv_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    getRecItemClick().onItemClick(position, data.get(position), 2, holder);
                }
            }
        });
            holder.iv_postion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getRecItemClick()!=null){
                        getRecItemClick().onItemClick(position, data.get(position), 3, holder);
                    }
                }
            });
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
        @BindView(R2.id.iv_message_icon)
        TextView iv_icon;
        @BindView(R2.id.tv_time)
        TextView tv_time;
        @BindView(R2.id.tv_message_title)
        TextView tv_title;
        @BindView(R2.id.tv_do)
        TextView tv_do;
        @BindView(R2.id.iv_postion)
        ImageView iv_postion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
