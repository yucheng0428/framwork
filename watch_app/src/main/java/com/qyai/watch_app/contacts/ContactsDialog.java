package com.qyai.watch_app.contacts;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.common.baseUtils.PhoneUtils;
import com.lib.common.recyclerView.RecyclerItemCallback;
import com.qyai.watch_app.R;
import com.qyai.watch_app.contacts.bean.ContactsInfo;

import java.util.List;

public class ContactsDialog extends Dialog {
    public Activity context;
    public DialogConstactsAdapter adapter;
    public List<ContactsInfo> contactsInfos;

    public ContactsDialog(@NonNull Activity context,List<ContactsInfo> list) {
        super(context, R.style.DialogTheme);
        this.context=context;
        this.contactsInfos=list;
        setContentView(R.layout.dialog_contacts_view);
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new DialogConstactsAdapter(context);
        adapter.setData(contactsInfos);
        adapter.setRecItemClick(new RecyclerItemCallback<ContactsInfo, DialogConstactsAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, ContactsInfo model, int tag, DialogConstactsAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                PhoneUtils.dialPhone(context, model.getPhoneNo());
            }
        });
        TextView textView=findViewById(R.id.id_bt_cancel);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        recyclerView.setAdapter(adapter);
        setWindow();
    }


    public void setWindow() {
        // TODO Auto-generated method stub
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置

        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = window.getAttributes();
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(p);
        window.setWindowAnimations(android.R.style.Animation);
        setCanceledOnTouchOutside(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void show() {
        try{
            if(context instanceof Activity && ((Activity)context).isFinishing())
                return;
            super.show();
        }catch (Exception e){}
    }
}
