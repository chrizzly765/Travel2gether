package www.traveltogether.de.traveltogether.model;

import java.text.SimpleDateFormat;

import www.traveltogether.de.traveltogether.DataType;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class Notification {
    private String text;
    private DataType type;
    private SimpleDateFormat receiveDate = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");

    public Notification(String _text, DataType _type, SimpleDateFormat _receiveDate){
        text = _text;
        type = _type;
        receiveDate = _receiveDate;
    }
}
