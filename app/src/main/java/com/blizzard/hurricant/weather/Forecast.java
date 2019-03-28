package com.blizzard.hurricant.weather;

import com.blizzard.hurricant.R;

public class Forecast {
    private Current mCurrent;
    private Hourly[] mHourlyForcast;
    private Daily[] mDailyForcast;

    public Current getmCurrent() {
        return mCurrent;
    }

    public void setmCurrent(Current mCurrent) {
        this.mCurrent = mCurrent;
    }

    public Hourly[] getmHourlyForcast() {
        return mHourlyForcast;
    }

    public void setmHourlyForcast(Hourly[] mHourlyForcast) {
        this.mHourlyForcast = mHourlyForcast;
    }

    public Daily[] getmDailyForcast() {
        return mDailyForcast;
    }

    public void setmDailyForcast(Daily[] mDailyForcast) {
        this.mDailyForcast = mDailyForcast;
    }

    /**********************************************************************************************/
    public static int getIconId(String iconString){

            int iconId = R.drawable.clear_day;
            switch (iconString){
                case "clear-day":
                    iconId = R.drawable.clear_day;
                    break;
                case "clear-night":
                    iconId = R.drawable.clear_night;
                    break;
                case "rain":
                    iconId = R.drawable.rain;
                    break;
                case "snow":
                    iconId = R.drawable.snow;
                    break;
                case "sleet":
                    iconId = R.drawable.sleet;
                    break;
                case "wind":
                    iconId = R.drawable.wind;
                    break;
                case "fog":
                    iconId = R.drawable.fog;
                    break;
                case "cloudy":
                    iconId = R.drawable.cloudy;
                    break;
                case "partly-cloudy-day":
                    iconId = R.drawable.partly_cloudy;
                    break;
                case "partly-cloudy-night":
                    iconId = R.drawable.cloudy_night;
            }
            return iconId;

    }
    /**********************************************************************************************/
}
