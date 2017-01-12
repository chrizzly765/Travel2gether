package de.traveltogether;

import android.util.Log;

import org.json.JSONObject;
import de.traveltogether.model.Participant;
import de.traveltogether.model.Response;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;

/**
 * Interactor for getting the participants for a trip
 * Doesn't belong to an activity
 */
public class StaticInteractor implements IInteractor {
    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        StaticTripData.setParticipants(((ParticipantList)JsonDecode.getInstance().jsonToClass(response.getData(), ParticipantList.class)).list);
    }

    public void getParticipantsForTrip(long tripId){
        try {
            JSONObject obj = new JSONObject();
            obj.put("tripId", tripId);
            HttpRequest request = new HttpRequest(DataType.TRIP, ActionType.GETPARTICIPANTS, obj.toString(), this);
        }
        catch (Exception e){
            Log.e(e.getClass().toString(), e.getMessage());
        }
    }

    class ParticipantList {
        Participant[] list;
    }
}
