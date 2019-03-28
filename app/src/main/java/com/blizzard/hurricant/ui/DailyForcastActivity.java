package com.blizzard.hurricant.ui;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blizzard.hurricant.R;
import com.blizzard.hurricant.adapters.Dayadapter;
import com.blizzard.hurricant.weather.Daily;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DailyForcastActivity extends Activity {
    private Daily[] daily;
    @InjectView(android.R.id.list) ListView mListView;
    @InjectView(android.R.id.empty) TextView mEmptyTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forcast);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        daily = Arrays.copyOf(parcelables,parcelables.length,Daily[].class);
        Dayadapter adapter = new Dayadapter(this, daily);
        mListView.setAdapter(adapter);
        mListView.setEmptyView(mEmptyTextView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dayOfTheWeek = daily[position].getDayOfTheWeek();
                String conditions = daily[position].getSummary();
                String highTemp = daily[position].getTemperatureMax() + "";
                String message = String.format("On %s the high will be %s and it'll be %s",
                        dayOfTheWeek,
                        highTemp,
                        conditions);
                Toast.makeText(DailyForcastActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

    }

}
