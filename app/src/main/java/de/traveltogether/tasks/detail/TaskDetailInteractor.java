package de.traveltogether.tasks.detail;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.model.Response;
import de.traveltogether.model.Task;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;

/**
 * Created by chris on 18.08.2016.
 */
public class TaskDetailInteractor implements ITaskDetailInteractor {

    ITaskDetailPresenter listener;

    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(response.getError()!="true"){
            if(actionType == ActionType.DELETE){
                listener.onSuccessDelete();
            }
            else if (actionType == ActionType.DETAIL) {
                listener.onSuccessGetDetails((Task) JsonDecode.getInstance().jsonToClass(response.getData(), DataType.TASK));
            }
        }
        else{
            listener.onError(response.getMessage());
        }
    }

    @Override
    public void getDetailsForTask(long featureId, ITaskDetailPresenter _listener) {
        listener = _listener;
        try{
            JSONObject obj = new JSONObject();
            obj.put("featureId", featureId);
            HttpRequest request = new HttpRequest(DataType.TASK, ActionType.DETAIL, obj.toString(), this);
        }
        catch(Exception e){
            Log.e("DetailTaskInteractor", e.getMessage());
        }
    }

    @Override
    public void deleteTask(long id, ITaskDetailPresenter _listener) {
        listener = _listener;
        try{
            JSONObject obj = new JSONObject();
            obj.put("featureId", id);
            HttpRequest request = new HttpRequest(DataType.TASK, ActionType.DELETE, obj.toString(), this);
        }
        catch(Exception e){
            Log.e("DetailTaskInteractor", e.getMessage());
        }
    }
}
