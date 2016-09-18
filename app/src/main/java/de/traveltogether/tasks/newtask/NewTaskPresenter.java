package de.traveltogether.tasks.newtask;

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
    public void onCreateTask(String title, int id, String description, int author) {
        interactor.createTask(title,id, description, author,this);
    }

    @Override
    public void onError(String message) { }

    @Override
    public void onSuccess(String message, long taskId) { }
}
