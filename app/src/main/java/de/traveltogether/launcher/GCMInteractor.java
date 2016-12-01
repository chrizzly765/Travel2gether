package de.traveltogether.launcher;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.StaticData;
import de.traveltogether.login.ILoginPresenter;
import de.traveltogether.model.Response;
import de.traveltogether.servercommunication.HttpRequest;

/**
 * Created by Anna-Lena on 25.11.2016.
 */

public class GCMInteractor implements IGCMInteractor {
    IGCMPresenter listener;
    @Override
    public void updateToken(String token, IGCMPresenter _listener) {
        try {
            listener = _listener;
            JSONObject obj = new JSONObject();
            obj.put("deviceId", token);
            obj.put("personId", StaticData.getUserId());

            //TODO: when server ready
            HttpRequest request = new HttpRequest(DataType.PERSON, ActionType.UPDATEDEVICEID, obj.toString(), this);
        }
        catch(Exception e){
            //TODO
        }
    }

    @Override
    public void sendInvitation(long tripId,int author, IGCMPresenter _listener) {
        try {
            listener = _listener;
            JSONObject obj = new JSONObject();
            obj.put("receiverId", StaticData.getUserId());
            obj.put("author", author);
            obj.put("tripId", tripId);

            HttpRequest request = new HttpRequest(DataType.INVITATION, ActionType.INVITE, obj.toString(), this);
        }
        catch(Exception e){
            //TODO
        }
    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {

    }
}
