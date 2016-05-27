package www.traveltogether.de.traveltogether.servercommunication;

/**
 * Created by Anna-Lena on 27.05.2016.
 */
public class Response {
    String status;
    String message;
    String data;

    public String getStatus(){
        return status;
    }

    public String getMessage(){
        return message;
    }

    public String getData(){
        return data;
    }

    public Response(String _status, String _msg, String _data){
        status = _status;
        message = _msg;
        data = data;
    }
}
