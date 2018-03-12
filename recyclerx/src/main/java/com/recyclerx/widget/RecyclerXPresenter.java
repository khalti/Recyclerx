package com.recyclerx.widget;

import android.support.annotation.NonNull;

import com.recyclerx.utils.EmptyUtil;
import com.recyclerx.utils.GuavaUtil;
import com.recyclerx.widget.listeners.OnLoadMoreListener;
import com.recyclerx.widget.listeners.OnTryAgainListener;

import rx.subscriptions.CompositeSubscription;

public class RecyclerXPresenter implements RecyclerXContract.Presenter {

    @NonNull
    private final RecyclerXContract.View view;
    private CompositeSubscription compositeSubscription;

    private String loadingText = "Loading... Please wait", errorText = "Something went wrong";
    private int loadingImage, errorImage;

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
        view.toggleProgressBar(show);
        view.setIndentedMessage(show ? loadingText : errorText);
        view.setIndentedImage(show ? loadingImage : errorImage);
        view.toggleTryAgainButton(false);
    }

    @Override
    public void onErrorToggled(boolean show) {
        view.toggleIndented(show);
        view.setIndentedMessage(show ? errorText : loadingText);
        view.setIndentedImage(show ? errorImage : loadingImage);
        view.toggleProgressBar(!show);
        view.toggleTryAgainButton(true);
    }

    @Override
    public void onSetErrorText(String text) {
        if (EmptyUtil.isNotNull(text) && EmptyUtil.isNotEmpty(text)) {
            errorText = text;
        }
    }

    @Override
    public void onSetLoadingText(String text) {
        if (EmptyUtil.isNotNull(text) && EmptyUtil.isNotEmpty(text)) {
            loadingText = text;
        }
    }

    @Override
    public void onSetLoadingImage(int image) {
        loadingImage = image;
    }

    @Override
    public void onSetErrorImage(int image) {
        errorImage = image;
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
    public void onSetTryAgainListener(OnTryAgainListener onTryAgainListener) {
        compositeSubscription.add(view.setButtonClickListener().subscribe(aVoid -> onTryAgainListener.onTryAgain()));
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
    public void onDestroy() {
        if (EmptyUtil.isNotNull(compositeSubscription) && compositeSubscription.hasSubscriptions() && !compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }
}
