package com.recyclerx.widget;

import android.support.v7.widget.RecyclerView;

import com.recyclerx.widget.listeners.OnLoadMoreListener;
import com.recyclerx.widget.listeners.OnTryAgainListener;

public interface RecyclerXProtocols {

    void setupList(RecyclerView.Adapter adapter, RecyclerView.LayoutManager layoutManager);

    void toggleLoading(boolean show);

    void toggleError(boolean show);

    void setLoadingText(String text);

    void setErrorText(String text);

    void setLoadingImage(int image);

    void setErrorImage(int image);

    void setProgressBarColor(int color);

    void setTryButtonColor(int color);

    void setOnTryAgainListener(OnTryAgainListener onTryAgainListener);

    void setOnScrollListener(int pageQuantum, OnLoadMoreListener onLoadMoreListener);

    void onDestroy();
}
