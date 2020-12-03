package com.qyai.watch_app.contacts;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.common.baseUtils.DateUtils;
import com.lib.common.recyclerView.SimpleRecAdapter;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.contacts.bean.ContactsInfo;
import com.qyai.watch_app.message.bean.MessageBean;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsAdapter extends SimpleRecAdapter<ContactsInfo, ContactsAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    public final static int add = 101;
    public final static int delect = 102;
    public final static int sort = 103;

    public ContactsAdapter(Context context) {
        super(context);
    }

    @Override
    public ContactsAdapter.ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_contacts;
    }

    @Override
    public void onBindViewHolder(ContactsAdapter.ViewHolder holder, int position) {
        ContactsInfo bean = data.get(position);
        if (bean.getType() == 1) {
            holder.layout_view.setVisibility(View.GONE);
            holder.tv_add.setVisibility(View.VISIBLE);
        } else {
            holder.layout_view.setVisibility(View.VISIBLE);
            holder.tv_add.setVisibility(View.GONE);
        }
        holder.tv_title_sort.setText("第" +(position+1)+"紧急联系人");
        holder.tv_phone_no.setText(bean.getPhoneNo());
        holder.tv_name.setText(bean.getName());
        if (position == 0) {
            holder.iv_sort.setImageResource(R.mipmap.icon_down);
        } else {
            holder.iv_sort.setImageResource(R.mipmap.icon_up);
        }
        holder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    getRecItemClick().onItemClick(position, data.get(position), add, holder);
                }
            }
        });
        holder.tv_delect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecItemClick().onItemClick(position, data.get(position), delect, holder);
            }
        });
        holder.iv_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    getRecItemClick().onItemClick(position, data.get(position), sort, holder);
                }
            }
        });
    }

    @Override
    public void onItemMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        if (fromPosition < data.size() && toPosition < data.size()) {
            //交换数据位置
            Collections.swap(data, fromPosition, toPosition);
            //刷新位置交换
            notifyItemMoved(fromPosition, toPosition);
        }
        //移动过程中移除view的放大效果
        onItemClear(viewHolder);
    }

    @Override
    public void onItemDissmiss(RecyclerView.ViewHolder viewHolder) {
        int position = viewHolder.getAdapterPosition();
        data.remove(position); //移除数据
        notifyItemRemoved(position);//刷新数据移除
    }

    @Override
    public void onItemSelect(RecyclerView.ViewHolder viewHolder) {
//当拖拽选中时放大选中的view
        viewHolder.itemView.setScaleX(1.2f);
        viewHolder.itemView.setScaleY(1.2f);
    }

    @Override
    public void onItemClear(RecyclerView.ViewHolder viewHolder) {
        //拖拽结束后恢复view的状态
        viewHolder.itemView.setScaleX(1.0f);
        viewHolder.itemView.setScaleY(1.0f);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_view)
        View layout_view;

        @BindView(R2.id.iv_sort)
        ImageView iv_sort;
        @BindView(R2.id.tv_title_sort)
        TextView tv_title_sort;
        @BindView(R2.id.tv_phone_no)
        TextView tv_phone_no;
        @BindView(R2.id.tv_name)
        TextView tv_name;
        @BindView(R2.id.tv_delect)
        TextView tv_delect;
        @BindView(R2.id.tv_add)
        TextView tv_add;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
