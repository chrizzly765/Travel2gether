package de.traveltogether.launcher;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.StaticData;
import de.traveltogether.model.Response;
import de.traveltogether.servercommunication.HttpRequest;

/**
 * Interactor for LauncherActivity
 * Implements ILauncherInteractor
 */
public class LauncherInteractor implements ILauncherInteractor {
    private ILauncherPresenter listener;
    @Override
    public void updateToken(String token, ILauncherPresenter _listener) {
        try {
            listener = _listener;
            JSONObject obj = new JSONObject();
            obj.put("deviceId", token);
            obj.put("personId", StaticData.getUserId());
            HttpRequest request = new HttpRequest(DataType.PERSON, ActionType.UPDATEDEVICEID, obj.toString(), this);
        }
        catch(Exception e){
            Log.e(e.getClass().toString(), e.getMessage());
        }
    }

    @Override
    public void sendInvitation(long tripId, int author, ILauncherPresenter _listener) {
        try {
            listener = _listener;
            JSONObject obj = new JSONObject();
            obj.put("receiverId", StaticData.getUserId());
            obj.put("author", author);
            obj.put("tripId", tripId);

            HttpRequest request = new HttpRequest(DataType.INVITATION, ActionType.INVITE, obj.toString(), this);
        }
        catch(Exception e){
            Log.e(e.getClass().toString(), e.getMessage());
        }
    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        //No answeres necessary
    }
}
