package com.example.recyclerviewintablayout;

import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;
import android.util.Log;

public class Excercise implements Parcelable {
    private String Time;
    private String endTime = "";
    private String Calories;
    private String Duration;
    private String Type;
    private String WarmUp;

    public Excercise(String time, String calories, String duration, String type, String warmUp) {
        Time = time;
        endTime = time;
        Calories = calories;
        Duration = duration;
        Type = type;
        WarmUp = warmUp;
    }

    public static final Creator<Excercise> CREATOR = new Creator<Excercise>() {
        @Override
        public Excercise createFromParcel(Parcel in) {
            return new Excercise(in);
        }

        @Override
        public Excercise[] newArray(int size) {
            return new Excercise[size];
        }
    };

    protected Excercise(Parcel in) {
        try {
            Time = in.readString();
            endTime = in.readString();
            Calories = in.readString();
            Duration = in.readString();
            Type = in.readString();
            WarmUp = in.readString();
        } catch (ParcelFormatException e) {
            e.printStackTrace();
        }
    }

    public String getTime() {return Time;}
    public String getEndTime() {return endTime; }
    public String getCalories() {return Calories;}
    public String getDuration() {return Duration;}
    public String getType() {return Type;}
    public String getWarmUp() {return WarmUp;}

    public void setTime(String time) {
        Time = time;
    }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public void setCalories(String calories) {
        Calories = calories;
    }
    public void setDuration(String duration) {
        Duration = duration;
    }
    public void setType(String type) {Type = type;}
    public void setWarmUp(String warmUp) {WarmUp = warmUp;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Must be in the same order as constructor
        dest.writeString(Time);
        dest.writeString(endTime);
        dest.writeString(Calories);
        dest.writeString(Duration);
        dest.writeString(Type);
        dest.writeString(WarmUp);
    }
}
