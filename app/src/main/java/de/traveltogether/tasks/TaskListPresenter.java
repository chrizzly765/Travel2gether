package de.traveltogether.tasks;


import android.util.Log;

import de.traveltogether.model.Task;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class TaskListPresenter implements ITaskListPresenter {

    TaskListActivity view;
    ITaskListInteractor interactor;

    public TaskListPresenter(TaskListActivity _view) {
        view = _view;
        interactor = new TaskListInteractor();
    }

    @Override
    public void onGetTasks(Long tripId) {
        interactor.getTasks(tripId,this);
    }

    @Override
    public void onSuccess(Task[] tasks) {

        Log.d("Success in presenter","");
        view.onViewTasks(tasks);
    }

    @Override
    public void onError(String message) {
        view.onViewError(message);
    }
}
