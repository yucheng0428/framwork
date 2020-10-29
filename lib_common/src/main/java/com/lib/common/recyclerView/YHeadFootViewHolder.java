package com.lib.common.recyclerView;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class YHeadFootViewHolder  extends RecyclerView.ViewHolder {
    public YHeadFootViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        itemView.setTag(this);
    }
}
