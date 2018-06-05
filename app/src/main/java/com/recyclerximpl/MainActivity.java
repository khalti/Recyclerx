package com.recyclerximpl;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.recyclerx.widget.RecyclerX;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvList)
    RecyclerX rvList;

    private NameAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fetchList(false);
        rvList.setPullToRefreshColor(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        rvList.setOnPullToRefreshListener(() -> new Handler().postDelayed(() -> rvList.setListRefreshing(false), 3000));
        rvList.togglePullToRefresh(false);
        rvList.setTryAgainListener(() -> fetchList(true));
    }

    private void fetchList(boolean success) {
        List<String> names = new ArrayList<>();
        names.add("Jay mata di");
        names.add("Hello kanchhi");
        names.add("Timro nau k ho bhana");
        names.add("Hello ko bolnu vako");
        names.add("Hello ko bolnu vako");
        names.add("bum bum");
        names.add("Hello ko bolnu vako");
        names.add("Hello ko bolnu vako");
        names.add("bhola nath");
        names.add("Hello ko bolnu vako");
        names.add("yas queen yas");
        names.add("Hello ko bolnu vako");
        names.add("1");
        names.add("2");
        names.add("3");
        names.add("4");

        adapter = new NameAdapter(this, names);

        View loadView = getLayoutInflater().inflate(R.layout.custom_view, null);
        ((ImageView) loadView.findViewById(R.id.iv)).setImageResource(R.mipmap.cat);
        rvList.setCustomLoadingView(loadView);

        View errorView = getLayoutInflater().inflate(R.layout.custom_view, null);
        ((ImageView) errorView.findViewById(R.id.iv)).setImageResource(R.drawable.ic_launcher_background);
        rvList.setCustomErrorView(errorView);

        rvList.toggleLoading(true);
        new Handler().postDelayed(() -> {
            rvList.toggleLoading(false);
            if (success) {
                rvList.setupList(adapter, new LinearLayoutManager(this));
                rvList.setOnScrollListener(10, this::fetchMore);
            } else {
                rvList.toggleError(true);
            }
        }, 3000);
    }

    private void fetchMore() {
        List<String> names = new ArrayList<>();
        names.add("Jay mata di");
        names.add("Hello kanchhi");
        names.add("Timro nau k ho bhana");
        names.add("Hello ko bolnu vako");
        names.add("Hello ko bolnu vako");
        names.add("bum bum");
        names.add("Hello ko bolnu vako");
        names.add("Hello ko bolnu vako");
        names.add("bhola nath");
        names.add("Hello ko bolnu vako");
        names.add("yas queen yas");
        names.add("Hello ko bolnu vako");
        names.add("1");
        names.add("2");
        names.add("3");
        names.add("4");
        names.add("5");
        names.add("6");
        names.add("7");
        names.add("8");
        names.add("9");
        names.add("10");
        names.add("11");
        names.add("12");

        new Handler().postDelayed(() -> adapter.addMore(names), 3000);
    }
}
