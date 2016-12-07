package de.traveltogether.packinglist.packingdetail;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.StaticTripData;
import de.traveltogether.model.PackingItem;
import de.traveltogether.model.PackingObject;
import de.traveltogether.model.Participant;
import de.traveltogether.model.Response;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;

/**
 * Created by Maria Dreher on 07.10.2016.
 */
public class PackingDetailInteractor implements IPackingDetailInteractor {
    IPackingDetailPresenter listener;

    @Override
    public void getDetailsForPackingObject(long featureId, IPackingDetailPresenter _listener) {
        listener = _listener;
        try{
            JSONObject obj = new JSONObject();
            obj.put("featureId", featureId);
            HttpRequest request = new HttpRequest(DataType.PACKINGOBJECT, ActionType.DETAIL, obj.toString(), this);
        }
        catch(Exception e){
            Log.e("DetailPackingInteractor", e.getMessage());
        }
    }

    @Override
    public void deletePackingObject(long id, IPackingDetailPresenter _listener) {
        listener = _listener;
        try{
            JSONObject obj = new JSONObject();
            obj.put("featureId", id);
            HttpRequest request = new HttpRequest(DataType.PACKINGOBJECT, ActionType.DELETE, obj.toString(), this);
        }
        catch(Exception e){
            Log.e("DetailPackingInteractor", e.getMessage());
        }
    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(response.getError()!="true"){
            if(actionType == ActionType.DELETE){
                listener.onSuccessDelete();
            }
            else if (actionType == ActionType.DETAIL) {
                try {
                    PackingObject obj = (PackingObject) JsonDecode.getInstance().jsonToArray(response.getData(), PackingObject.class);
                    listener.onSuccessGetDetails(obj);//(PackingObject) JsonDecode.getInstance().jsonToClass(response.getData(), DataType.PACKINGOBJECT));
                }
                catch(Exception e){
                    e.printStackTrace();
                    listener.onCloseActivity();
                }
            }
            else if(actionType == ActionType.UPDATE){
                //TODO
            }
            else if(actionType == ActionType.GETPARTICIPANTS){
                StaticTripData.setParticipants(((ParticipantList)JsonDecode.getInstance().jsonToArray(response.getData(), ParticipantList.class)).list);
            }
        }
        else{
            if (actionType == ActionType.DETAIL) {
                listener.onCloseActivity();
            }
            listener.onError(response.getMessage(), response.getMessage());
        }
    }

    public void updatePackingItem(PackingItem item, IPackingDetailPresenter _listener) {
        listener = _listener;
        try {
            String s = JsonDecode.getInstance().classToJson(item);
            HttpRequest request = new HttpRequest(DataType.PACKINGITEM, ActionType.UPDATE, s, this);
        } catch (Exception e) {
            Log.e("DetailPackingInteractor", e.getMessage());
        }
    }

    @Override
    public void getParticipantsForTrip(long tripId, IPackingDetailPresenter _listener) {
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
