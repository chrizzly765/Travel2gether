package de.traveltogether.mainmenu;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.StaticData;
import de.traveltogether.model.Participant;
import de.traveltogether.model.Response;
import de.traveltogether.model.Statistic;
import de.traveltogether.model.Trip;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class MainMenuInteractor implements IMainMenuInteractor {
    IMainMenuPresenter listener;

    @Override
    public void deleteTrip(IMainMenuPresenter _listener, Long tripId) {
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
    public void leaveTrip(long tripId, String userId, IMainMenuPresenter _listener) {
        listener = _listener;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tripId", tripId);
            jsonObject.put("personId", userId);
            HttpRequest request = new HttpRequest(DataType.TRIP, ActionType.RESIGN, jsonObject.toString(), this);
        }
        catch(Exception e){
            Log.e(e.getClass().toString(), e.getMessage());
        }
    }

    @Override
    public void getParticipantsForTrip(long tripId, IMainMenuPresenter _listener) {
        /*listener = _listener;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tripId", tripId);
            HttpRequest request = new HttpRequest(DataType.TRIP, ActionType.GETPARTICIPANTS, jsonObject.toString(), this);
        }
        catch(Exception e){
            Log.e(e.getClass().toString(), e.getMessage());
        }
*/
    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(response.getError() == "false"){
            if(dataType== DataType.TRIP && actionType == ActionType.DELETE) {
                listener.onSuccessDeletingTrip();
                return;
            }
            if(dataType== DataType.TRIP && actionType== ActionType.RESIGN){
                listener.onSuccessLeavingTrip();
                return;
            }
            /*if(dataType==DataType.TRIP && actionType==ActionType.GETPARTICIPANTS){
                if(response.getError()!="true") {
                    Participant[] participants = ((ParticipantList) JsonDecode.getInstance().jsonToArray(response.getData(), ParticipantList.class)).list;
                    StaticTripData.setParticipants(participants);
                    listener.onSuccessGetParticipants();
                    return;
                }
                else{
                    listener.onError(response.getMessage());
                }
            }*/
            if(dataType==DataType.TRIP && actionType==ActionType.GETSTATISTIC){
                if(response.getError()!="true") {
                    listener.onSuccessGetStatistics((Statistic) JsonDecode.getInstance().jsonToClass(response.getData(), DataType.STATISTIC));
                    //Statistic statistics = ((Statistic) JsonDecode.getInstance().jsonToClass(response.getData(), DataType.STATISTIC));
                    //listener.onSuccessGetStatistics(statistics);
                    return;
                }
                else{
                    listener.onError(response.getMessage());
                }
            }
        }
        else{
            listener.onError(response.getMessage());
        }

    }

    @Override
    public void getStatisticsForTrip(long tripId, int userId, IMainMenuPresenter _listener) {
        listener = _listener;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tripId", tripId);
            jsonObject.put("personId", userId);
            HttpRequest request = new HttpRequest(DataType.TRIP, ActionType.GETSTATISTIC, jsonObject.toString(), this);
        }
        catch(Exception e){
            Log.e(e.getClass().toString(), e.getMessage());
        }
    }

    @Override
    public void getTitleForTrip(long tripId, IMainMenuPresenter listener) {
        try{
            JSONObject obj = new JSONObject();
            obj.put("tripId", tripId);
            HttpRequest request = new HttpRequest(DataType.TRIP, ActionType.GETTITLE, obj.toString(), this);
        }
        catch (Exception e){

        }
    }


    class ParticipantList {
        public Participant[] list;
    }

}


