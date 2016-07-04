package www.traveltogether.de.traveltogether.mainmenu;

import android.util.Log;

import org.json.JSONObject;

import www.traveltogether.de.traveltogether.ActionType;
import www.traveltogether.de.traveltogether.DataType;
import www.traveltogether.de.traveltogether.model.Response;
import www.traveltogether.de.traveltogether.servercommunication.HttpRequest;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class MainMenuInteractor implements IMainMenuInteractor {
    IMainMenuPresenter listener;

    @Override
    public void deleteTrip(IMainMenuPresenter _listener, Long tripId) {
        listener = _listener;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tripId", tripId);
            HttpRequest request = new HttpRequest(DataType.TRIP, ActionType.DELETE, jsonObject.toString(), this);
        }
        catch(Exception e){
            Log.e(e.getClass().toString(), e.getMessage());
        }

    }

    @Override
    public void leaveTrip(long tripId, String userId, IMainMenuPresenter _listener) {
        listener = _listener;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tripId", tripId);
            jsonObject.put("personId", userId);
            HttpRequest request = new HttpRequest(DataType.TRIP, ActionType.RESIGN, jsonObject.toString(), this);
        }
        catch(Exception e){
            Log.e(e.getClass().toString(), e.getMessage());
        }
    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(response.getError() == "false"){
            if(dataType== DataType.TRIP && actionType == ActionType.DELETE) {
                listener.onSuccessDeletingTrip();
                return;
            }
            if(dataType== DataType.TRIP && actionType== ActionType.RESIGN){
                listener.onSuccessLeavingTrip();
                return;
            }
        }
        else{
            listener.onError(response.getMessage());
        }
    }
}
