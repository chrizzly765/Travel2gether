package de.traveltogether.triplist.newtrip;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.model.Trip;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;
import de.traveltogether.model.Response;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class NewTripInteractor implements INewTripInteractor {
    INewTripPresenter listener;

    @Override
    public void createTrip(String title, String description, String startdate, String enddate, String place, INewTripPresenter _listener) {
        listener = _listener;
        Trip trip = new Trip(title, description, place, startdate,enddate);
        String json = JsonDecode.getInstance().classToJson(trip);
        HttpRequest request = new HttpRequest(DataType.TRIP, ActionType.ADD, json, this);
    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(response.getError()=="false"){
            long tripId = ((Trip)JsonDecode.getInstance().jsonToClass(response.getData(), DataType.TRIP)).getTripId();
            listener.onSuccess(response.getMessage(), tripId);
        }
        else{
            listener.onError(response.getMessage());
        }
    }
}
