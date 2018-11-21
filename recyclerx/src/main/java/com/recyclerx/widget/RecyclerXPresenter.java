package com.recyclerx.widget;

import android.support.annotation.NonNull;

import com.recyclerx.utils.EmptyUtil;
import com.recyclerx.utils.GuavaUtil;
import com.recyclerx.utils.LogUtil;
import com.recyclerx.widget.listeners.OnLoadMoreListener;
import com.recyclerx.widget.listeners.OnPullToRefreshListener;
import com.recyclerx.widget.listeners.OnTryAgainListener;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

public class RecyclerXPresenter implements RecyclerXContract.Presenter {

    @NonNull
    private final RecyclerXContract.View view;
    private CompositeDisposable compositeDisposable;

    RecyclerXPresenter(@NonNull RecyclerXContract.View view) {
        this.view = GuavaUtil.checkNotNull(view);
        view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
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
    public void onTryAgainToggled(boolean show) {
        view.toggleTryAgain(show);
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
    public void onSetCustomLoadingView() {
        view.setCustomLoadingView();
    }

    @Override
    public void onSetCustomErrorView() {
        view.setCustomErrorView();
    }

    @Override
    public void onSetCustomTryAgainView() {
        view.setCustomTryAgainView();
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
    public boolean onGetRefreshingStatus() {
        return view.isListRefreshing();
    }

    @Override
    public void onSetListRefreshing(boolean refreshing) {
        view.setListRefreshing(refreshing);
    }

    @Override
    public void onSetTryAgainListener(OnTryAgainListener onTryAgainListener) {
        view.setTryAgainListener(onTryAgainListener::onTryAgain);
    }

    @Override
    public void onSetListScrollListener(int pageQuantum, OnLoadMoreListener onLoadMoreListener) {
        compositeDisposable.add(view.addListScrollListener().subscribe(map -> {
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
        compositeDisposable.add(view.setPullToRefreshListener().subscribe(aVoid -> onPullToRefreshListener.onRefresh()));
    }

    @Override
    public Observable<Object> onSetTryAgainListener() {
        return view.setTryAgainListener();
    }

    @Override
    public Observable<Object> onSetListScrollListener(int pageQuantum) {
        PublishSubject<Object> listScrollPublish = PublishSubject.create();
        compositeDisposable.add(view.addListScrollListener().subscribe(map -> {
            int pastVisibleItems = map.get("past_visible_items");
            int visibleItemCount = map.get("visible_item_count");
            int totalItemCount = map.get("total_item_count");

            if (totalItemCount > pageQuantum && (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                LogUtil.checkpoint("load more");
                listScrollPublish.onNext("");
            }
        }));

        return listScrollPublish;
    }

    @Override
    public Observable<Object> onSetPullToRefreshListener() {
        return view.setPullToRefreshListener();
    }

    @Override
    public void onDestroy() {
        if (EmptyUtil.isNotNull(compositeDisposable) && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
