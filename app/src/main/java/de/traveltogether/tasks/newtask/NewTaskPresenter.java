package de.traveltogether.tasks.newtask;

import de.traveltogether.model.Task;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class NewTaskPresenter implements INewTaskPresenter {

    private NewTaskActivity view;
    INewTaskInteractor interactor;
    public NewTaskPresenter(NewTaskActivity _view){
        view = _view;
        interactor = new NewTaskInteractor();
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
    public void onCreateTask(Task task) {
        interactor.createTask(task,this);
    }

    @Override
    public void onUpdateTask(Task task) {
        interactor.updateTask(task,this);
    }

    @Override
    public void onError(String message) {
        view.onViewError(message);
    }

    @Override
    public void onSuccessCreate(String message) {
        view.onSuccessAddingTask();
    }

    @Override
    public void onSuccessUpdate(String message) {
        view.onSuccessUpdateTask();
    }

}
