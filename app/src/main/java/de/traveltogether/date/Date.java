package de.traveltogether.date;

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
                    monthAsWord = "Jan";
                    break;
                case 2:
                    monthAsWord = "Feb";
                    break;
                case 3:
                    monthAsWord = "Mär";
                    break;
                case 4:
                    monthAsWord = "Apr";
                    break;
                case 5:
                    monthAsWord = "Mai";
                    break;
                case 6:
                    monthAsWord = "Jun";
                    break;
                case 7:
                    monthAsWord = "Jul";
                    break;
                case 8:
                    monthAsWord = "Aug";
                    break;
                case 9:
                    monthAsWord = "Sep";
                    break;
                case 10:
                    monthAsWord = "Okt";
                    break;
                case 11:
                    monthAsWord = "Nov";
                    break;
                case 12:
                    monthAsWord = "Dez";
                    break;
                default:
                    monthAsWord = "Jan";
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
