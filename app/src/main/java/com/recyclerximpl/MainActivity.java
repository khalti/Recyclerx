package com.recyclerximpl;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.recyclerx.widget.RecyclerX;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvList)
    RecyclerX rvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        List<String> names = new ArrayList<>();
        names.add("Jay mata di");
        names.add("Hello kanchhi");
        names.add("Timro nau k ho bhana");
        names.add("Hello ko bolnu vako");

        rvList.toggleLoading(true);
        new Handler().postDelayed(() -> {
            rvList.toggleLoading(false);
            rvList.setupList(new NameAdapter(this, names), new LinearLayoutManager(this));
        }, 7000);
    }
}
