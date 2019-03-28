package com.blizzard.hurricant.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blizzard.hurricant.R;
import com.blizzard.hurricant.databinding.ActivityMainBinding;
import com.blizzard.hurricant.weather.Current;
import com.blizzard.hurricant.weather.Daily;
import com.blizzard.hurricant.weather.Forecast;
import com.blizzard.hurricant.weather.Hourly;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Forecast mForecast;
    private ImageView iconImageView;
    final double latitude= 36.7538259;
    final double longitude = 3.0534636;

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILY_FORECAST = "DAILY_FORECAST";
    public static final String HOURLY_FORECAST = "HOURLY_FORECAST";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getForecast(latitude,longitude);
        Log.d(TAG, "Main UI code is running");

    }

    private void getForecast(double latitude,double longitude) {
        final ActivityMainBinding binding = DataBindingUtil.setContentView(MainActivity.this,
                R.layout.activity_main);

        TextView darkSky = findViewById(R.id.darkSkyAttribution);
        darkSky.setMovementMethod(LinkMovementMethod.getInstance());

        iconImageView = findViewById(R.id.hIconImageView);

        String apiKey = "0804ad7fd097c53331ae5957326ee343";

        String forcastURL = "https://api.darksky.net/forecast/"
                + apiKey + "/" + latitude + "," + longitude +"?units=auto";
//**********************************************************************************************************
        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forcastURL)
                    .build();

            Call call = client.newCall(request);
            //****************************************************************************
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            mForecast = parseForcastDetails(jsonData);
                                Current current = mForecast.getmCurrent();
                            final Current displayWeather = new Current(
                                    current.getLocationLabel(),
                                    current.getIcon(),
                                    current.getTime(),
                                    current.getTemperature(),
                                    current.getHumidity(),
                                    current.getPrecipChance(),
                                    current.getSummary(),
                                    current.getTimeZone()
                            );
                            binding.setWeather(displayWeather);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Drawable drawable = getResources().getDrawable(displayWeather.getIconId());
                                    iconImageView.setImageDrawable(drawable);
                                }
                            });


                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "IOException caught: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSONException caught: ", e);
                    }

                }
            });
            //****************************************************************************
        }
    }

    private Forecast parseForcastDetails(String jsonData) throws JSONException{
        Forecast forecast =  new Forecast();
        forecast.setmCurrent(getCurrentDetails(jsonData));
        forecast.setmHourlyForcast(getHourlyForcast(jsonData));
        forecast.setmDailyForcast(getDailyForcast(jsonData));
        return forecast;
    }

 private Daily[] getDailyForcast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");
        Daily [] days = new Daily[data.length()];

        for (int i=0;i<data.length();i++){
            JSONObject jsonDay = data.getJSONObject(i);
            Daily day = new Daily();

            day.setSummary(jsonDay.getString("summary"));
            day.setIcon(jsonDay.getString("icon"));
            day.setTemperatureMax(jsonDay.getDouble("temperatureMax"));
            day.setTime(jsonDay.getLong("time"));
            day.setTimeZone(timezone);

            days [i] = day;
        }
        return days;
    }


    private Hourly[] getHourlyForcast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        Hourly[] hours = new Hourly[data.length()];

        for (int i = 0; i<data.length(); i++){
            JSONObject jsonHour = data.getJSONObject(i);
            Hourly hour = new Hourly();

            hour.setSummary(jsonHour.getString("summary"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimeZone(timezone);

            hours[i] = hour;
        }
        return hours;
    }

    private Current getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG,"From JSON: "+ timezone);

        JSONObject currently = forecast.getJSONObject("currently");

        Current current = new Current();

        current.setHumidity(currently.getDouble("humidity"));
        current.setTime(currently.getLong("time"));
        current.setIcon(currently.getString("icon"));
        current.setLocationLabel("Algiers, Algeria");
        current.setPrecipChance(currently.getDouble("precipProbability"));
        current.setSummary(currently.getString("summary"));
        current.setTemperature(currently.getDouble("temperature"));
        current.setTimeZone(timezone);


        return current;

    }

    /**********************************************************************************************************/
    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;

        if (networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        } else {

            Toast.makeText(this, R.string.network_unavailable_message,
                    Toast.LENGTH_LONG).show();
        }
    return  isAvailable;
    }

    /**********************************************************************************************************/
    private void alertUserAboutError() {
    AlertDialogFragment dialog = new AlertDialogFragment();
    dialog.show(getSupportFragmentManager(),"error_dialog");
    }
    /**********************************************************************************************************/
    public void refreshOnClick(View view){
        Toast.makeText(this,"Refreshing data", Toast.LENGTH_LONG).show();
        getForecast(latitude,longitude);
    }

    public void startDailyActivity(View view){
        Intent intent = new Intent(this,DailyForcastActivity.class);
        intent.putExtra(DAILY_FORECAST, mForecast.getmDailyForcast());
        startActivity(intent);
    }
        //
    public void startHourlyActivity(View view){
        Intent intent = new Intent(this,HourlyForcastActivity.class);
        intent.putExtra(HOURLY_FORECAST, mForecast.getmHourlyForcast());
        startActivity(intent);
    }
}
