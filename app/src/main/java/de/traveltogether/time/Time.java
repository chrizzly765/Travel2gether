package de.traveltogether.time;

/**
 * Created by Isa on 07.10.2016.
 */
public class Time {
    int hour;
    int minute;
    int second;
    String resultTime = "";

    String languageCode = "DE";

    public Time(String timeString){

        if (timeString != null) {
            String[] s = timeString.split("\\:", -1);
            hour = Integer.parseInt(s[0]);
            minute = Integer.parseInt(s[1]);
            second = Integer.parseInt(s[2]);
        }
    }
}
