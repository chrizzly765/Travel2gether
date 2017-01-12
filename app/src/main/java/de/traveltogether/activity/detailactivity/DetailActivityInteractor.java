package de.traveltogether.activity.detailactivity;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.StaticTripData;
import de.traveltogether.model.Activity;
import de.traveltogether.model.Participant;
import de.traveltogether.model.Response;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;

public class DetailActivityInteractor implements IDetailActivityInteractor {
    private IDetailActivityPresenter listener;

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(!response.getError().equals("true")){
            if(actionType == ActionType.DELETE){
                listener.onSuccessDelete();
            }
            else if (actionType == ActionType.DETAIL) {
                try {
                    listener.onSuccessGetDetails((Activity) JsonDecode.getInstance().jsonToClassByType(response.getData(), DataType.ACTIVITY));

                }
                catch(Exception e){
                    e.printStackTrace();
                    listener.onCloseActivity();
                }
            }
            else if(actionType == ActionType.GETPARTICIPANTS){
                StaticTripData.setParticipants(((ParticipantList)JsonDecode.getInstance().jsonToClass(response.getData(), ParticipantList.class)).list);
            }
        }
        else{
            if (actionType == ActionType.DETAIL) {
                listener.onCloseActivity();
            }
            listener.onError(response.getMessage(), response.getMessage());
        }
    }

    @Override
    public void getDetailsForActivity(long featureId, IDetailActivityPresenter _listener) {
        listener = _listener;
        try{
            JSONObject obj = new JSONObject();
            obj.put("featureId", featureId);
            HttpRequest request = new HttpRequest(DataType.ACTIVITY, ActionType.DETAIL, obj.toString(), this);
        }
        catch(Exception e){
            Log.e("DetailActiInteractor", e.getMessage());
        }
    }


    @Override
    public void deleteActivity(long id, IDetailActivityPresenter _listener) {
        listener = _listener;
        try{
            JSONObject obj = new JSONObject();
            obj.put("featureId", id);
            HttpRequest request = new HttpRequest(DataType.ACTIVITY, ActionType.DELETE, obj.toString(), this);
        }
        catch(Exception e){
            Log.e("DetailActiInteractor", e.getMessage());
        }
    }

    public void getParticipantsForTrip(long tripId, IDetailActivityPresenter _listener) {
        listener = _listener;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tripId", tripId);
            HttpRequest request = new HttpRequest(DataType.TRIP, ActionType.GETPARTICIPANTS, jsonObject.toString(), this);
        }
        catch(Exception e){
            Log.e(e.getClass().toString(), e.getMessage());
        }
    }
    class ParticipantList {
        public Participant[] list;
    }

}
