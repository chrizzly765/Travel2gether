package de.traveltogether.tasks;


import android.util.Log;

import de.traveltogether.model.Task;


public class TaskListPresenter implements ITaskListPresenter {

    private TaskListActivity view;
    private ITaskListInteractor interactor;

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
