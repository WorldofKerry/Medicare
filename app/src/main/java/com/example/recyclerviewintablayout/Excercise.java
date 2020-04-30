package com.example.recyclerviewintablayout;

public class Excercise {
    private String Time;
    private String Calories;
    private String Duration;
    private String Type;
    private String WarmUp;

    public Excercise(String time, String calories, String duration, String type, String warmUp) {
        Time = time;
        Calories = calories;
        Duration = duration;
        Type = type;
        WarmUp = warmUp;
    }

    public String getTime() {return Time;}
    public String getCalories() {return Calories;}
    public String getDuration() {return Duration;}
    public String getType() {return Type;}
    public String getWarmUp() {return WarmUp;}

    public void setTime(String time) {
        Time = time;
    }
    public void setCalories(String calories) {
        Calories = calories;
    }
    public void setDuration(String duration) {
        Duration = duration;
    }
    public void setType(String type) {Type = type;}
    public void setWarmUp(String warmUp) {WarmUp = warmUp;}

}
