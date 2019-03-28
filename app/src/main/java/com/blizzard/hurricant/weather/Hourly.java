package com.blizzard.hurricant.weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Hourly implements Parcelable {
    private long mTime;
    private String mSummary;
    private double mTemperature;
    private String mIcon;
    private String mTimeZone;

        public Hourly() {}

        private Hourly(Parcel in) {
            mTime = in.readLong();
            mTemperature = in.readDouble();
            mSummary = in.readString();
            mIcon = in.readString();
            mTimeZone = in.readString();
        }

        public static final Creator<Hourly> CREATOR = new Creator<Hourly>() {
            @Override
            public Hourly createFromParcel(Parcel in) {
                return new Hourly(in);
            }

            @Override
            public Hourly[] newArray(int size) {
                return new Hourly[size];
            }
        };

    public long getTime() {
        return mTime;
    }

    public void setTime(long mTime) {
        this.mTime = mTime;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String mSummary) {
        this.mSummary = mSummary;
    }

    public int getTemperature() { return (int) Math.round(mTemperature); }

    public void setTemperature(double mTemperature) {
        this.mTemperature = mTemperature;
    }

    public String getIcon() {
        return mIcon;
    }

    public  int getIconId(){
        return Forecast.getIconId(mIcon);
    }

    public void setIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String mTimeZone) {
        this.mTimeZone = mTimeZone;
    }

    public String getHour(){
        SimpleDateFormat formatter = new SimpleDateFormat("h a");
        Date date = new Date(mTime * 1000);
        return formatter.format(date);
    }

    @Override
    public int describeContents() {
        return 0; //ignore
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeLong(mTime);
        dest.writeDouble(mTemperature);
        dest.writeString(mSummary);
        dest.writeString(mIcon);
        dest.writeString(mTimeZone);
    }
}
