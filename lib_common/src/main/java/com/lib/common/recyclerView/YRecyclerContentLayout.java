package com.lib.common.recyclerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lib.common.R;



/**
 * Created by wanglei on 2016/10/30.
 */

public class YRecyclerContentLayout extends QTContentLayout implements YRecyclerView.StateCallback, SwipeRefreshLayout.OnRefreshListener {

    private int padding;
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;
    private int scrollbarStyle;
    private int backgroundColor;
    private boolean clipToPadding;
    private boolean scrollbarNone = false;

    SwipeRefreshLayout swipeRefreshLayout;
    YRecyclerView recyclerView;


    public YRecyclerContentLayout(Context context) {
        this(context, null);
    }

    public YRecyclerContentLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YRecyclerContentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setupAttrs(context, attrs);
        inflateView(context);
    }

    private void setupAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.YRecyclerContentLayout);

        backgroundColor = typedArray.getColor(R.styleable.YRecyclerContentLayout_recyclerBackgroundColor, Color.WHITE);
        padding = (int) typedArray.getDimension(R.styleable.YRecyclerContentLayout_recyclerPadding, -1.0f);
        paddingLeft = (int) typedArray.getDimension(R.styleable.YRecyclerContentLayout_recyclerPaddingLeft, 0.0f);
        paddingRight = (int) typedArray.getDimension(R.styleable.YRecyclerContentLayout_recyclerPaddingRight, 0.0f);
        paddingTop = (int) typedArray.getDimension(R.styleable.YRecyclerContentLayout_recyclerPaddingTop, 0.0f);
        paddingBottom = (int) typedArray.getDimension(R.styleable.YRecyclerContentLayout_recyclerPaddingBottom, 0.0f);
        scrollbarStyle = typedArray.getInt(R.styleable.YRecyclerContentLayout_recyclerScrollbarStyle, 2);
        clipToPadding = typedArray.getBoolean(R.styleable.YRecyclerContentLayout_recyclerClipToPadding, false);
        scrollbarNone = typedArray.getBoolean(R.styleable.YRecyclerContentLayout_recyclerScrollbarNone, false);

        typedArray.recycle();
    }

    private void inflateView(Context context) {
        inflate(context, R.layout.x_view_recycler_content_layout, this);
    }

    @Override
    protected void onFinishInflate() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (YRecyclerView) findViewById(R.id.recyclerView);

        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.x_red,
                R.color.x_blue,
                R.color.x_yellow,
                R.color.x_green
        );
        swipeRefreshLayout.setOnRefreshListener(this);
        if (padding != -1) {
            recyclerView.setPadding(padding, padding, padding, padding);
        } else {
            recyclerView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        }
        recyclerView.setClipToPadding(clipToPadding);

        if (scrollbarNone) {
            recyclerView.setVerticalScrollBarEnabled(false);
            recyclerView.setHorizontalScrollBarEnabled(false);
        } else {
            recyclerView.setScrollBarStyle(scrollbarStyle);
        }

        recyclerView.setBackgroundColor(backgroundColor);

        recyclerView.stateCallback(this);

        super.onFinishInflate();
    }

    @Override
    public void setDisplayState(int displayState) {
        YRecyclerAdapter adapter = recyclerView.getAdapter();
        if (adapter != null && adapter.getItemCount() > 0) {
            super.setDisplayState(STATE_CONTENT);
            return;
        }
        super.setDisplayState(displayState);
    }

    public void setDisplayState(int state, boolean isForce) {
        if (isForce) {
            super.setDisplayState(state);
            return;
        }
        setDisplayState(state);
    }

    @Override
    public void showEmpty() {
        setDisplayState(STATE_EMPTY, true);
    }

    @Override
    public void showError() {
        setDisplayState(STATE_ERROR, true);
    }

    @Override
    public void showLoading() {
        setDisplayState(STATE_LOADING, true);
    }

    @Override
    public void notifyEmpty() {
        showEmpty();
    }

    @Override
    public void notifyContent() {
        showContent();
    }

    @Override
    public void refreshState(boolean isRefresh) {
        swipeRefreshLayout.setRefreshing(isRefresh);
    }

    @Override
    public void refreshEnabled(boolean isEnabled) {
        swipeRefreshLayout.setEnabled(isEnabled);
    }

    @Override
    public void onRefresh() {
        recyclerView.onRefresh();
    }

    public YRecyclerView getRecyclerView() {
        return recyclerView;
    }

    public YRecyclerContentLayout setColorSchemeResources(int... colorResIds){
        swipeRefreshLayout.setColorSchemeResources();
        return this;
    }
}
