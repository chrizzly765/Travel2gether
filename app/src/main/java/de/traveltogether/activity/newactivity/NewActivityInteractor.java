package de.traveltogether.activity.newactivity;

import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.SimpleTimeZone;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.model.Activity;
import de.traveltogether.model.Trip;
import de.traveltogether.model.Participant;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;
import de.traveltogether.model.Response;

/**
 * Created by Isa on 13.08.2016.
 */
public class NewActivityInteractor implements INewActivityInteractor {
    INewActivityPresenter listener;

    @Override
    public void createActivity(String title, int id, long tripId, String description, int participant, int icon, String destination, String time, String date, INewActivityPresenter _listener) {
        listener = _listener;
        Activity activity = new Activity(title, id, tripId, description, participant, icon, destination, time, date );
        String json = JsonDecode.getInstance().classToJson(activity);
        HttpRequest request = new HttpRequest(DataType.ACTIVITY, ActionType.ADD, json, this);
    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(response.getError()=="false"){
           // long tripId = ((Trip)JsonDecode.getInstance().jsonToClass(response.getData(), DataType.TRIP)).getTripId();
            listener.onSuccess(response.getMessage());
        }
        else{
            listener.onError(response.getMessage());
        }
    }
}
