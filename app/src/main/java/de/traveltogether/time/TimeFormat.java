package de.traveltogether.time;

/**
 * Created by Isa on 07.10.2016.
 */
public class TimeFormat {
    static TimeFormat instance;

    public static TimeFormat getInstance(){
        if(instance != null){
            return instance;
        }
        else {
            instance = new TimeFormat();
            return instance;
        }
    }

    public TimeFormat(){
        instance = this;
    }

    public String getTimeWithoutSeconds(String time){
        Time t = new Time(time);
        String s = "" + String.valueOf(t.hour) + "."+ String.valueOf(t.minute);
        return s;
    }

    public String getTimeWithoutSecondsWithWord(String time){
        Time t = new Time(time);
        return String.valueOf(t.hour) + ":" + String.valueOf(t.minute) + " Uhr";
    }
}
