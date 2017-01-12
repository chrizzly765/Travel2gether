package de.traveltogether.triplist.newtrip;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.model.Trip;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;
import de.traveltogether.model.Response;

/**
 * Interactor for NewTripActivity
 * Implements INewTripInteractor
 */
public class NewTripInteractor implements INewTripInteractor {
    private INewTripPresenter listener;

    @Override
    public void createTrip(Trip trip, INewTripPresenter _listener) {
        listener = _listener;
        String json = JsonDecode.getInstance().classToJson(trip);
        HttpRequest request = new HttpRequest(DataType.TRIP, ActionType.ADD, json, this);
    }

    @Override
    public void updateTrip(Trip trip, INewTripPresenter _listener) {
        listener = _listener;
        String json = JsonDecode.getInstance().classToJson(trip);
        HttpRequest request = new HttpRequest(DataType.TRIP, ActionType.UPDATE, json, this);
    }

    @Override
    public void getDetailsForTripId(long tripId, INewTripPresenter _listener) {
        listener = _listener;
        JSONObject obj = new JSONObject();
        try {
            obj.put("tripId", tripId);
        }
        catch(Exception e){
            listener.onError("Fehler", "Fehler beim Http Request");
            Log.e(e.getClass().toString(), e.getMessage());
        }
        HttpRequest req = new HttpRequest(DataType.TRIP, ActionType.DETAIL, obj.toString(), this);
    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(response.getError().equals("false")){
            if(actionType == ActionType.ADD) {
                long tripId = ((Trip) JsonDecode.getInstance().jsonToClassByType(response.getData(), DataType.TRIP)).getTripId();
                listener.onSuccess(response.getMessage(), tripId);
            }
            else if(actionType==ActionType.DETAIL){
                listener.onSuccessGetDetails((Trip)JsonDecode.getInstance().jsonToClassByType(response.getData(), DataType.TRIP));
            }
            else if(actionType == ActionType.UPDATE){
                listener.onSuccessUpdateTrip(response.getMessage());
            }
        }
        else{
            listener.onError(response.getMessage(), response.getMessage());
        }
    }
}
