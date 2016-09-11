package de.traveltogether.expense.newexpense;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.model.Participant;
import de.traveltogether.model.Response;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class NewExpenseInteractor implements INewExpenseInteractor {
    INewExpensePresenter listener;

    @Override
    public void getParticipantsForTrip(long tripId, INewExpensePresenter _listener) {
        listener= _listener;
        try {
            JSONObject obj = new JSONObject();
            obj.put("tripId", tripId);
            HttpRequest request = new HttpRequest(DataType.TRIP, ActionType.GETPARTICIPANTS, obj.toString(), this);
        }
        catch(Exception e){
            Log.e("NewExpenseInteractor", e.getMessage());
        }

    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(dataType==DataType.PARTICIPANT && actionType==ActionType.LIST){
            if(response.getError()=="false") {
                Participant[] participants = ((ParticipantList) JsonDecode.getInstance().jsonToArray(response.getData(), ParticipantList.class)).list;
                listener.onSuccessGetParticipants(participants);
            }
            else{
                listener.onError(response.getMessage());
            }
        }
    }

    class ParticipantList {
        Participant[] list;
    }
}
