package com.blizzard.hurricant.ui;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.InjectView;
import butterknife.ButterKnife;
import com.blizzard.hurricant.R;
import com.blizzard.hurricant.adapters.Houradapter;
import com.blizzard.hurricant.weather.Hourly;

import java.util.Arrays;

public class HourlyForcastActivity extends AppCompatActivity {
    private Hourly[] mHours;
    @InjectView(R.id.recyclerview) RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forcast);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        Parcelable [] parcelables = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);
        mHours = Arrays.copyOf(parcelables,parcelables.length,Hourly[].class);
        Houradapter adapter = new Houradapter(this, mHours);
        mRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
    }
}
