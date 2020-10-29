package com.lib.common.pageMvp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.lib.common.BaseMvp.BaseMvpFragment;
import com.lib.common.R;
import com.lib.common.baseUtils.UIHelper;
import com.lib.common.recyclerView.YRecyclerContentLayout;
import com.lib.common.recyclerView.YRecyclerView;


/**
 * @author yucheng
 * @date 2018/9/27 10:01
 * @Description MVP模式 列表Frament
 */
public abstract class BasePageFragment<T, M extends BasePageModel, P extends PagePersenter<T, M>> extends BaseMvpFragment<PageView, P> implements PageView {
    YRecyclerContentLayout contentLayout;

    @Override
    protected void initUI(Bundle bundle) {
        contentLayout = view.findViewById(R.id.contentLayout);
        getMvpPresenter().setMvpModel(getMvpModel());
        setLayoutManager(contentLayout.getRecyclerView());
        contentLayout.getRecyclerView().setAdapter(getPageAdapter());
        contentLayout.getRecyclerView().setOnRefreshAndLoadMoreListener(new YRecyclerView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                getMvpPresenter().getFragmentMpdel().loadData(startPageNo(), getUrl());
            }

            @Override
            public void onLoadMore(int page) {
                getMvpPresenter().getFragmentMpdel().loadData(page, getUrl());
            }
        });

        contentLayout.loadingView(View.inflate(getFragMentContext(), R.layout.dlg_main_progress, null));
//        contentLayout.getRecyclerView().useDefLoadMoreView();
        getMvpPresenter().getFragmentMpdel().loadData(startPageNo(), getUrl());
    }


    /**
     * 数据操作模型 请求的数据处理都在这个里面做
     *
     * @return
     */
    public abstract M getMvpModel();


    @Override
    public int startPageNo() {
        return 1;
    }

    /**
     * 请求地址
     *
     * @return
     */
    public abstract String getUrl();

    @Override
    public YRecyclerContentLayout getContentLayout() {
        return contentLayout;
    }

    @Override
    public Context getFragMentContext() {
        return mActivity;
    }


    /**
     * 进度框显示
     */
    @Override
    public void showLodingDialog() {
        mActivity.showProgress();
    }

    /**
     * 进度框隐藏
     */
    @Override
    public void hidLodingDialog() {
        mActivity.hindProgress();
    }

    /**
     * toast提示
     *
     * @param msg
     */
    @Override
    public void showErrMsg(String msg) {
        UIHelper.ToastMessage(mActivity,msg );
    }

    /**
     * 设置RecyclerView 布局样式 列表 表格
     *
     * @param recyclerView
     */
    public abstract void setLayoutManager(YRecyclerView recyclerView);

    @Override
    public void setTitleMsg(String titleMsg) {

    }

    @Override
    public void setHidTvRight(boolean flag) {

    }

    @Override
    public void finshView() {
        getActivity().finish();
    }
}
