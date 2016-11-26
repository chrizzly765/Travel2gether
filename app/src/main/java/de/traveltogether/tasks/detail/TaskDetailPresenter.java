package de.traveltogether.tasks.detail;

import de.traveltogether.model.Task;

/**
 * Created by chris on 18.08.2016.
 */
public class TaskDetailPresenter implements ITaskDetailPresenter {

    TaskDetailActivity view;
    ITaskDetailInteractor interactor;

    public TaskDetailPresenter(TaskDetailActivity _view){
        view= _view;
        interactor= new TaskDetailInteractor();
    }

    @Override
    public void onGetDetailsForTask(long featureId) {
        interactor.getDetailsForTask(featureId, this);
    }

    @Override
    public void onSuccessGetDetails(Task task) {
        view.onViewDetails(task);
    }

    @Override
    public void onError(String message) {
        view.onViewError(message);
    }

    @Override
    //public void onDeleteTask(long id) {
    public void onDeleteTask(Task task) {
        interactor.deleteTask(task, this);
    }

    @Override
    public void onSuccessDelete() {
        view.onSuccessDelete();
    }

    public void onCloseActivity(){
        view.onCloseActivity();
    }
}
