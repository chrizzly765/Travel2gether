package de.traveltogether.activity.detailactivity;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.model.Activity;
import de.traveltogether.model.Response;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;

/**
 * Created by Isa on 05.10.2016.
 */
public class DetailActivityInteractor implements IDetailActivityInteractor {
    IDetailActivityPresenter listener;

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(response.getError()!="true"){
            if(actionType == ActionType.DELETE){
                listener.onSuccessDelete();
            }
            else if (actionType == ActionType.DETAIL) {
                try {
                    listener.onSuccessGetDetails((Activity) JsonDecode.getInstance().jsonToClass(response.getData(), DataType.ACTIVITY));

                }
                catch(Exception e){
                    e.printStackTrace();
                    listener.onCloseActivity();
                }
            }
        }
        else{
            listener.onError(response.getMessage(), response.getMessage());
        }
    }

    @Override
    public void getDetailsForActivity(long featureId, IDetailActivityPresenter _listener) {
        listener = _listener;
        try{
            JSONObject obj = new JSONObject();
            obj.put("featureId", featureId);
            HttpRequest request = new HttpRequest(DataType.ACTIVITY, ActionType.DETAIL, obj.toString(), this);
        }
        catch(Exception e){
            Log.e("DetailActiInteractor", e.getMessage());
        }
    }


    @Override
    public void deleteActivity(long id, IDetailActivityPresenter _listener) {
        listener = _listener;
        try{
            JSONObject obj = new JSONObject();
            obj.put("featureId", id);
            HttpRequest request = new HttpRequest(DataType.ACTIVITY, ActionType.DELETE, obj.toString(), this);
        }
        catch(Exception e){
            Log.e("DetailActiInteractor", e.getMessage());
        }
    }

}
