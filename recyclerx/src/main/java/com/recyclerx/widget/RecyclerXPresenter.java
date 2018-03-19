package com.recyclerx.widget;

import android.support.annotation.NonNull;

import com.recyclerx.utils.EmptyUtil;
import com.recyclerx.utils.GuavaUtil;
import com.recyclerx.widget.listeners.OnLoadMoreListener;
import com.recyclerx.widget.listeners.OnPullToRefreshListener;
import com.recyclerx.widget.listeners.OnTryAgainListener;

import rx.subscriptions.CompositeSubscription;

public class RecyclerXPresenter implements RecyclerXContract.Presenter {

    @NonNull
    private final RecyclerXContract.View view;
    private CompositeSubscription compositeSubscription;

    RecyclerXPresenter(@NonNull RecyclerXContract.View view) {
        this.view = GuavaUtil.checkNotNull(view);
        view.setPresenter(this);
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onSetupList() {
        view.setupList();
    }

    @Override
    public void onLoadingToggled(boolean show) {
        view.toggleIndented(show);
    }

    @Override
    public void onErrorToggled(boolean show) {
        view.toggleError(show);
    }

    @Override
    public void onPullToRefreshToggled(boolean enable) {
        view.togglePullToRefresh(enable);
    }

    @Override
    public void onSetErrorText(String text) {
        view.setErrorText(text);
    }

    @Override
    public void onSetLoadingText(String text) {
        view.setLoadingText(text);
    }

    @Override
    public void onSetLoadingImage(int image) {
        view.setLoadingImage(image);
    }

    @Override
    public void onSetErrorImage(int image) {
        view.setErrorImage(image);
    }

    @Override
    public void onSetProgressBarColor(int color) {
        view.setProgressBarColor(color);
    }

    @Override
    public void onSetTryButtonColor(int color) {
        view.setTryButtonColor(color);
    }

    @Override
    public void onSetPullToRefreshColor(int... color) {
        view.setPullToRefreshColor(color);
    }

    @Override
    public void onSetTryAgainListener(OnTryAgainListener onTryAgainListener) {
        view.setTryAgainListener(onTryAgainListener::onTryAgain);
    }

    @Override
    public void onSetListScrollListener(int pageQuantum, OnLoadMoreListener onLoadMoreListener) {
        compositeSubscription.add(view.addListScrollListener().subscribe(map -> {
            int pastVisibleItems = map.get("past_visible_items");
            int visibleItemCount = map.get("visible_item_count");
            int totalItemCount = map.get("total_item_count");

            if (totalItemCount > pageQuantum && (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                onLoadMoreListener.onLoadMore();
            }
        }));
    }

    @Override
    public void onSetPullToRefreshListener(OnPullToRefreshListener onPullToRefreshListener) {
        compositeSubscription.add(view.setPullToRefreshListener().subscribe(aVoid -> onPullToRefreshListener.onRefresh()));
    }

    @Override
    public void onDestroy() {
        if (EmptyUtil.isNotNull(compositeSubscription) && compositeSubscription.hasSubscriptions() && !compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }
}
