package com.qyai.watch_app.message;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.common.recyclerView.SimpleRecAdapter;
import com.qyai.watch_app.R;
import com.qyai.watch_app.R2;
import com.qyai.watch_app.message.bean.JusClassResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckJustClassIdAdapter extends SimpleRecAdapter<JusClassResult.DataBean, CheckJustClassIdAdapter.ViewHolder> {
    List<JusClassResult.DataBean> cleanList = new ArrayList<>();

    public CheckJustClassIdAdapter(Context context) {
        super(context);
    }

    @Override
    public CheckJustClassIdAdapter.ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_more_text_check;
    }

    @Override
    public void onBindViewHolder(CheckJustClassIdAdapter.ViewHolder holder, int position) {
        JusClassResult.DataBean more = data.get(position);
        holder.tv_name.setText(more.getName());
        if (more.getChildList() != null && more.getChildList().size() > 0) {
            holder.iv_more.setVisibility(View.VISIBLE);
            if (more.openList) {
                holder.iv_more.setImageResource(R.mipmap.iv_close);
            } else {
                holder.iv_more.setImageResource(R.mipmap.iv_open);
            }
        } else {
            holder.iv_more.setVisibility(View.INVISIBLE);
        }
        if (more.checkItem) {
            holder.iv_check.setVisibility(View.VISIBLE);
        } else {
            holder.iv_check.setVisibility(View.INVISIBLE);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(50 * more.leve - 1, 0, 0, 0);
        holder.layout.setLayoutParams(params);
        holder.iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (more.getChildList() != null && more.getChildList().size() > 0) {
                    if (!more.openList) {
                        data.addAll(position + 1, more.getChildList());
                        more.openList = true;
                        notifyDataSetChanged();
                    } else {
                        cleanList.clear();
                        more.openList = false;
                        resetList(more.getChildList());
                        notifyDataSetChanged();
                    }
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecItemClick().onItemClick(position, more, 1, holder);
            }
        });
    }

    //清理展开的列表
    public void resetList(List<JusClassResult.DataBean> dataBeans) {
        for (JusClassResult.DataBean bean : dataBeans) {
            if (bean.openList) {
                bean.openList = false;
            }
            if (bean.getChildList() != null && bean.getChildList().size() > 0) {
                resetList(bean.getChildList());
            }
            removeElement(bean);
        }
    }


    public void resetcheck() {
        for (JusClassResult.DataBean dataBean : data) {
            dataBean.checkItem = false;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.tv)
        TextView tv_name;
        @BindView(R2.id.iv_more)
        ImageView iv_more;
        @BindView(R2.id.iv_check)
        ImageView iv_check;
        @BindView(R2.id.layout_item)
        RelativeLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
