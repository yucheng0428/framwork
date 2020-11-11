package com.qyai.main.bracelet;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.common.recyclerView.SimpleRecAdapter;
import com.qyai.main.R;


public abstract class ItemAdapter<T> extends SimpleRecAdapter<T, ItemAdapter.ViewHolder> {
    public static final int TAG = 1;
    public ItemAdapter(Context context) {
        super(context);
    }
    @Override
    public ItemAdapter.ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_text_check;
    }

    public abstract void onBindViewHolder(ItemAdapter.ViewHolder holder, int position);

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(com.lib.common.R.id.tv);
        }
    }
}
