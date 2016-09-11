package de.traveltogether.model;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Notification {
    private String message;
    private String type;
    private long id;
    private long tripOrFeatureId;
    private String /*SimpleDateFormat*/ added;// = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
    private Boolean opened = false;

    public String getMessage(){
        return message;
    }
    public String getType(){
        return type;
    }
    public String getReceiveDate(){
        return added;
    }
    public long getId(){return id;}
    public long getFeatureOrTripId(){return tripOrFeatureId;}
    public Boolean getOpened(){return opened;}

    public Notification(String _text, String _type, String _receiveDate, long _id, long _featureId){
        message = _text;
        type = _type;
        added = _receiveDate;
        id =_id;
        tripOrFeatureId =_featureId;
    }
}
