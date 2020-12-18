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

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlarmAdapter extends SimpleRecAdapter<AlarmInfo,AlarmAdapter.ViewHolder> {
    public  int type=1;

    public AlarmAdapter(Context context) {
        super(context);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
        AlarmInfo info=data.get(position);
        if(type==1){
            holder.tv_do.setVisibility(View.GONE);
        }else {
            holder.tv_do.setVisibility(View.VISIBLE);
        }
        holder.tv_time.setText(info.getTime());
        holder.tv_title.setText(info.getTitle());
        if(SPValueUtil.isEmpty(info.getTyep())){
            if(info.getTyep().equals("1")){
                holder.iv_icon.setImageResource(R.mipmap.icon_message_blue);
            }else if(info.getTyep().equals("2")){
                holder.iv_icon.setImageResource(R.mipmap.icon_message_orange);
            }
        }
        holder.tv_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    getRecItemClick().onItemClick(position, data.get(position), 2, holder);
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
        ImageView iv_icon;
        @BindView(R2.id.tv_time)
        TextView tv_time;
        @BindView(R2.id.tv_message_title)
        TextView tv_title;
        @BindView(R2.id.tv_do)
        TextView tv_do;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
