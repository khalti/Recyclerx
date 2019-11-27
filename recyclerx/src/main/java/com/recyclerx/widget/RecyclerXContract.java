package com.recyclerx.widget;

import com.recyclerx.widget.listeners.OnLoadMoreListener;
import com.recyclerx.widget.listeners.OnPullToRefreshListener;
import com.recyclerx.widget.listeners.OnTryAgainListener;

import java.util.HashMap;

import io.reactivex.Observable;

public interface RecyclerXContract {
    interface View {

        void setupList();

        void toggleIndented(boolean show);

        void toggleError(boolean show);

        void togglePullToRefresh(boolean enable);

        void toggleTryAgain(boolean show);

        void setErrorText(String text);

        void setLoadingText(String text);

        void setLoadingImage(int image);

        void setErrorImage(int image);

        void setCustomLoadingView();

        void setCustomErrorView();

        void setCustomTryAgainView();

        void setProgressBarColor(int color);

        void setTryButtonColor(int color);

        void setTryButtonText(String text);

        void setPullToRefreshColor(int... color);

        boolean isListRefreshing();

        void setListRefreshing(boolean refreshing);

        Observable<HashMap<String, Integer>> addListScrollListener();

        void setTryAgainListener(com.stateLayout.widget.listeners.OnTryAgainListener onTryAgainListener);

        Observable<Object> setTryAgainListener();

        Observable<Object> setPullToRefreshListener();

        Presenter getPresenter();

        void setPresenter(Presenter presenter);
    }

    interface Presenter {

        void onSetupList();

        void onLoadingToggled(boolean show);

        void onErrorToggled(boolean show);

        void onPullToRefreshToggled(boolean enable);

        void onTryAgainToggled(boolean show);

        void onSetErrorText(String text);

        void onSetLoadingText(String text);

        void onSetLoadingImage(int image);

        void onSetErrorImage(int image);

        void onSetCustomLoadingView();

        void onSetCustomErrorView();

        void onSetCustomTryAgainView();

        void onSetProgressBarColor(int color);

        void onSetTryButtonColor(int color);

        void onSetPullToRefreshColor(int... color);

        void onSetTryButtonText(String text);

        boolean onGetRefreshingStatus();

        void onSetListRefreshing(boolean refreshing);

        void onSetTryAgainListener(OnTryAgainListener onTryAgainListener);

        void onSetListScrollListener(int pageQuantum, OnLoadMoreListener onLoadMoreListener);

        void onSetPullToRefreshListener(OnPullToRefreshListener onPullToRefreshListener);

        Observable<Object> onSetTryAgainListener();

        Observable<Object> onSetListScrollListener(int pageQuantum);

        Observable<Object> onSetPullToRefreshListener();

        void onDestroy();
    }
}
