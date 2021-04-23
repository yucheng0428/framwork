package com.qyai.baidumap;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.services.core.PoiItem;
import com.lib.common.baseUtils.SPValueUtil;
import com.lib.common.recyclerView.SimpleRecAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PosAdapter extends SimpleRecAdapter<PoiItem,PosAdapter.ViewHolder> {
    public PosAdapter(Context context) {
        super(context);
    }

    @Override
    public PosAdapter.ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_text_check;
    }

    @Override
    public void onBindViewHolder(PosAdapter.ViewHolder holder, int position) {
        PoiItem item=data.get(position);
        if(SPValueUtil.isEmpty(item.getAdName())){
            holder.textView.setText(item.getAdName());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.tv)
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
}
