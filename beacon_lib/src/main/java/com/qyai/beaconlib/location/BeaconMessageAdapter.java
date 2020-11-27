package com.qyai.beaconlib.location;

import android.content.Context;
import android.view.View;

import com.lib.common.baseUtils.DateUtils;
import com.lib.common.recyclerView.ItemAdapter;
import com.qyai.beaconlib.bean.Beacon;

public class BeaconMessageAdapter extends ItemAdapter<Beacon> {
    public static final int TAG = 1;
    public BeaconMessageAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder holder, int position) {
        final Beacon msgModel=data.get(position);
        holder.textView.setText("major:"+msgModel.major+"\n" +
                                "minor:"+msgModel.minor+"\n" +
                                "rssi:"+msgModel.rssi+"\n" +
                                "时间:"+ DateUtils.timedate(msgModel.timeStamp+""));
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
