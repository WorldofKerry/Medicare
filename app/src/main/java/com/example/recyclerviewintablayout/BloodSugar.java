package com.example.recyclerviewintablayout;

public class BloodSugar {

    private  String Level;
    private String Time;

    public BloodSugar() {
    }

    public BloodSugar(String level, String time) {
        Level = level;
        Time = time;
    }

    //Getter

    public String getLevel() {
        return Level;
    }

    public String getTime() {
        return Time;
    }

    //Setter

    public void setLevel(String level) {
        Level = level;
    }

    public void setTime(String time) {
        Time = time;
    }
}
