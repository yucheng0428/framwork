package com.qyai.watch_app.home;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.common.recyclerView.SimpleRecAdapter;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyWatchAdapter extends SimpleRecAdapter<MyWatchInfo, MyWatchAdapter.ViewHolder> {
    public MyWatchAdapter(Context context) {
        super(context);
    }

    @Override
    public MyWatchAdapter.ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_my_watch_view;
    }

    @Override
    public void onBindViewHolder(MyWatchAdapter.ViewHolder holder, int position) {
        MyWatchInfo bean = data.get(position);
        holder.tv_name.setText(bean.getName());
        holder.tv_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getRecItemClick()!=null){
                    getRecItemClick().onItemClick(position,bean,2,holder);
                }
            }
        });
        holder.tv_unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getRecItemClick()!=null){
                    getRecItemClick().onItemClick(position,bean,1,holder);
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.tv_name)
        TextView tv_name;
        @BindView(R2.id.tv_unbind)
        TextView tv_unbind;
        @BindView(R2.id.tv_bind)
        TextView tv_bind;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
