package de.traveltogether.tasks.detail;

import de.traveltogether.model.Task;

/**
 * Created by chris on 18.08.2016.
 */
public interface ITaskDetailPresenter {

    public void onGetDetailsForTask(long featureId);
    public void onSuccessGetDetails(Task task);
    public void onError(String message);
    //public void onDeleteTask(long id);
    public void onDeleteTask(Task task);
    public void onSuccessDelete();
    public void onCloseActivity();
}
