package de.traveltogether.model;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Activity extends AbstractListObject {
    private int icon;
    private String destination;
    private String date;
    private String time;
   // private SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
    //private SimpleDateFormat time = new SimpleDateFormat("HH:mm");

    public int getIcon(){
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

    public void setDestination(String dest){
        destination = dest;
    }

    public void setDate(String d){
        date = d;
    }

    public void setTime(String t){
        time = t;
    }

    public void setIcon(int i){
        icon = i;
    }

    //TODO: implement description, icon, destination, time and date as optional
    public Activity(String _title, int _id, long _tripId, String _description, int _author, int _icon, String _destination, String _time, String  _date) {
        super(_title, _id, _tripId, _description, _author);
        icon = _icon;
        destination = _destination;
        time = _time;
        date = _date;
    }
}
