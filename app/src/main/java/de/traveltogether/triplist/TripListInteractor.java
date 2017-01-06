package de.traveltogether.triplist;

import android.util.Log;

import org.json.JSONObject;

import java.util.Objects;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.StaticData;
import de.traveltogether.model.Trip;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;
import de.traveltogether.model.Response;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class TripListInteractor implements ITripListInteractor {
    private ITripListPresenter listener;

    @Override
    public void getTrips(ITripListPresenter _listener) {
        listener = _listener;
        JSONObject obj = new JSONObject();
        try {
            obj.put("personId", StaticData.getUserId());
        }
        catch(Exception e){
            Log.e(e.getClass().toString(), e.getMessage());
        }
        HttpRequest req = new HttpRequest(DataType.TRIP, ActionType.LIST, obj.toString(), this);
    }

    @Override
    public void getNotiCount(ITripListPresenter _listener)
    {
        listener=_listener;
        JSONObject obj = new JSONObject();
        try {
            obj.put("personId", StaticData.getUserId());
        }
        catch(Exception e){
            Log.e(e.getClass().toString(), e.getMessage());
        }
        HttpRequest req = new HttpRequest(DataType.NOTIFICATION, ActionType.GETNOTICOUNT, obj.toString(), this);

    }
    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(response.getError().equals("true")){
            listener.onError(response.getMessage());
        }
        else {
            if (actionType == ActionType.LIST && dataType == DataType.TRIP) {
                try {
                    TripList tripList = (TripList) JsonDecode.getInstance().<TripList>jsonToArray(response.getData(), TripList.class);

                    listener.onSuccess(tripList.list);
                    return;
                } catch (Exception e) {
                    Log.e(e.getClass().toString(), e.getMessage());
                    e.printStackTrace();
                }
            }
            else if(actionType == ActionType.GETNOTICOUNT && dataType == DataType.NOTIFICATION){
                try {
                    listener.onSuccessNotiCount(Integer.parseInt(response.getData()));
                }
                catch(Exception e){
                    Log.e("TripListInteractor", e.getMessage());
                }
            }
        }
    }

    //Diese Klasse muss vorhanden sein und an JsonDecode.jsonToArray übergeben werden um ein Array aus dem Json zu erhalten
    class TripList{
        Trip[] list;
    }

}
