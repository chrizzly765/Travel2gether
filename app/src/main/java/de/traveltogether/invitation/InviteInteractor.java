package de.traveltogether.invitation;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.StaticData;
import de.traveltogether.model.Person;
import de.traveltogether.model.Response;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;

/**
 * Created by Anna-Lena on 16.05.2016.
 */
public class InviteInteractor implements IInviteInteractor {
    private IInvitePresenter listener;

    @Override
    public void getFormerParticipants(long tripId, IInvitePresenter _listener) {
        listener = _listener;
        JSONObject obj = new JSONObject();
        try {
            obj.put("personId", StaticData.getUserId());
            obj.put("tripId", tripId);
        } catch (Exception e) {
            Log.e(e.getClass().toString(), e.getMessage());
        }
        HttpRequest req = new HttpRequest(DataType.INVITATION, ActionType.GETPARTICIPANTS, obj.toString(), this);
    }

    @Override
    public void invite(long tripId, int receiverId, IInvitePresenter _listener) {
        listener = _listener;
        JSONObject obj = new JSONObject();
        try {
            obj.put("receiverId", receiverId);
            obj.put("author", StaticData.getUserId());
            obj.put("tripId", tripId);
        } catch (Exception e) {
            Log.e(e.getClass().toString(), e.getMessage());
        }
        HttpRequest req = new HttpRequest(DataType.INVITATION, ActionType.INVITE, obj.toString(), this);
    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(actionType == ActionType.GETPARTICIPANTS) {
            if (response.getError().equals("true")) {
                Log.e("Error in Interactor", response.getMessage());
                listener.onError(response.getMessage());
            } else {
                PersonList persons = (PersonList)JsonDecode.getInstance().jsonToClass(response.getData(), PersonList.class);
                if(persons!=null) {
                    listener.onShowParticipants(persons.list);
                }
                else {
                    listener.onShowParticipants(new Person[0]);
                }
            }
        }
        else if(actionType == ActionType.INVITE){
            if (response.getError().equals("true")) {
                listener.onError(response.getMessage());
            } else {
                listener.onInviteSuccess();
            }
        }
    }

    class PersonList{
        Person[] list;
    }
}
