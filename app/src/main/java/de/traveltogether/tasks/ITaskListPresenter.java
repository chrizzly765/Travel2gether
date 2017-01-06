package de.traveltogether.tasks;

import de.traveltogether.model.Task;


 interface ITaskListPresenter {
     void onGetTasks(Long tripId);
     void onSuccess(Task[] tasks);
     void onError(String message);

}
