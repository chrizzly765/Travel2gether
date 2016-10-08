package de.traveltogether.packinglist.packingdetail;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.model.PackingObject;
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
                    listener.onSuccessGetDetails((PackingObject) JsonDecode.getInstance().jsonToClass(response.getData(), DataType.EXPENSE));
                }
                catch(Exception e){
                    listener.onError("Auf dieses Packelement kann nicht mehr zugegriffen werden.");
                    listener.onCloseActivity();
                }
            }
        }
        else{
            listener.onError(response.getMessage());
        }
    }
}
