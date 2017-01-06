package de.traveltogether.activity;

import android.util.Log;

import org.json.JSONObject;

import java.util.Objects;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.model.Activity;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;
import de.traveltogether.model.Response;

/**
 * Created by Anna-Lena on 28.07.2016.
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

        }
        HttpRequest req = new HttpRequest(DataType.ACTIVITY, ActionType.LIST, obj.toString(), this);
    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {

        if (response.getError().equals("true")) {
            Log.e("Error in Interactor", response.getMessage());
            listener.onError(response.getMessage());
        } else {
            ActivityList activities = (ActivityList)JsonDecode.getInstance().<ActivityList>jsonToArray(response.getData(), ActivityList.class);
            listener.onSuccess(activities.list);

        }

    }

    //Diese Klasse muss vorhanden sein und an JsonDecode.jsonToArray übergeben werden um ein Array aus dem Json zu erhalten
    class ActivityList{
        Activity[] list;
    }
}
