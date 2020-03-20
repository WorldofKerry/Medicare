package com.example.recyclerviewintablayout;

import android.os.Parcel;
import android.os.Parcelable;

public class Symptom implements Parcelable {

    private String Name;
    private String Location;
    private  String Level;
    private String Time;

    public Symptom() {
    }

    public Symptom(String name, String location, String level, String time) {
        Name = name;
        Location = location;
        Level = level;
        Time = time;
    }

    //Getter


    protected Symptom(Parcel in) {
        // Must be in same order as writeToParcel method
        Name = in.readString();
        Location = in.readString();
        Level = in.readString();
        Time = in.readString();
    }

    public static final Creator<Symptom> CREATOR = new Creator<Symptom>() {
        @Override
        public Symptom createFromParcel(Parcel in) {
            return new Symptom(in);
        }

        @Override
        public Symptom[] newArray(int size) {
            return new Symptom[size];
        }
    };

    public String getName() {
        return Name;
    }

    public String getLocation() {
        return Location;
    }

    public String getLevel() {
        return Level;
    }

    public String getTime() {
        return Time;
    }

    //Setter


    public void setName(String name) {
        Name = name;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public void setTime(String time) {
        Time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Must be in the same order as constructor
        dest.writeString(Name);
        dest.writeString(Location);
        dest.writeString(Level);
        dest.writeString(Time);
    }
}
