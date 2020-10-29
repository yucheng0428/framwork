package com.lib.common.recyclerView;

import androidx.recyclerview.widget.RecyclerView;


public class YDataObserver extends RecyclerView.AdapterDataObserver {
    private YRecyclerAdapter adapter;

    public YDataObserver(YRecyclerAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onChanged() {
        super.onChanged();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        super.onItemRangeChanged(positionStart, itemCount);
        adapter.notifyItemRangeChanged(positionStart + adapter.getHeaderSize(), itemCount);
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
        adapter.notifyItemRangeInserted(positionStart + adapter.getHeaderSize(), itemCount);
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        adapter.notifyItemRangeChanged(fromPosition + adapter.getHeaderSize(), itemCount + adapter.getHeaderSize() + toPosition);
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        super.onItemRangeRemoved(positionStart, itemCount);
        adapter.notifyItemRangeRemoved(positionStart + adapter.getHeaderSize(), itemCount);
    }
}
