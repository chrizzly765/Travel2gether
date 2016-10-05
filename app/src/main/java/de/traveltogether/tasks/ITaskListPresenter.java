package de.traveltogether.tasks;

import de.traveltogether.model.Task;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface ITaskListPresenter {
    public void onGetTasks(Long tripId);
    public void onSuccess(Task[] tasks);
    public void onError(String message);

}
