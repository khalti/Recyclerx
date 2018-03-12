package com.recyclerx.widget;

import android.support.annotation.NonNull;

import com.recyclerx.utils.EmptyUtil;
import com.recyclerx.utils.GuavaUtil;

public class RecyclerXPresenter implements RecyclerXContract.Presenter {

    @NonNull
    private final RecyclerXContract.View view;

    private String loadingText = "Loading... Please wait", errorText = "Something went wrong";
    private int loadingImage, errorImage;

    RecyclerXPresenter(@NonNull RecyclerXContract.View view) {
        this.view = GuavaUtil.checkNotNull(view);
        view.setPresenter(this);
    }

    @Override
    public void onSetupList() {
        view.setupList();
    }

    @Override
    public void onLoadingToggled(boolean show) {
        view.toggleLoading(show);
        view.toggleProgressBar(show);
        view.setIndentedMessage(show ? loadingText : errorText);
        view.setIndentedImage(show ? loadingImage : errorImage);
    }

    @Override
    public void onErrorToggled(boolean show) {
        view.setIndentedMessage(show ? errorText : loadingText);
        view.setIndentedImage(show ? errorImage : loadingImage);
        view.toggleProgressBar(!show);
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
    public void onTryAgain() {
        view.tryAgain();
    }
}
