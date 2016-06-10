package www.traveltogether.de.traveltogether.triplist.newtrip;

import www.traveltogether.de.traveltogether.ActionType;
import www.traveltogether.de.traveltogether.DataType;
import www.traveltogether.de.traveltogether.model.Trip;
import www.traveltogether.de.traveltogether.servercommunication.HttpRequest;
import www.traveltogether.de.traveltogether.servercommunication.JsonDecode;
import www.traveltogether.de.traveltogether.servercommunication.Response;

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
    public void onRequestFinished(Response response) {
        if(response.getError()=="false"){
            listener.onSuccess(response.getMessage());
        }
        else{
            listener.onError(response.getMessage());
        }
    }
}
