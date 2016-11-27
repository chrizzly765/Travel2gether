package de.traveltogether.activity.newactivity;

import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.SimpleTimeZone;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.activity.detailactivity.IDetailActivityPresenter;
import de.traveltogether.model.Activity;
import de.traveltogether.model.Trip;
import de.traveltogether.model.Participant;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;
import de.traveltogether.model.Response;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Isa on 13.08.2016.
 */
public class NewActivityInteractor implements INewActivityInteractor {
    INewActivityPresenter listener;

    @Override
    public void createActivity(long tripId, Activity activity, INewActivityPresenter _listener) {
        listener=_listener;
        try {
            String jsonString = JsonDecode.getInstance().classToJson(activity);
            JSONObject obj = new JSONObject(jsonString);
            obj.put("tripId", tripId);
            HttpRequest request = new HttpRequest(DataType.ACTIVITY, ActionType.ADD, obj.toString(), this);
        }
        catch (Exception e){
            Log.e("NewActiInteractor", e.getMessage());
        }
    }

    @Override
    public void getDetailsForActivity(long featureId, INewActivityPresenter _listener) {
        listener= _listener;
        try {
            JSONObject obj = new JSONObject();
            obj.put("featureId", featureId);
            HttpRequest request = new HttpRequest(DataType.ACTIVITY, ActionType.DETAIL, obj.toString(), this);
        }
        catch(Exception e){
            Log.e("NewActiInteractor", e.getMessage());
        }
    }

    @Override
    public void updateActivity(Activity activity, INewActivityPresenter _listener) {
        listener= _listener;
        try {
            String json = JsonDecode.getInstance().classToJson(activity);
            HttpRequest request = new HttpRequest(DataType.ACTIVITY, ActionType.UPDATE, json, this);
        }
        catch(Exception e){
            Log.e("NewActiInteractor", e.getMessage());
        }
    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {

        if (response.getError() == "false") {
            if (dataType == DataType.ACTIVITY && actionType == ActionType.ADD) {
                listener.onSuccessAddingActivity();
            } else if (dataType == DataType.ACTIVITY && actionType == ActionType.DETAIL) {
                listener.onSuccessGetDetail((Activity) JsonDecode.getInstance().jsonToClass(response.getData(), DataType.ACTIVITY));
            } else if (dataType == DataType.ACTIVITY && actionType == ActionType.UPDATE) {
                listener.onSuccessUpdateActivity();
            }
        } else {
            listener.onError(response.getMessage(), response.getMessage());
        }
        /*
        if(response.getError()=="false"){
           // long tripId = ((Trip)JsonDecode.getInstance().jsonToClass(response.getData(), DataType.TRIP)).getTripId();
            listener.onSuccess(response.getMessage());
        }
        else{
            listener.onError(response.getMessage());
        }
        */
    }


}
