package com.qyai.baidumap.postion;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.common.recyclerView.SimpleRecAdapter;
import com.qyai.baidumap.R;
import com.qyai.baidumap.R2;
import com.qyai.baidumap.postion.bean.EnclosureInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEnclosureAdapter extends SimpleRecAdapter<EnclosureInfo, AddEnclosureAdapter.ViewHolder> {
    public AddEnclosureAdapter(Context context) {
        super(context);
    }

    @Override
    public AddEnclosureAdapter.ViewHolder newViewHolder(View itemView) {
        return new AddEnclosureAdapter.ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_enclosure;
    }

    @Override
    public void onBindViewHolder(AddEnclosureAdapter.ViewHolder holder, int position) {
          EnclosureInfo info=data.get(position);
          holder.et_enclosure.setText(info.getName());
          holder.tv_adress.setText(info.getAdress());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.et_enclosuer)
        EditText et_enclosure;
        @BindView(R2.id.tv_adress)
        TextView tv_adress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
