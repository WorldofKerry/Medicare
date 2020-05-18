package com.example.recyclerviewintablayout;

public class Reminder {

    private String Activity;
    private String Time;
    private String Repeat;
    private String Icon;

    public Reminder() {
    }

    public Reminder(String level, String time, String repeat, String icon) {
        Activity = level;
        Time = time;
        Repeat = repeat;
        Icon = icon;
    }

    //Getter

    public String getActivity() { return Activity; }

    public String getTime() {
        return Time;
    }

    public String getRepeat() {
        return Repeat;
    }

    public String getIcon() {
        return Icon;
    }


    //Setter

    public void setActivity(String activity) {
        Activity = activity;
    }

    public void setTime(String time) {
        Time = time;
    }

    public void setIcon(String icon) { Icon = icon;}

    public void setRepeat(String repeat) {
        Repeat = repeat;
    }
}

