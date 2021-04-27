/*
 * 苏州旭日朝晖软件有限公司
 * 
 * --------------+--------------+--------------- 更新时间 更新者 更新内容 --------------+--------------+--------------- 2015年5月6日
 * niudaye 创建
 * 
 * 文 件 名: AsyncListAdapter 版 权: SuZhou XuRiSoft. Copyright 2015-2025 开发人员: niudaye
 */
package com.lib.common.widgt.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lib.common.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * <列表适配器抽象基类> <功能详细描述>
 * @author 姓名
 * @version [版本号, 2015年5月9日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class AsyncListAdapter<T> extends BaseAdapter
{
    /**
     * 数据项
     */
    public List<T> mList;
    
    /**
     * 下拉刷新标签
     */
    public boolean refreshFlag = false;
    
    /**
     * 加载更多标签
     */
    public boolean loadMoreFlag = false;
    
    /**
     * Context 对象
     */
    protected Context context;
    
    /**
     * LayoutInflater 对象
     */
    public LayoutInflater inflater;
    
    /**
     * layout 布局
     */
    private int layout;
    
    /**
     * 列表为空标志
     */
    public boolean isEmpty = false;
    
    /**
     * 列表加载没有更多
     */
    public boolean noMore = false;
    
    /**
     * 列表空字串
     */
    private int emptyString = R.string.listEmpty;
    
    // private int emptyIco = R.drawable.search_empty;
    
    /**
     * 列表加载数量
     */
    public int PAGE_SIZE = 10;
    
    /**
     * 当前绘制的item在列表中的位置
     */
    protected int position;
    
    public int pageNo = 1;
    
    private final int EMPTY_LIST_SIZE = 1;


    /**
     * 列表ItemView
     */
    public AsyncListAdapter(Context context, int layout)
    {
        // 继承父类方法
        super();
        // 初始化Context对象
        this.context = context;
        // 初始化数据列表
        mList = new ArrayList<T>();
        // 初始化布局resource
        this.layout = layout;
        // 初始化列表非空
        this.isEmpty = false;
        // 初始化LayoutInflater
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override
    public int getCount()
    {
        // 获取列表是否为空
        int size = mList.size();
        // 空页面的判定
        if (size == 0 && isEmpty)
        {
            return EMPTY_LIST_SIZE;
        }
        // 返回真实的list size
        return mList.size();
    }
    
    @Override
    public T getItem(int position)
    {
        // 判断数据越界
        if (position < mList.size())
        {
            // 未越界，返回真实对象
            return mList.get(position);
        }
        // 越界，返回空
        return null;
    }
    
    // 返回当前id
    @Override
    public long getItemId(int item)
    {
        return item;
    }
    
    // protected abstract void setViewHolder(V holder,View v);
    // 抽象方法，ViewHolder方法
    public abstract ViewInjHolder<T> getViewHolder();
    
    // 设置内容项
    
    // View 的绑定
    // public abstract void bindView(V holder, View v);
    
    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View v, ViewGroup arg2)
    {
        // 当前位置
        this.position = position;
        // item 的View
        View view = null;
        // item 绑定数据
        ViewInjHolder<T> holder = null;
        // 列表数据的判断
        int size = mList.size();
        if (size == 0 && isEmpty)
        {
            // 使用空列表页
            view = inflater.inflate(R.layout.list_empty, null);
            // 设置空列表文字
            ((TextView)view.findViewById(R.id.tv_text)).setText(emptyString);
            view.setOnClickListener(null);
            return view;
        }
        
        // 数据抽象的对象
        T record = getItem(position);
        if (v == null || v.getTag() == null)
        {
            // 优化内存的判断
            v = inflater.inflate(layout, null);
            // 取View模块对象
            holder = getViewHolder();

            ButterKnife.bind(holder,v);
            // 绑定View
            // bindView(holder, v);
            // 设置tag标签
            v.setTag(holder);
        }
        else
        {
            // 缓存读取
            holder = (ViewInjHolder<T>)v.getTag();
        }
        // item列表项的view
        view = v;
        // 设置内容
        holder.setContent(record, position);
        return view;
    }
    
    /**
     * 设置空字串
     * @param emptyString
     * @see [类、类#方法、类#成员]
     */
    public void setEmptyString(int emptyString)
    {
        this.emptyString = emptyString;
    }
    
    // public void setEmptyIco(int emptyIco)
    // {
    // this.emptyIco = emptyIco;
    // }
    
   public abstract class ViewInjHolder<T>{
        public abstract void setContent(T record,int position);
    }
}
