package de.traveltogether.tasks;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.model.Response;
import de.traveltogether.model.Task;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;

/**
 * Interactor for TaskListActivity
 * Implements ITaskListInteractor
 */
public class TaskListInteractor implements ITaskListInteractor {

    private ITaskListPresenter listener;

    @Override
    public void getTasks(Long tripId, ITaskListPresenter _listener) {
        listener = _listener;
        JSONObject obj = new JSONObject();
        try {
            obj.put("tripId", tripId);
        }
        catch(Exception e){
            listener.onError("Fehler beim Http Request");
            Log.e(e.getClass().toString(), e.getMessage());
        }
        HttpRequest req = new HttpRequest(DataType.TASK, ActionType.LIST, obj.toString(), this);
    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(response.getError().equals("true")){
            listener.onError(response.getMessage());
        }
        else{
            try {
                TaskList taskList = (TaskList) JsonDecode.getInstance().jsonToClass(response.getData(), TaskList.class);
                listener.onSuccess(taskList.list);
                return;
            }
            catch(Exception e){
                listener.onError("Fehler beim Http Request");
                Log.e(e.getClass().toString(), e.getMessage());
            }
        }
    }

    //Neccessary class for getting array from JsonDecode
    class TaskList{
        Task[] list;
    }
}
