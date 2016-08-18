package de.traveltogether.model;

import java.text.SimpleDateFormat;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Activity extends AbstractListObject {
    private String icon;
    private String destination;
    private String date;
    private String time;
   // private SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
    //private SimpleDateFormat time = new SimpleDateFormat("HH:mm");

    public String getIcon(){
        return icon;
    }

    public String getDestination(){
        return destination;
    }

    public String getDate(){
        return date;
    }
    public String  getTime(){
        return time;
    }

    //TODO: implement description, icon, destination, time and date as optional
    public Activity(String _title, int _id, String _description, Participant _author, String _icon, String _destination, String _time, String  _date) {
        super(_title, _id, _description, _author);
        icon = _icon;
        destination = _destination;
        time = _time;
        date = _date;
    }
}
