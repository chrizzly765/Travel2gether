package de.traveltogether.packinglist;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.StaticData;
import de.traveltogether.model.PackingObject;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;
import de.traveltogether.model.Response;


/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class PackingListInteractor implements IPackingListInteractor {

    IPackingListPresenter listener;

    @Override
    public void getPackingObjects(long tripId, IPackingListPresenter _listener) {
        listener = _listener;
        JSONObject obj = new JSONObject();
        try {
            obj.put("tripId", tripId);
        }
        catch(Exception e){

        }
        HttpRequest req = new HttpRequest(DataType.PACKINGOBJECT, ActionType.LIST, obj.toString(), this);
    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(response.getError() == "true"){
            listener.onError(response.getMessage());
        }
        else{
            try {
                PackingList packingList = (PackingList)JsonDecode.getInstance().jsonToArray(response.getData(), PackingList.class);

                listener.onSuccess(packingList.list);
                return;
            }
            catch(Exception e){
                Log.e(e.getClass().toString(), e.getMessage());
                e.printStackTrace();
                listener.onError(response.getMessage());
            }
        }
    }

    //Diese Klasse muss vorhanden sein und an JsonDecode.jsonToArray übergeben werden um ein Array aus dem Json zu erhalten
    class PackingList{
        PackingObject[] list;
    }
}
