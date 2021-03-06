package de.traveltogether.date;

import java.util.Calendar;

/**
 * Class for formatting one date or two dates
 */
public class DateFormat {
    private static DateFormat instance;

    public static DateFormat getInstance(){
        if(instance != null){
            return instance;
        }
        else {
            instance = new DateFormat();
            return instance;
        }
    }

    private DateFormat(){
        instance = this;
    }


    public String getDateWithoutYear(String date){
        Date d = new Date(date);
        String s = "" + String.valueOf(d.day) + "."+ d.monthAsWord;
        return s;
    }

    /**
     * @param begin Startdate of structure "DD.MM.YYYY"
     * @param end Enddate of structure "DD.MM.YYYY"
     * @return String that represents a time period of structure "SS. bis EE. monthInWord YYYY"
     */
    public String getDateFromToInWords(String begin, String end){
        Date b = new Date(begin);
        Date e = new Date(end);
        String s = "";


        if(b.month == e.month && b.year == e.year){
            s = String.valueOf(b.day) + ". bis " + String.valueOf(e.day) + ". " + b.monthAsWord + " " + b.year;
        }
        else if(b.month!=e.month && b.year == e.year){
            s = String.valueOf(b.day) + ". " + b.monthAsWord +  " bis " + String.valueOf(e.day) + ". " + e.monthAsWord + " " + b.year;
        }
        else{
            s = String.valueOf(b.day) + "." + String.valueOf(b.month) + "." + String.valueOf(b.year) + " bis " + String.valueOf(e.day) + "." + String.valueOf(e.month) + "." + String.valueOf(e.year);
        }

        return s;
    }

    public String getDateInWords(String date){
        Date d = new Date(date);
        return String.valueOf(d.day) + ". " + d.monthAsWord + " " + String.valueOf(d.year);
    }

    public  String getDateInWordsWithoutYear(String date){
        Date d = new Date(date);
        return String.valueOf(d.day) + ". " + d.monthAsWord;
    }

    /**
     * Compares two dates
     * @param date1
     * @param date2
     * @return int
     * 1 if date1 > date2
     * -1 if date1< date2
     * 0 if date1 = date2
     */
    public int compareDates(String date1, String date2){
        Date d1 = new Date(date1);
        Date d2 = new Date(date2);

        if(d1.year == d2.year){
            if(d1.month == d2.month){
                if(d1.day == d2.day){
                    return 0;
                }else if(d1.day>d2.day){
                    return 1;
                }else {
                    return -1;
                }
            }else if(d1.month > d2.month){
                return 1;
            }else{
                return -1;
            }
        }else if(d1.year > d2.year){
            return 1;
        }else{
            return -1;
        }
    }

    public String getDateAsCountdown(String startDate){
        Date d = new Date(startDate);
        String s = "";
        Calendar calander = Calendar.getInstance();
        int cDay = calander.get(Calendar.DAY_OF_MONTH);
        int cMonth = calander.get(Calendar.MONTH) + 1;
        int cYear = calander.get(Calendar.YEAR);
        s = String.valueOf(d.month - cMonth) + " : " + String.valueOf(d.day - cDay);
        return s;
    }
}
