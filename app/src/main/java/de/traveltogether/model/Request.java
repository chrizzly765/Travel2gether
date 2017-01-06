package de.traveltogether.model;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;


public class Request {

    private String type;
    private String action;
    private String data;

    public Request(DataType _type, ActionType _action, String _data){
        type = _type.toString().toLowerCase();
        action = _action.toString().toLowerCase();
        data=_data;
    }

}
