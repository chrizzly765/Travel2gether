package de.traveltogether.model;

/**
 * Model class for Activity
 */
public class Activity extends AbstractListObject {
    private int icon;
    private String destination;
    private String date;
    private String time;

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

    public Activity(String _title, int _id, long _tripId, String _description, int _author, int _icon, String _destination, String _time, String  _date) {
        super(_title, _id, _tripId, _description, _author);
        icon = _icon;
        destination = _destination;
        time = _time;
        date = _date;
    }
}
