package com.qyai.main.bracelet;

import android.content.Context;
import android.view.View;

import com.qyai.main.bracelet.bean.DeviceBean;


public class DeviceAdapter extends ItemAdapter<DeviceBean> {
    public static final int TAG = 1;
    public DeviceAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder holder, int position) {
        final DeviceBean msgModel=data.get(position);
        holder.textView.setText("设备名:"+msgModel.getDeviceName()+"\n设备Mac:"+msgModel.getDeviceMac());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getRecItemClick()!=null){
                    getRecItemClick().onItemClick(position, data.get(position), TAG, holder);
                }
            }
        });
    }
}
