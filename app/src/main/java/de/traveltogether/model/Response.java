package de.traveltogether.model;

/**
 * Model class for Response
 */
public class Response {
    private String error;
    private String message;
    private String data;

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
