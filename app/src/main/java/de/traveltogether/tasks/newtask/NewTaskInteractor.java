package de.traveltogether.tasks.newtask;

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
    public void createTask(String title, int id, String description, int author, INewTaskPresenter _listener) {
        listener = _listener;
        Task task = new Task(title, id, description, author);
        String json = JsonDecode.getInstance().classToJson(task);
        HttpRequest request = new HttpRequest(DataType.TASK, ActionType.ADD, json, this);
    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(response.getError()=="false"){
            long taskId = ((Task) JsonDecode.getInstance().jsonToClass(response.getData(), DataType.TASK)).getTaskId();
            listener.onSuccess(response.getMessage(), taskId);
        }
        else{
            listener.onError(response.getMessage());
        }
    }
}
