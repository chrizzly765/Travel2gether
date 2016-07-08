package www.traveltogether.de.traveltogether.triplist;

import android.util.Log;
import android.view.ViewDebug;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

import www.traveltogether.de.traveltogether.ActionType;
import www.traveltogether.de.traveltogether.DataType;
import www.traveltogether.de.traveltogether.StaticData;
import www.traveltogether.de.traveltogether.model.Trip;
import www.traveltogether.de.traveltogether.servercommunication.HttpRequest;
import www.traveltogether.de.traveltogether.servercommunication.JsonDecode;
import www.traveltogether.de.traveltogether.model.Response;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class TripListInteractor implements ITripListInteractor {
    ITripListPresenter listener;

    @Override
    public void getTrips(ITripListPresenter _listener) {
        listener = _listener;
        JSONObject obj = new JSONObject();
        try {
            obj.put("personId", StaticData.getUserId());
        }
        catch(Exception e){

        }
        HttpRequest req = new HttpRequest(DataType.TRIP, ActionType.LIST, obj.toString(), this);
    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(response.getError() == "true"){
            listener.onError(response.getMessage());
        }
        else{
            try {
                TripList tripList = (TripList)JsonDecode.getInstance().<TripList>jsonToArray(response.getData(), TripList.class);

                listener.onSuccess(tripList.list);
                return;
            }
            catch(Exception e){
                Log.e(e.getClass().toString(), e.getMessage());
                e.printStackTrace();
            }
        }
    }

    class TripList{
        Trip[] list;
    }

}
