package com.recyclerx.widget;

import com.recyclerx.widget.listeners.OnLoadMoreListener;
import com.recyclerx.widget.listeners.OnTryAgainListener;

import java.util.HashMap;

import rx.Observable;

public interface RecyclerXContract {
    interface View {

        void setupList();

        void toggleIndented(boolean show);

        void toggleProgressBar(boolean show);

        void toggleTryAgainButton(boolean show);

        void setIndentedMessage(String text);

        void setIndentedImage(int image);

        void setProgressBarColor(int color);

        void setTryButtonColor(int color);

        Observable<Void> setButtonClickListener();

        Observable<HashMap<String, Integer>> addListScrollListener();

        Presenter getPresenter();

        void setPresenter(Presenter presenter);
    }

    interface Presenter {

        void onSetupList();

        void onLoadingToggled(boolean show);

        void onErrorToggled(boolean show);

        void onSetErrorText(String text);

        void onSetLoadingText(String text);

        void onSetLoadingImage(int image);

        void onSetErrorImage(int image);

        void onSetProgressBarColor(int color);

        void onSetTryButtonColor(int color);

        void onSetTryAgainListener(OnTryAgainListener onTryAgainListener);

        void onSetListScrollListener(int pageQuantum, OnLoadMoreListener onLoadMoreListener);

        void onDestroy();
    }
}
