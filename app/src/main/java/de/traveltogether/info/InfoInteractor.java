package de.traveltogether.info;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.StaticData;
import de.traveltogether.model.Participant;
import de.traveltogether.model.Response;
import de.traveltogether.model.Trip;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class InfoInteractor implements IInfoInteractor{
    IInfoPresenter listener;

    @Override
    public void getInfoForTripId(long tripId, IInfoPresenter _listener) {
        listener = _listener;
        JSONObject obj = new JSONObject();
        try {
            obj.put("tripId", tripId);
        }
        catch(Exception e){

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

        }
        HttpRequest req = new HttpRequest(DataType.TRIP, ActionType.GETPARTICIPANTS, obj.toString(), this);
    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(response.getError() == "true"){
            listener.onError(response.getMessage());
        }
        else {
            if (actionType == ActionType.DETAIL) {
                listener.onSuccessGetDetail((Trip)JsonDecode.getInstance().jsonToClass(response.getData(), DataType.TRIP));
            } else if (actionType == ActionType.GETPARTICIPANTS) {
                listener.onSuccessGetParticipants(((ParticipantList)JsonDecode.getInstance().jsonToArray(response.getData(), ParticipantList.class)).list);
            }
        }
    }

    class ParticipantList{
        public Participant[] list;
    }
}
