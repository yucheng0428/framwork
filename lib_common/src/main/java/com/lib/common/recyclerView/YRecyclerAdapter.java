package com.lib.common.recyclerView;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class YRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Cloneable {
    private static final int ITEM_TYPE_HEADER = Integer.MAX_VALUE + 1;        //headerView
    private static final int ITEM_TYPE_NORMAL = ITEM_TYPE_HEADER + 1 * 1000;        //normalItem
    private static final int ITEM_TYPE_FOOTER = ITEM_TYPE_HEADER + 2 * 1000;        //footerView

    private RecyclerView.Adapter baseAdapter;
    private List<View> headerViewList = new ArrayList<View>();
    private List<View> footerViewList = new ArrayList<View>();
    private Map<Class, Integer> adapterClassMap;

    private RecyclerView.AdapterDataObserver observer = new YDataObserver(this);

    public YRecyclerAdapter(RecyclerView.Adapter baseAdapter) {
        this(baseAdapter, null, null);
    }

    public YRecyclerAdapter(RecyclerView.Adapter adapter, List<View> headerViewList, List<View> footerViewList) {
        if (headerViewList != null && headerViewList.size() > 0) {
            this.headerViewList.addAll(headerViewList);
        }
        if (footerViewList != null && footerViewList.size() > 0) {
            this.footerViewList.addAll(footerViewList);
        }
        if (adapterClassMap == null) {
            adapterClassMap = new HashMap<Class, Integer>();
        }
        setAdapter(adapter);
    }

    private void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter == null) return;

        if (baseAdapter != null) {
            baseAdapter.unregisterAdapterDataObserver(observer);
        }
        baseAdapter = adapter;
        Class clazz = baseAdapter.getClass();
        if (!adapterClassMap.containsKey(clazz)) {
            putClass(clazz);
        }
        baseAdapter.registerAdapterDataObserver(observer);
    }

    private void putClass(Class clazz) {
        adapterClassMap.put(clazz, Integer.valueOf(ITEM_TYPE_FOOTER + 100 * adapterClassMap.size()));
    }

    private int getMapClassIntValue() {
        return adapterClassMap.get(baseAdapter.getClass()).intValue();
    }

    /**
     * 数据是否为空
     *
     * @return
     */
    private boolean isEmpty() {
        return getDataCount() == 0;
    }

    /**
     * 添加headview
     *
     * @param pos
     * @param view
     * @return
     */
    public boolean addHeadView(int pos, View view) {
        if (view == null || headerViewList.contains(view)) return false;

        headerViewList.add(pos, view);
        notifyItemInserted(headerViewList.indexOf(Integer.valueOf(pos)));
        return true;
    }

    /**
     * 添加headView
     *
     * @param view
     * @return
     */
    public boolean addHeadView(View view) {
        return addHeadView(getHeaderSize(), view);
    }

    /**
     * 添加footView
     *
     * @param pos
     * @param view
     * @return
     */
    public boolean addFootView(int pos, View view) {
        if (view == null || footerViewList.contains(view)) return false;

        footerViewList.add(pos, view);
        notifyItemInserted(getHeaderSize() + getDataCount() + pos);

        return true;
    }

    public boolean addFootView(View view) {
        return addFootView(getFooterSize(), view);
    }

    /**
     * 删除headView
     *
     * @param view
     * @return
     */
    public boolean removeHeadView(View view) {
        if (view == null && !headerViewList.contains(view)) return false;

        int pos = headerViewList.indexOf(view);
        boolean result = headerViewList.remove(view);

        if (result) {
            notifyItemRemoved(pos);
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
        }
        return result;
    }

    public boolean removeAllHeadersView(){
        headerViewList.clear();
        notifyDataSetChanged();
        return true;
    }

    public boolean removeAllFootView(){
        footerViewList.clear();
        notifyDataSetChanged();
        return true;
    }

    /**
     * 删除footView
     *
     * @param view
     * @return
     */
    public boolean removeFootView(View view) {
        boolean result = false;

        if (!footerViewList.contains(view)) result = false;

        int pos = footerViewList.indexOf(view);
        if (pos > -1) {
            result = footerViewList.remove(view);
        }
        if (result) {
            notifyItemRemoved(1 + pos + getHeaderSize() + getDataCount());
        }
        return result;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int index = 0;
        if (viewType < ITEM_TYPE_HEADER + getHeaderSize()) {
            index = viewType + ITEM_TYPE_HEADER;
            return new YHeadFootViewHolder(headerViewList.get(index));
        }
        if (viewType < ITEM_TYPE_NORMAL + getDataCount()) {
            index = viewType - ITEM_TYPE_NORMAL;
            if (index < footerViewList.size()) {
                return new YHeadFootViewHolder(footerViewList.get(index));
            }
        }
        index = viewType - getMapClassIntValue();
        return baseAdapter.onCreateViewHolder(parent, index);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int headViewSize = getHeaderSize();
        if (position >= headViewSize && (position < headViewSize + getDataCount())) {
            baseAdapter.onBindViewHolder(holder, position - headViewSize);
        }
    }

    @Override
    public int getItemCount() {
        return getHeaderSize() + getDataCount() + getFooterSize();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getHeaderSize()) {
            return ITEM_TYPE_HEADER + position;
        }
        if (position < getHeaderSize() + getDataCount()) {
            return getMapClassIntValue() + baseAdapter.getItemViewType(position - getHeaderSize());
        }
        return ITEM_TYPE_NORMAL + position - getHeaderSize() - getDataCount();
    }

    /**
     * 判断当前位置是否是header or footer
     *
     * @param position
     * @return
     */
    public boolean isHeaderOrFooter(int position) {
        return position < getHeaderSize() || position >= getHeaderSize() + getDataCount();
    }

    public int getDataCount() {
        return baseAdapter.getItemCount();
    }

    /**
     * 获取headView的数量
     *
     * @return
     */
    public int getHeaderSize() {
        return headerViewList != null ? headerViewList.size() : 0;
    }

    /**
     * 获取footerView的数量
     *
     * @return
     */
    public int getFooterSize() {
        return footerViewList != null ? footerViewList.size() : 0;
    }

    public RecyclerView.Adapter getAdapter() {
        return baseAdapter;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        YRecyclerAdapter adapter = new YRecyclerAdapter(getAdapter());
        adapter.headerViewList = headerViewList;
        adapter.footerViewList = footerViewList;
        return adapter;
    }

    /**
     * 获取headViews
     *
     * @return
     */
    public List<View> getHeaderViewList() {
        return headerViewList;
    }

    /**
     * 获取footViews
     *
     * @return
     */
    public List<View> getFooterViewList() {
        return footerViewList;
    }
}
