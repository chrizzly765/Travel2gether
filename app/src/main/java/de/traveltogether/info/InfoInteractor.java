package de.traveltogether.info;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.model.Participant;
import de.traveltogether.model.Response;
import de.traveltogether.model.Trip;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;


 class InfoInteractor implements IInfoInteractor{
    private IInfoPresenter listener;

    @Override
    public void getInfoForTripId(long tripId, IInfoPresenter _listener) {
        listener = _listener;
        JSONObject obj = new JSONObject();
        try {
            obj.put("tripId", tripId);
        }
        catch(Exception e){
            Log.e(e.getClass().toString(),e.getMessage());
        }
        HttpRequest req = new HttpRequest(DataType.TRIP, ActionType.DETAIL, obj.toString(), this);

    }

    @Override
    public void getParticipantsForTrip(long tripId, IInfoPresenter _listener) {
        listener = _listener;
        JSONObject obj = new JSONObject();
        try {
            obj.put("tripId", tripId);
        }
        catch(Exception e){
            Log.e(e.getClass().toString(),e.getMessage());
        }
        HttpRequest req = new HttpRequest(DataType.TRIP, ActionType.GETPARTICIPANTS, obj.toString(), this);
    }

    @Override
    public void deleteTrip(long tripId, IInfoPresenter _listener) {
        listener = _listener;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tripId", tripId);
            HttpRequest request = new HttpRequest(DataType.TRIP, ActionType.DELETE, jsonObject.toString(), this);
        }
        catch(Exception e){
            Log.e(e.getClass().toString(), e.getMessage());
        }
    }

    @Override
    public void leaveTrip(long tripId, IInfoPresenter listener) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tripId", tripId);
            jsonObject.put("personId", StaticData.getUserId());
            HttpRequest request = new HttpRequest(DataType.TRIP, ActionType.RESIGN, jsonObject.toString(), this);
        }
        catch (Exception e){
            Log.e(e.getClass().toString(), e.getMessage());
        }
    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(response.getError().equals("true")){
            listener.onError("Kein Zugriff", response.getMessage());
        }
        else {
            if (actionType == ActionType.DETAIL) {
                if(!response.getData().equals("{}")) {
                    Trip trip = JsonDecode.getInstance().jsonToClassByType(response.getData(), DataType.TRIP);
                    listener.onSuccessGetDetail((Trip) JsonDecode.getInstance().jsonToClassByType(response.getData(), DataType.TRIP));
                }
                else {
                    listener.onError("Kein Zugriff", "Die Reise wurde gelöscht.");
                }
            } else if (actionType == ActionType.GETPARTICIPANTS) {
                Participant[] participants = ((ParticipantList)JsonDecode.getInstance().jsonToClass(response.getData(), ParticipantList.class)).list;
                StaticTripData.setParticipants(participants);
                listener.onSuccessGetParticipants(participants);
            }
            else if(actionType==ActionType.DELETE){
                listener.onSuccessDeleteTrip();
            }
        }
    }

    private class ParticipantList{
        public Participant[] list;
    }
}
