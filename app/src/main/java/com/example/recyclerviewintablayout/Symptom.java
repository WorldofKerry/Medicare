package com.example.recyclerviewintablayout;

public class Symptom {

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
}
