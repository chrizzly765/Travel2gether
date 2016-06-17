package www.traveltogether.de.traveltogether.model;

import java.text.SimpleDateFormat;

import www.traveltogether.de.traveltogether.DataType;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Notification {
    private String message;
    private DataType type;
    private String id;
    private String /*SimpleDateFormat*/ receiveDate;// = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");

    public String getMessage(){
        return message;
    }

    public DataType getType(){
        return type;
    }
    public String getReceiveDate(){
        return receiveDate;
    }
    public Notification(String _text, DataType _type, String _receiveDate, String _id){
        message = _text;
        type = _type;
        receiveDate = _receiveDate;
        id =_id;
    }
}
