package com.recyclerx.widget;

public interface RecyclerXContract {
    interface View {

        void setupList();

        void toggleLoading(boolean show);

        void toggleProgressBar(boolean show);

        void setIndentedMessage(String text);

        void setIndentedImage(int image);

        void setProgressBarColor(int color);

        void setTryButtonColor(int color);

        Presenter getPresenter();

        void tryAgain();

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

        void onTryAgain();
    }
}
