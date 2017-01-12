package de.traveltogether.servercommunication;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.IInteractor;
import de.traveltogether.model.Request;
import de.traveltogether.model.Response;

/**
 * Class for sending http requests to the server
 * Implements IHttpRequest
 */
public class HttpRequest  implements IHttpRequest{
    private Response responseObject;
    private DataType dataType;
    private ActionType actionType;
    private String jsonString;
    private IInteractor listener;
    private static String getUrl() {
        return "http://www.imagik.de/traveltogether/main.php";
    }

    /**
     * Constructor for HttpRequest
     * Directly starts request
     * @param _dataType DataType of the request
     * @param _actionType ActionType of the request
     * @param json data
     * @param _listener listener that takes response
     */
    public HttpRequest(DataType _dataType, ActionType _actionType, String json, IInteractor _listener) {
        dataType = _dataType;
        actionType = _actionType;
        jsonString = json;
        listener = _listener;
        Request request = new Request(_dataType, _actionType, json);

        //replace symbols that cannot be processed on server
        String string = (JsonDecode.getInstance().classToJson(request)).replace("\\\"", "\"").replace("\"{", "{").replace("}\"", "}");
        AsyncHttpTask task = new AsyncHttpTask(this);
        task.execute(getUrl(), string);
    }

    /**
     * Returns the response to the listener
     * @param response
     */
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

