package de.traveltogether.date;

/**
 * Created by Anna-Lena on 22.09.2016.
 */
public class DateFormat {
    static DateFormat instance;

    public static DateFormat getInstance(){
        if(instance != null){
            return instance;
        }
        else {
            instance = new DateFormat();
            return instance;
        }
    }

    public DateFormat(){
        instance = this;
    }

    /**
     * Gets String that represents a date of structure "DD.MM.YYYY"
     * returns String that represents date of structure "DD.MM"
     * @param date
     */
    public String getDateWithoutYear(String date){
        Date d = new Date(date);
        String s = "" + String.valueOf(d.day) + "."+ String.valueOf(d.month);
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
            s = String.valueOf(b.day) + ". " + b.monthAsWord +  " bis " + String.valueOf(e.day) + ". " + String.valueOf(e.monthAsWord) + " " + b.year;
        }
        else{
            s = String.valueOf(b.day) + "." + String.valueOf(b.month) + "." + String.valueOf(b.year) + " bis " + String.valueOf(e.day) + "." + String.valueOf(e.month) + "." + String.valueOf(e.year);
            //Alternativ falls nicht zu lange:
            // s = String .valueOf(b.day)+ ". " + b.monthAsWord + " "+ String.valueOf(b.year) + " bis " + String .valueOf(e.day)+ ". " + e.monthAsWord + " "+ String.valueOf(e.year);
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
}
