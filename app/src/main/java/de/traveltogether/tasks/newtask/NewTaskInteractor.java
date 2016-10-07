package de.traveltogether.tasks.newtask;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.model.Response;
import de.traveltogether.model.Task;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class NewTaskInteractor implements INewTaskInteractor {

    INewTaskPresenter listener;

    @Override
    public void createTask(Task task, INewTaskPresenter _listener) {

        listener = _listener;
        HttpRequest request = new HttpRequest(DataType.TASK, ActionType.ADD, JsonDecode.getInstance().classToJson(task), this);
    }

    @Override
    public void updateTask(Task task, INewTaskPresenter _listener) {

        listener = _listener;
        HttpRequest request = new HttpRequest(DataType.TASK, ActionType.UPDATE, JsonDecode.getInstance().classToJson(task), this);

    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(response.getError()=="false"){
            //long taskId = ((Task) JsonDecode.getInstance().jsonToClass(response.getData(), DataType.TASK)).getId();
            if (actionType == ActionType.ADD) {
                listener.onSuccessCreate(response.getMessage());
            }
            else if (actionType == ActionType.UPDATE) {
                listener.onSuccessUpdate(response.getMessage());
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
    public void getDetailsForTask(long featureId, INewTaskPresenter _listener) {
        listener = _listener;
        try{
            JSONObject obj = new JSONObject();
            obj.put("featureId", featureId);
            HttpRequest request = new HttpRequest(DataType.TASK, ActionType.DETAIL, obj.toString(), this);
        }
        catch(Exception e){
            Log.e("NewTaskInteractor", e.getMessage());
        }
    }
}
