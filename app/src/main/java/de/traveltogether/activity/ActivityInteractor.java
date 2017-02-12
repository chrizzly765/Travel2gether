package de.traveltogether.activity;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.model.Activity;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;
import de.traveltogether.model.Response;

/**
 * Interactor for ActivityActivity
 * Implements IActivityInteractor
 */
public class ActivityInteractor implements IActivityInteractor {

    private IActivityPresenter listener;

    @Override
    public void getFormerActivities(long tripId, IActivityPresenter _listener) {
        listener = _listener;
        JSONObject obj = new JSONObject();
        try {
            obj.put("tripId", tripId);
        }
        catch(Exception e){
            listener.onError("Fehler beim Http Request");
            Log.e(e.getClass().toString(), e.getMessage());
        }
        HttpRequest req = new HttpRequest(DataType.ACTIVITY, ActionType.LIST, obj.toString(), this);
    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if (response.getError().equals("true")) {
            Log.e("Error in Interactor", response.getMessage());
            listener.onError(response.getMessage());
        } else {
            ActivityList activities = (ActivityList)JsonDecode.getInstance().jsonToClass(response.getData(), ActivityList.class);
            listener.onSuccess(activities.list);
        }
    }

    /**
     * Necessary class for getting array from json string
     */
    class ActivityList{
        Activity[] list;
    }
}
