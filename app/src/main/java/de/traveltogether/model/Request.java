package de.traveltogether.model;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;

/**
 * Created by Anna-Lena on 28.05.2016.
 */
public class Request {

    String type;
    String action;
    String data;

    public Request(DataType _type, ActionType _action, String _data){
        type = _type.toString().toLowerCase();
        action = _action.toString().toLowerCase();
        data=_data;
    }

}