package com.lib.common.pageMvp;

import android.content.Context;

import com.lib.common.BaseMvp.view.BaseView;
import com.lib.common.recyclerView.SimpleRecAdapter;
import com.lib.common.recyclerView.YRecyclerContentLayout;



public interface PageView extends BaseView {
    /**
     * 获取适配器
     */
    SimpleRecAdapter getPageAdapter();

    /**
     * 获取列控件
     *
     * @return
     */
    YRecyclerContentLayout getContentLayout();


    /**
     * 获取Contenxt对象
     *
     * @return
     */
    Context getFragMentContext();


    String getUrl();


    int startPageNo();


    /**
     * 设置标题
     * @param titleMsg
     */
    void setTitleMsg(String titleMsg);


    /**
     * 显示添加按钮
     * @param flag
     */
    void setHidTvRight(boolean flag);


    void finshView();

}
