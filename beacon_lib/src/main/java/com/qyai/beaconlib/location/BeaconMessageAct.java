package com.qyai.beaconlib.location;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.common.base.BaseHeadActivity;
import com.lib.common.recyclerView.ItemAdapter;
import com.lib.common.recyclerView.RecyclerItemCallback;
import com.qyai.beaconlib.R;
import com.qyai.beaconlib.R2;
import com.qyai.beaconlib.bean.Beacon;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
@Route(path = "/beacon/BeaconMessageAct")
public class BeaconMessageAct extends BaseHeadActivity {
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    BeaconMessageAdapter adapter;
    @Override
    public int layoutId() {
        return R.layout.activity_beacon_message;
    }

    @Override
    protected void initUIData() {
        EventBus.getDefault().register(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BeaconMessageAdapter(mActivity);
        recyclerView.setAdapter(adapter);
        adapter.setRecItemClick(new RecyclerItemCallback<Beacon, ItemAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, Beacon model, int tag, ItemAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void even(List<Beacon> list){
        adapter.clearData();
        if(list!=null&&list.size()>0){
            adapter.addData(list);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
