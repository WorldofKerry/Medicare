package com.example.recyclerviewintablayout;

public class BloodSugar {

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
}
