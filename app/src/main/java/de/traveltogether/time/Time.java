package de.traveltogether.time;

/**
 * Class representing time
 */
class Time {
    int hour;
    int minute;
    int second;

    public Time(String timeString){
        if (timeString != null) {
            String[] s = timeString.split("\\:", -1);
            hour = Integer.parseInt(s[0]);
            minute = Integer.parseInt(s[1]);
            second = Integer.parseInt(s[2]);
        }
    }
}
