package de.traveltogether.tasks.detail;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.StaticTripData;
import de.traveltogether.model.Participant;
import de.traveltogether.model.Response;
import de.traveltogether.model.Task;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;

/**
 * Interactor for TaskDetailActivity
 * Implements ITaskDetailInteractor
 */
public class TaskDetailInteractor implements ITaskDetailInteractor {

    private ITaskDetailPresenter listener;

    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(!response.getError().equals("true")){
            if(actionType == ActionType.DELETE){
                listener.onSuccessDelete();
            }
            else if (actionType == ActionType.DETAIL) {
                try {
                    listener.onSuccessGetDetails((Task) JsonDecode.getInstance().jsonToClassByType(response.getData(), DataType.TASK));
                }
                catch (Exception e){
                    e.printStackTrace();
                    listener.onCloseActivity();
                }
            }
            else if(actionType == ActionType.GETPARTICIPANTS){
                StaticTripData.setParticipants(((ParticipantList)JsonDecode.getInstance().jsonToClass(response.getData(), ParticipantList.class)).list);
                listener.onSuccessGetParticipants();
            }
        }
        else{
            if (actionType == ActionType.DETAIL) {
                listener.onCloseActivity();
            }
            listener.onError(response.getMessage(), response.getMessage());
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
    //public void deleteTask(long id, ITaskDetailPresenter _listener) {
    public void deleteTask(Task task, ITaskDetailPresenter _listener) {
        listener = _listener;
        try{
            JSONObject obj = new JSONObject();
            obj.put("featureId", task.getId());
            obj.put("personId", task.getPersonId());
            obj.put("author", task.getAuthor());
            HttpRequest request = new HttpRequest(DataType.TASK, ActionType.DELETE, obj.toString(), this);
        }
        catch(Exception e){
            listener.onError("Fehler beim Http Request", "Fehler" );
            Log.e("DetailTaskInteractor", e.getMessage());
        }
    }

    @Override
    public void getParticipantsForTrip(long tripId, ITaskDetailPresenter _listener) {
        listener = _listener;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tripId", tripId);
            HttpRequest request = new HttpRequest(DataType.TRIP, ActionType.GETPARTICIPANTS, jsonObject.toString(), this);
        }
        catch(Exception e){
            listener.onError("Fehler beim Http Request", "Fehler" );
            Log.e(e.getClass().toString(), e.getMessage());
        }
    }

    //Neccessary class for getting array from JsonDecode
    class ParticipantList {
        public Participant[] list;
    }
}
