package com.qyai.watch_app.xiaqu;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.common.recyclerView.SimpleRecAdapter;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XiaQuAdapter extends SimpleRecAdapter<XiaQuInfo, XiaQuAdapter.ViewHolder> {


    public XiaQuAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new XiaQuAdapter.ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_xiaqu_view;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       XiaQuInfo info=data.get(position);
       holder.tv_name.setText(info.getName());
       holder.tv_sex.setText(info.getSex());
       if(info.getStat().equals("1")){
           holder.tv_stat.setText("健康特征正常！");
           holder.tv_stat.setTextColor(context.getResources().getColor(R.color.x_green));
       }else {
           holder.tv_stat.setText("健康特征异常！");
           holder.tv_stat.setTextColor(context.getResources().getColor(R.color.x_red));
       }
       holder.iv_head.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (getRecItemClick() != null) {
                   getRecItemClick().onItemClick(position, data.get(position), 4, holder);
               }
           }
       });
       holder.iv_phone.setOnClickListener(new View.OnClickListener() {
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
               if (getRecItemClick() != null) {
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
        @BindView(R2.id.iv_head)
        ImageView iv_head;
        @BindView(R2.id.iv_postion)
        ImageView iv_postion;
        @BindView(R2.id.iv_phone)
        ImageView iv_phone;
        @BindView(R2.id.tv_stat)
        TextView tv_stat;
        @BindView(R2.id.tvsex)
        TextView tv_sex;
        @BindView(R2.id.tv_name)
        TextView tv_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

