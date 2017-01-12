package de.traveltogether.tasks;


import android.util.Log;

import de.traveltogether.model.Task;

/**
 * Presenter for TaskListActivity
 * Implements ITaskListPresenter
 */
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
        view.onViewTasks(tasks);
    }

    @Override
    public void onError(String message) {
        view.onViewError(message);
    }
}
