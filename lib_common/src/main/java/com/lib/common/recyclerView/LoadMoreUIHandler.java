package com.lib.common.recyclerView;

public interface LoadMoreUIHandler {
    void onLoading();

    void onLoadFinish(boolean hasMore);
}
