package de.traveltogether.date;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Anna-Lena on 22.09.2016.
 */
public class Date {
    int month;
    int year;
    int day;
    String monthAsWord = "";

    private long currentDateInSeconds;
    private long dateInSeconds;
    private SimpleDateFormat dateFormat;
    private String date;

    String languageCode = "DE";

    public Date(String dateString){
            String[] s = dateString.split("\\.", -1);
            day = Integer.parseInt(s[0]);
            month = Integer.parseInt(s[1]);
            year = Integer.parseInt(s[2]);
            date = dateString;

            switch (month) {
                case 1:
                    monthAsWord = "Januar";
                    break;
                case 2:
                    monthAsWord = "Februar";
                    break;
                case 3:
                    monthAsWord = "März";
                    break;
                case 4:
                    monthAsWord = "April";
                    break;
                case 5:
                    monthAsWord = "Mai";
                    break;
                case 6:
                    monthAsWord = "Juni";
                    break;
                case 7:
                    monthAsWord = "Juli";
                    break;
                case 8:
                    monthAsWord = "August";
                    break;
                case 9:
                    monthAsWord = "September";
                    break;
                case 10:
                    monthAsWord = "Oktober";
                    break;
                case 11:
                    monthAsWord = "November";
                    break;
                case 12:
                    monthAsWord = "Dezember";
                    break;
                default:
                    monthAsWord = "Januar";
                    break;
            }
    }

    private void setDateFormat(String format) {
        dateFormat = new SimpleDateFormat(format);
    }

    private void setDateInSeconds() {

        try {
            dateInSeconds = dateFormat.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setCurrentDateInSeconds() {
        currentDateInSeconds = (new java.util.Date().getTime());
    }

    /**
     * true if date >= current date
     * false if date < current date
     * @return boolean
     */
    public boolean compareDateWithCurrent() {

        setDateFormat("dd.MM.yyyy");
        setDateInSeconds();
        setCurrentDateInSeconds();

        if(dateInSeconds >= currentDateInSeconds) {
            return true;
        }
        else {
            return false;
        }
    }
}
