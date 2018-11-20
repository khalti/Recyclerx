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

    void toggleTryAgain(boolean show);

    boolean isListRefreshing();

    void setListRefreshing(boolean refreshing);

    void setLoadingText(String text);

    void setErrorText(String text);

    void setLoadingImage(int image);

    void setErrorImage(int image);

    void setCustomLoadingView(View view);

    void setCustomErrorView(View view);

    void setCustomTryAgainButton(View view);

    void setProgressBarColor(int color);

    void setTryButtonColor(int color);

    void setPullToRefreshColor(int... color);

    void setOnTryAgainListener(OnTryAgainListener onTryAgainListener);

    void setOnScrollListener(int pageQuantum, OnLoadMoreListener onLoadMoreListener);

    void setOnPullToRefreshListener(OnPullToRefreshListener onPullToRefreshListener);

    Observable<Object> setOnTryAgainListener();

    Observable<Object> setOnScrollListener(int pageQuantum);

    Observable<Object> setOnPullToRefreshListener();

    void onDestroy();
}