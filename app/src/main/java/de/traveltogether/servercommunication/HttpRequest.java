package de.traveltogether.servercommunication;

import android.util.Log;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.IInteractor;
import de.traveltogether.model.Request;
import de.traveltogether.model.Response;

/**
 * Created by Anna-Lena on 16.05.2016.
 */
public class HttpRequest  implements IHttpRequest{

    Response responseObject;
    DataType dataType;
    ActionType actionType;
    String jsonString;
    IInteractor listener;

    public String getUrl() {
        String url = "http://www.imagik.de/traveltogether/main.php";
        return url;
    }

    public HttpRequest(DataType _dataType, ActionType _actionType, String json, IInteractor _listener) {
        dataType = _dataType;
        actionType = _actionType;
        jsonString = json;
        listener = _listener;
        Request request = new Request(_dataType, _actionType, json);
        String string = (JsonDecode.getInstance().classToJson(request)).replace("\\\"", "\"").replace("\"{", "{").replace("}\"", "}");//.replace("\\\\", "\\");
        AsyncHttpTask task = new AsyncHttpTask(this);
        task.execute(getUrl(), string);
    }

    public void setResponse(String response){

            try {

                JSONObject obj = new JSONObject(response);
                responseObject = new Response(obj.get("error").toString(), obj.get("message").toString(), obj.get("data").toString());
                if(listener!=null) {
                    listener.onRequestFinished(responseObject, dataType, actionType);
                }
            }
            catch(Exception e){
                if(e.getMessage()!=null) {
                    Log.e("Error in httpRequest", e.getMessage());
                }
                e.printStackTrace();
                if(listener!=null) {
                    listener.onRequestFinished(new Response("true", "Error", ""), dataType, actionType);
                }
        }
    }

}

