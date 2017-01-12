package de.traveltogether.tasks;

import de.traveltogether.model.Task;

/**
 * Interface for presenter of TaskListActivity
 */
 interface ITaskListPresenter {
     void onGetTasks(Long tripId);
     void onSuccess(Task[] tasks);
     void onError(String message);

}
