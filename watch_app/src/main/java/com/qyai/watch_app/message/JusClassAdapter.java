package com.qyai.watch_app.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qyai.watch_app.R;
import com.qyai.watch_app.message.bean.JusClassResult;

import java.util.ArrayList;
import java.util.List;

public class JusClassAdapter extends BaseAdapter {
    List<JusClassResult.DataBean> data = new ArrayList<>();
    Context context;

    public JusClassAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<JusClassResult.DataBean> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }

    public List<JusClassResult.DataBean> getData() {
        return data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        TextView text;
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_text_check, parent, false);
        } else {
            view = convertView;
        }
        text = view.findViewById(R.id.tv);
        text.setText(data.get(position).getName());


        return view;
    }
}
