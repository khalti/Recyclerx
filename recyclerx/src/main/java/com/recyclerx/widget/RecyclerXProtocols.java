package com.recyclerx.widget;

public interface RecyclerXProtocols {

    void toggleLoading(boolean show);

    void toggleError(boolean show);

    void toggleTryButton(boolean show);

    void setLoadingText(String text);

    void setErrorText(String text);

    void setLoadingImage(int image);

    void setErrorImage(int image);

    void progressBarColor(int color);

    void setTryButtonColor(int color);
}
