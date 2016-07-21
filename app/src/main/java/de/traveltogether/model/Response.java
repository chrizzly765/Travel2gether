package de.traveltogether.model;

/**
 * Created by Anna-Lena on 27.05.2016.
 */
public class Response {
    String error;
    String message;
    String data;

    public String getError(){
        return error;
    }

    public String getMessage(){
        return message;
    }

    public String getData(){
        return data;
    }

    public Response(String _error, String _msg, String _data){
        error = _error;
        message = _msg;
        data = _data;
    }
}
