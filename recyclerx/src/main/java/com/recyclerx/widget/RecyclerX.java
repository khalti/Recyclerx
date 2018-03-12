package com.recyclerx.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.recyclerx.R;
import com.recyclerx.carbonX.widget.Button;
import com.recyclerx.carbonX.widget.ProgressBar;
import com.recyclerx.utils.EmptyUtil;

public class RecyclerX extends FrameLayout implements RecyclerXProtocols {

    private Context context;
    private AttributeSet attributeSet;
    private RecyclerXContract.Presenter presenter;
    private android.support.v7.widget.RecyclerView.Adapter adapter;
    private android.support.v7.widget.RecyclerView.LayoutManager layoutManager;
    private OnTryAgainListener onTryAgainListener;

    /*Views*/
    android.support.v7.widget.RecyclerView rvList;
    NestedScrollView nsvIndented;
    ProgressBar pdLoad;
    AppCompatTextView tvMessage;
    Button btnTryAgain;
    ImageView ivIndented;

    public RecyclerX(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    public RecyclerX(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attributeSet = attrs;
        init();
    }

    @Override
    public void setupList(android.support.v7.widget.RecyclerView.Adapter adapter, android.support.v7.widget.RecyclerView.LayoutManager layoutManager) {
        this.adapter = adapter;
        this.layoutManager = layoutManager;
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onSetupList();
        }
    }

    @Override
    public void toggleLoading(boolean show) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onLoadingToggled(show);
        }
    }

    @Override
    public void toggleError(boolean show) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onErrorToggled(show);
        }
    }

    @Override
    public void setLoadingText(String text) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onSetLoadingText(text);
        }
    }

    @Override
    public void setErrorText(String text) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onSetErrorText(text);
        }
    }

    @Override
    public void setLoadingImage(int image) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onSetLoadingImage(image);
        }
    }

    @Override
    public void setErrorImage(int image) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onSetErrorImage(image);
        }
    }

    @Override
    public void setProgressBarColor(int color) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onSetProgressBarColor(color);
        }
    }

    @Override
    public void setTryButtonColor(int color) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onSetTryButtonColor(color);
        }
    }

    @Override
    public void setOnTryAgainListener(OnTryAgainListener onTryAgainListener) {
        this.onTryAgainListener = onTryAgainListener;
    }

    private void init() {

        @SuppressLint("CustomViewStyleable")
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.app, 0, 0);
        String errorText = typedArray.getString(R.styleable.app_errorText);
        String loadingText = typedArray.getString(R.styleable.app_loadingText);
        int progressBarColor = typedArray.getInt(R.styleable.app_progressBarColor, R.color.accent);
        int tryButtonColor = typedArray.getInt(R.styleable.app_tryButtonColor, R.color.primary);
        int loadingImage = typedArray.getResourceId(R.styleable.app_loadingImage, R.drawable.ic_photo);
        int errorImage = typedArray.getResourceId(R.styleable.app_errorImage, R.drawable.ic_photo);

        typedArray.recycle();

        View mainView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (EmptyUtil.isNotNull(inflater)) {
            mainView = inflater.inflate(R.layout.recyclerview, this, true);

            rvList = mainView.findViewById(R.id.rxList);
            nsvIndented = mainView.findViewById(R.id.nsvIndented);
            pdLoad = mainView.findViewById(R.id.pdLoad);
            tvMessage = mainView.findViewById(R.id.tvMessage);
//            flTryAgain = mainView.findViewById(R.id.flTryAgain);
            ivIndented = mainView.findViewById(R.id.ivIndented);
            btnTryAgain = mainView.findViewById(R.id.btnTryAgain);

            presenter = new RecyclerView().getPresenter();
            presenter.onSetErrorText(errorText);
            presenter.onSetLoadingText(loadingText);
            presenter.onSetProgressBarColor(progressBarColor);
            presenter.onSetTryButtonColor(tryButtonColor);
            presenter.onSetErrorImage(errorImage);
            presenter.onSetLoadingImage(loadingImage);

//            btnTryAgain.setOnClickListener(view -> presenter.onTryAgain());
        }
    }

    private class RecyclerView implements RecyclerXContract.View {

        private RecyclerXContract.Presenter presenter;

        RecyclerView() {
            presenter = new RecyclerXPresenter(this);
        }

        @Override
        public void setupList() {
            rvList.setAdapter(adapter);
            rvList.setHasFixedSize(false);
            rvList.setLayoutManager(layoutManager);
        }

        @Override
        public void toggleLoading(boolean show) {
            nsvIndented.setVisibility(show ? VISIBLE : GONE);
            rvList.setVisibility(show ? GONE : VISIBLE);
        }

        @Override
        public void toggleProgressBar(boolean show) {
            pdLoad.setVisibility(show ? VISIBLE : GONE);
        }

        @Override
        public void toggleTryAgainButton(boolean show) {
            btnTryAgain.setVisibility(show ? VISIBLE : INVISIBLE);
        }

        @Override
        public void setIndentedMessage(String text) {
            tvMessage.setText(text);
        }

        @Override
        public void setIndentedImage(int image) {
            ivIndented.setImageResource(image);
        }

        @Override
        public void setProgressBarColor(int color) {
            pdLoad.setTint(color);
        }

        @Override
        public void setTryButtonColor(int color) {
            btnTryAgain.setBackgroundTint(getResources().getColorStateList(color));
        }

        @Override
        public RecyclerXContract.Presenter getPresenter() {
            return presenter;
        }

        @Override
        public void tryAgain() {
            if (EmptyUtil.isNotNull(onTryAgainListener)) {
                onTryAgainListener.onTryAgain();
            }
        }

        @Override
        public void setPresenter(RecyclerXContract.Presenter presenter) {
            this.presenter = presenter;
        }
    }
}
