package de.traveltogether.packinglist.newpackingitem;

import android.util.Log;

import org.json.JSONObject;

import java.util.Objects;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.model.PackingObject;
import de.traveltogether.model.Response;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class NewPackingItemInteractor implements INewPackingItemInteractor {
    private INewPackingItemPresenter listener;

    @Override
    public void createPackingObject(PackingObject packingObject, INewPackingItemPresenter _listener) {
        listener = _listener;
        HttpRequest request = new HttpRequest(DataType.PACKINGOBJECT, ActionType.ADD, JsonDecode.getInstance().classToJson(packingObject), this);
    }

    @Override
    public void updatePackingObject(PackingObject packingObject, INewPackingItemPresenter _listener) {
        listener = _listener;
        HttpRequest request = new HttpRequest(DataType.PACKINGOBJECT, ActionType.UPDATE, JsonDecode.getInstance().classToJson(packingObject), this);

    }

    @Override
    public void getDetailForPackingObject(long featureId, INewPackingItemPresenter _listener) {
        listener = _listener;
        try {
            JSONObject obj = new JSONObject();
            obj.put("featureId", featureId);
            HttpRequest request = new HttpRequest(DataType.PACKINGOBJECT, ActionType.DETAIL, obj.toString(), this);
        }
        catch(Exception e){
            Log.e("NewPackingInteractor", e.getMessage());
        }    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(response.getError().equals("true")){
            listener.onError(response.getMessage(), response.getMessage());
        }
        else{
            if(actionType == ActionType.UPDATE){
                listener.onSuccessUpdatePackingObject();
            }
            else if(actionType == ActionType.ADD){
                listener.onSuccessAddingPackingObject();
            }
            else if(actionType == ActionType.DETAIL){
                listener.onSuccessGetDetail((PackingObject) JsonDecode.getInstance().jsonToClass(response.getData(), DataType.PACKINGOBJECT));
            }
        }
    }
}
