package de.traveltogether.tasks;

import android.util.Log;

import org.json.JSONObject;

import java.util.Objects;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.model.Response;
import de.traveltogether.model.Task;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;

/**
 * Created by Anna-Lena on 12.05.2016.
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
                TaskList taskList = (TaskList) JsonDecode.getInstance().<TaskList>jsonToArray(response.getData(), TaskList.class);

                listener.onSuccess(taskList.list);
                return;
            }
            catch(Exception e){
                Log.e(e.getClass().toString(), e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //Diese Klasse muss vorhanden sein und an JsonDecode.jsonToArray übergeben werden um ein Array aus dem Json zu erhalten
    class TaskList{
        Task[] list;
    }
}
