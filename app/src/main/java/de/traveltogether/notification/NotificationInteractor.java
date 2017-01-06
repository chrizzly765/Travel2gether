package de.traveltogether.notification;

import android.util.Log;

import org.json.JSONObject;

import java.util.Objects;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.StaticData;
import de.traveltogether.model.Notification;
import de.traveltogether.model.Response;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;

/**
 * Created by Anna-Lena on 16.05.2016.
 */
public class NotificationInteractor implements INotificationInteractor {
    private INotificationPresenter listener;


    @Override
    public void answerInvitation(long tripId, ActionType type, INotificationPresenter _listener) {
        listener = _listener;
        /*if(type!=ActionType.DECLINE || type!=ActionType.ACCEPT){
            listener.onError("Internal error");
            return;
        }*/
        try {
            JSONObject obj = new JSONObject();
            obj.put("tripId", tripId);
            obj.put("personId", StaticData.getUserId());
            HttpRequest req = new HttpRequest(DataType.INVITATION, type, obj.toString(), this);
        }
        catch(Exception e){
            Log.e(e.getClass().toString(), e.getMessage());
        }
    }

    @Override
    public void getNotificationList(INotificationPresenter _listener) {
        listener=_listener;
        try {
            JSONObject obj = new JSONObject();
            obj.put("personId", StaticData.getUserId());
            HttpRequest req = new HttpRequest(DataType.NOTIFICATION, ActionType.LIST, obj.toString(), this);
        }
        catch(Exception e){
            Log.e(e.getClass().toString(), e.getMessage());
        }

    }

    @Override
    public void setNotificationRead(long notificationId, INotificationPresenter _listener) {
        listener=_listener;
        try {
            JSONObject obj = new JSONObject();
            obj.put("notificationId", notificationId);
            HttpRequest req = new HttpRequest(DataType.NOTIFICATION, ActionType.UPDATE, obj.toString(), this);
        }
        catch(Exception e){
            Log.e(e.getClass().toString(), e.getMessage());
        }
    }


    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(response.getError().equals("true")){
            listener.onError(response.getMessage());
        }
        else{
            if(dataType.equals(DataType.NOTIFICATION) && actionType.equals(ActionType.LIST)) {
                NotificationList not = ((NotificationList) JsonDecode.getInstance().jsonToArray(response.getData(), NotificationList.class));
                if(not!=null) {
                    Notification[] list = not.list;
                    listener.onViewNotificationList(list);
                }
                else {
                    listener.onViewNotificationList(new Notification[0]);
                }
            }
            else if(dataType.equals(DataType.NOTIFICATION) && actionType == ActionType.UPDATE){
                listener.onSuccessSetNotificationRead();
            }
        }
    }
    class NotificationList{
        public Notification[] list;
    }
}
