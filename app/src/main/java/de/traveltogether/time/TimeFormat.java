package de.traveltogether.time;

/**
 * Created by Isa on 07.10.2016.
 */
public class TimeFormat {
    private static TimeFormat instance;

    public static TimeFormat getInstance(){
        if(instance != null){
            return instance;
        }
        else {
            instance = new TimeFormat();
            return instance;
        }
    }

    private TimeFormat(){
        instance = this;
    }

    public String getTimeWithoutSecondsWithWord(String time){
        Time t = new Time(time);
        String stringMinute= "";
        String stringHour ="";
        if (t.hour < 10) {
            stringHour = "0" + Integer.toString(t.hour);
        }
        else {
            stringHour = Integer.toString(t.hour);
        }
        if (t.minute < 10) {
            stringMinute = "0" + Integer.toString(t.minute);
        }
        else {
            stringMinute = Integer.toString(t.minute);
        }
        return stringHour + ":" + stringMinute + " Uhr";
    }

    public String getTimeWithoutSecondsWithoutWord(String time){
        Time t = new Time(time);
        String stringMinute= "";
        String stringHour ="";
        if (t.hour < 10) {
            stringHour = "0" + Integer.toString(t.hour);
        }
        else {
            stringHour = Integer.toString(t.hour);
        }
        if (t.minute < 10) {
            stringMinute = "0" + Integer.toString(t.minute);
        }
        else {
            stringMinute = Integer.toString(t.minute);
        }
        return stringHour + ":" + stringMinute;
    }


}
