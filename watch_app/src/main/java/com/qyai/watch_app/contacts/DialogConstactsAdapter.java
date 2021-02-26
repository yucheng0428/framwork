package com.qyai.watch_app.contacts;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.common.recyclerView.SimpleRecAdapter;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.contacts.bean.ContactsInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogConstactsAdapter extends SimpleRecAdapter<ContactsInfo, DialogConstactsAdapter.ViewHolder> {
    public DialogConstactsAdapter(Context context) {
        super(context);
    }

    @Override
    public DialogConstactsAdapter.ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_dialog_contacts;
    }

    @Override
    public void onBindViewHolder(DialogConstactsAdapter.ViewHolder holder, int position) {
        ContactsInfo bean = data.get(position);
        holder.tv_title_sort.setText("第" +(position+1)+"紧急联系人");
        holder.tv_phone_no.setText(bean.getPhoneNo());
        holder.tv_name.setText(bean.getName());
        holder.iv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecItemClick().onItemClick(position,bean,1,holder);
            }
        });
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {

        @BindView(R2.id.tv_title_sort)
        TextView tv_title_sort;
        @BindView(R2.id.tv_phone_no)
        TextView tv_phone_no;
        @BindView(R2.id.tv_name)
        TextView tv_name;
        @BindView(R2.id.iv_phone)
        ImageView iv_phone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
