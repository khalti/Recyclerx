package com.recyclerx.widget;

import com.recyclerx.widget.listeners.OnLoadMoreListener;
import com.recyclerx.widget.listeners.OnPullToRefreshListener;
import com.recyclerx.widget.listeners.OnTryAgainListener;

import java.util.HashMap;

import rx.Observable;

public interface RecyclerXContract {
    interface View {

        void setupList();

        void toggleIndented(boolean show);

        void toggleError(boolean show);

        void togglePullToRefresh(boolean enable);

        void setErrorText(String text);

        void setLoadingText(String text);

        void setLoadingImage(int image);

        void setErrorImage(int image);

        void setProgressBarColor(int color);

        void setTryButtonColor(int color);

        void setPullToRefreshColor(int... color);

        Observable<HashMap<String, Integer>> addListScrollListener();

        void setTryAgainListener(com.stateLayout.widget.listeners.OnTryAgainListener onTryAgainListener);

        Observable<Void> setPullToRefreshListener();

        Presenter getPresenter();

        void setPresenter(Presenter presenter);
    }

    interface Presenter {

        void onSetupList();

        void onLoadingToggled(boolean show);

        void onErrorToggled(boolean show);

        void onPullToRefreshToggled(boolean enable);

        void onSetErrorText(String text);

        void onSetLoadingText(String text);

        void onSetLoadingImage(int image);

        void onSetErrorImage(int image);

        void onSetProgressBarColor(int color);

        void onSetTryButtonColor(int color);

        void onSetPullToRefreshColor(int... color);

        void onSetTryAgainListener(OnTryAgainListener onTryAgainListener);

        void onSetListScrollListener(int pageQuantum, OnLoadMoreListener onLoadMoreListener);

        void onSetPullToRefreshListener(OnPullToRefreshListener onPullToRefreshListener);

        void onDestroy();
    }
}
