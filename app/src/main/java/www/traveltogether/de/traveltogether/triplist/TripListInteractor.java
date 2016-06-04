package www.traveltogether.de.traveltogether.triplist;

import www.traveltogether.de.traveltogether.ActionType;
import www.traveltogether.de.traveltogether.DataType;
import www.traveltogether.de.traveltogether.StaticData;
import www.traveltogether.de.traveltogether.model.Trip;
import www.traveltogether.de.traveltogether.servercommunication.JsonDecode;
import www.traveltogether.de.traveltogether.servercommunication.Request;
import www.traveltogether.de.traveltogether.servercommunication.Response;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class TripListInteractor implements ITripListInteractor {
    ITripListPresenter listener;

    @Override
    public void getTrips(ITripListPresenter _listener) {
        listener = _listener;
        String json = JsonDecode.getInstance().classToJson(new StaticData());
        Request request = new Request(DataType.TRIP, ActionType.LIST, json);
    }

    @Override
    public void onRequestFinished(Response response) {
        if(response.getError() == "true"){
            listener.onError(response.getMessage());
        }
        else{
            Trip[] trips = (Trip[])JsonDecode.getInstance().jsonToArray(response.getData(), DataType.TRIP);
        }
    }
}
