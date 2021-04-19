package com.qyai.watch_app.contacts;



import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.common.base.BaseHeadActivity;
import com.lib.common.baseUtils.Common;
import com.lib.common.baseUtils.Constants;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.recyclerView.RecyclerItemCallback;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.contacts.bean.ContactsInfo;

import butterknife.BindView;

public class ContactsActivity extends BaseHeadActivity {
    public String title="紧急联系人";
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    ContactsAdapter adapter;

    @Override
    public int layoutId() {
        return R.layout.activity_contacts;
    }

    @Override
    protected void initUIData() {
        title=getIntent().getStringExtra("title");
        setTvTitle(title);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new ContactsAdapter(mActivity);
        recyclerView.setAdapter(adapter);
        adapter.setData(ContactsInfo.getContactsInfoList());
        adapter.setRecItemClick(new RecyclerItemCallback<ContactsInfo, ContactsAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, ContactsInfo model, int tag, ContactsAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (tag){
                    case ContactsAdapter.add:
                        Intent intent =new Intent(mActivity,AddContactsActivity.class);
                        startActivityForResult(intent, Common.REQUEST_CODE);
                        break;
                    case ContactsAdapter.delect:
                        adapter.getDataSource().remove(position);
                        adapter.notifyDataSetChanged();
                        break;
                    case ContactsAdapter.sort:
                        UIHelper.ToastMessage(mActivity,"ContactsAdapter.sort"+position);
                        break;
                }
            }
        });
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        //用Callback构造ItemtouchHelper
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        //调用ItemTouchHelper的attachToRecyclerView方法建立联系
        touchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Common.REQUEST_CODE){
            if(data!=null){
                ContactsInfo info= (ContactsInfo) data.getSerializableExtra("data");
                if(info!=null){
                    adapter.getDataSource().add(adapter.getDataSource().size()-1,info);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}