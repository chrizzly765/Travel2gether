package de.traveltogether.time;

/**
 * Class for formatting time
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

    /**
     * @return String that represents a time of structure "mm:hh Uhr"
     */
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

    /**
     * @return String that represents a time of structure "mm:hh"
     */
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
