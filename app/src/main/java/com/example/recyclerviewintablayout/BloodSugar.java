package com.example.recyclerviewintablayout;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

public class BloodSugar implements Parcelable {

    private String Level;
    private String Time;
    private String Notes;
    private boolean Safe;

    public BloodSugar() {
    }

    public BloodSugar(String level, String time, String notes, boolean isLessThanTwoHours) {
        Level = level;
        Time = time;
        Notes = notes;
        if (isLessThanTwoHours && Integer.parseInt(level) < 180 && Integer.parseInt(level) > 70) {
            Safe = true;
        } else Safe = !isLessThanTwoHours;
    }

    public static final Creator<BloodSugar> CREATOR = new Creator<BloodSugar>() {
        @Override
        public BloodSugar createFromParcel(Parcel in) {
            return new BloodSugar(in);
        }

        @Override
        public BloodSugar[] newArray(int size) {
            return new BloodSugar[size];
        }
    };

    protected BloodSugar(Parcel in) {
        Level = in.readString();
        Time = in.readString();
        Notes = in.readString();
        Safe = Boolean.parseBoolean(in.readString());
    }

    //Getter

    public String getLevel() {
        return Level;
    }

    public String getTime() {
        return Time;
    }

    public String getNotes() {
        return Notes;
    }

    public boolean getSafe() {
        return Safe;
    }


    //Setter

    public void setLevel(String level) {
        Level = level;
    }

    public void setTime(String time) {
        Time = time;
    }

    public void setSafe(boolean isLessThanTwoHours) {
        if (isLessThanTwoHours && Integer.parseInt(Level) < 180 && Integer.parseInt(Level) > 70) {
            Safe = true;
        } else Safe = !isLessThanTwoHours;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Must be in the same order as constructor
        dest.writeString(Level);
        dest.writeString(Time);
        dest.writeString(Notes);
        dest.writeString(Boolean.toString(Safe));
    }
}
