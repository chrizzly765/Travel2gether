package de.traveltogether.servercommunication;

import android.util.Log;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.IInteractor;
import de.traveltogether.model.Request;
import de.traveltogether.model.Response;


public class HttpRequest  implements IHttpRequest{

    private Response responseObject;
    private DataType dataType;
    private ActionType actionType;
    private String jsonString;
    private IInteractor listener;

    public String getUrl() {
        return "http://www.imagik.de/traveltogether/main.php";
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
                    try {
                        listener.onRequestFinished(responseObject, dataType, actionType);
                    }
                    catch(Exception e){
                        Log.e("Error", e.getMessage());
                    }
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

