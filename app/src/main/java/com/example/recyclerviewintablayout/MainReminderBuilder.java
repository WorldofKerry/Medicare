package com.example.recyclerviewintablayout;

public class MainReminderBuilder {

    private String Name;
    private String Days;
    private String TimeStart;
    private String TimeEnd;

    public MainReminderBuilder() {
    }

    public MainReminderBuilder(String name, String days, String timeStart, String timeEnd) {
        Name = name;
        Days = days;
        TimeStart = timeStart;
        TimeEnd = timeEnd;
    }

    //Getter


    public String getName() {
        return Name;
    }

    public String getDays() {
        return Days;
    }

    public String getTimeStart() {
        return TimeStart;
    }

    public String getTimeEnd() {
        return TimeEnd;
    }

    //Setter


    public void setName(String name) {
        Name = name;
    }

    public void setDays(String days) {
        Days = days;
    }

    public void setTimeStart(String timeStart) {
        TimeStart = timeStart;
    }

    public void setTimeEnd(String timeEnd) {
        TimeEnd = timeEnd;
    }
}
