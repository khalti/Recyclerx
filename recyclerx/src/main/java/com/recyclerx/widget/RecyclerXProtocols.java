package com.recyclerx.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.recyclerx.widget.listeners.OnLoadMoreListener;
import com.recyclerx.widget.listeners.OnPullToRefreshListener;
import com.recyclerx.widget.listeners.OnTryAgainListener;

import io.reactivex.Observable;

public interface RecyclerXProtocols {

    void setupList(RecyclerView.Adapter adapter, RecyclerView.LayoutManager layoutManager);

    void toggleLoading(boolean show);

    void toggleError(boolean show);

    void togglePullToRefresh(boolean enable);

    boolean isListRefreshing();

    void setListRefreshing(boolean refreshing);

    void setLoadingText(String text);

    void setErrorText(String text);

    void setLoadingImage(int image);

    void setErrorImage(int image);

    void setCustomLoadingView(View view);

    void setCustomErrorView(View view);

    void setProgressBarColor(int color);

    void setTryButtonColor(int color);

    void setPullToRefreshColor(int... color);

    void setTryAgainListener(OnTryAgainListener onTryAgainListener);

    void setOnScrollListener(int pageQuantum, OnLoadMoreListener onLoadMoreListener);

    void setOnPullToRefreshListener(OnPullToRefreshListener onPullToRefreshListener);

    Observable<Object> setTryAgainListener();

    Observable<Object> setOnScrollListener(int pageQuantum);

    Observable<Object> setOnPullToRefreshListener();

    void onDestroy();
}