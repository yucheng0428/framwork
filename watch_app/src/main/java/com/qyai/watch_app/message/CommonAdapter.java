package com.qyai.watch_app.message;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lib.common.baseUtils.baseModle.GankResults;
import com.qyai.watch_app.R;
import com.qyai.watch_app.message.bean.CommonResult;

import java.util.ArrayList;
import java.util.List;

public class CommonAdapter extends BaseAdapter {

    List<CommonResult.DataBean> data = new ArrayList<>();
    Context context;

    public CommonAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CommonResult.DataBean> list) {
        data.addAll(list);
        notifyDataSetChanged();
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
        text.setText(data.get(position).getContent());


        return view;
    }
}
