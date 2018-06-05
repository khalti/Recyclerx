package com.recyclerx.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;
import com.recyclerx.R;
import com.recyclerx.utils.EmptyUtil;
import com.recyclerx.widget.listeners.OnLoadMoreListener;
import com.recyclerx.widget.listeners.OnPullToRefreshListener;
import com.recyclerx.widget.listeners.OnTryAgainListener;
import com.stateLayout.widget.StateLayout;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

public class RecyclerX extends FrameLayout implements RecyclerXProtocols {

    private Context context;
    private AttributeSet attributeSet;
    private RecyclerXContract.Presenter presenter;
    private android.support.v7.widget.RecyclerView.Adapter adapter;
    private android.support.v7.widget.RecyclerView.LayoutManager layoutManager;
    private CompositeDisposable compositeDisposable;

    /*Views*/
    private android.support.v7.widget.RecyclerView rvList;
    private SwipeRefreshLayout srlList;
    private StateLayout slLoad;

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
    public void togglePullToRefresh(boolean enable) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onPullToRefreshToggled(enable);
        }
    }

    @Override
    public boolean isListRefreshing() {
        if (EmptyUtil.isNotNull(presenter)) {
            return presenter.onGetRefreshingStatus();
        }
        return false;
    }

    @Override
    public void setListRefreshing(boolean refreshing) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onSetListRefreshing(refreshing);
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
    public void setPullToRefreshColor(int... color) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onSetPullToRefreshColor(color);
        }
    }

    @Override
    public void setTryAgainListener(OnTryAgainListener onTryAgainListener) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onSetTryAgainListener(onTryAgainListener);
        }
    }

    @Override
    public void setOnScrollListener(int pageQuantum, OnLoadMoreListener onLoadMoreListener) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onSetListScrollListener(pageQuantum, onLoadMoreListener);
        }
    }

    @Override
    public void setOnPullToRefreshListener(OnPullToRefreshListener onPullToRefreshListener) {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onSetPullToRefreshListener(onPullToRefreshListener);
        }
    }

    @Override
    public void onDestroy() {
        if (EmptyUtil.isNotNull(presenter)) {
            presenter.onDestroy();
        }
    }

    private void init() {
        compositeDisposable = new CompositeDisposable();
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
            slLoad = mainView.findViewById(R.id.slLoad);
            srlList = mainView.findViewById(R.id.srlList);

            presenter = new RecyclerView().getPresenter();
            presenter.onSetErrorText(errorText);
            presenter.onSetLoadingText(loadingText);
            presenter.onSetProgressBarColor(progressBarColor);
            presenter.onSetTryButtonColor(tryButtonColor);
            presenter.onSetErrorImage(errorImage);
            presenter.onSetLoadingImage(loadingImage);
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
        public void toggleIndented(boolean show) {
            slLoad.toggleLoading(show);
        }

        @Override
        public void toggleError(boolean show) {
            slLoad.toggleError(show);
        }

        @Override
        public void togglePullToRefresh(boolean enable) {
            srlList.setEnabled(enable);
        }

        @Override
        public void setErrorText(String text) {
            slLoad.setErrorText(text);
        }

        @Override
        public void setLoadingText(String text) {
            slLoad.setLoadingText(text);
        }

        @Override
        public void setLoadingImage(int image) {
            slLoad.setLoadingImage(image);
        }

        @Override
        public void setErrorImage(int image) {
            slLoad.setErrorImage(image);
        }

        @Override
        public void setProgressBarColor(int color) {
            slLoad.setProgressBarColor(color);
        }

        @Override
        public void setTryButtonColor(int color) {
            slLoad.setTryButtonColor(color);
        }

        @Override
        public void setPullToRefreshColor(int... color) {
            srlList.setColorSchemeResources(color);
        }

        @Override
        public boolean isListRefreshing() {
            return srlList.isRefreshing();
        }

        @Override
        public void setListRefreshing(boolean refreshing) {
            srlList.setRefreshing(refreshing);
        }

        @Override
        public Observable<HashMap<String, Integer>> addListScrollListener() {
            PublishSubject<HashMap<String, Integer>> scrollListener = PublishSubject.create();

            compositeDisposable.add(RxRecyclerView.scrollEvents(rvList)
                    .subscribe(recyclerViewScrollEvent -> {
                        if (recyclerViewScrollEvent.dy() > 0) {
                            int fp = 0;
                            if (layoutManager instanceof LinearLayoutManager) {
                                fp = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                            }
                            int finalFp = fp;
                            scrollListener.onNext(new HashMap<String, Integer>() {{
                                put("dy", recyclerViewScrollEvent.dy());
                                put("past_visible_items", finalFp);
                                put("visible_item_count", layoutManager.getChildCount());
                                put("total_item_count", layoutManager.getItemCount());
                            }});
                        }
                    }));
            return scrollListener;
        }

        @Override
        public void setTryAgainListener(com.stateLayout.widget.listeners.OnTryAgainListener onTryAgainListener) {
            slLoad.setOnTryAgainListener(onTryAgainListener);
        }

        @Override
        public Observable<Object> setPullToRefreshListener() {
            return RxSwipeRefreshLayout.refreshes(srlList);
        }

        @Override
        public RecyclerXContract.Presenter getPresenter() {
            return presenter;
        }

        @Override
        public void setPresenter(RecyclerXContract.Presenter presenter) {
            this.presenter = presenter;
        }
    }
}
