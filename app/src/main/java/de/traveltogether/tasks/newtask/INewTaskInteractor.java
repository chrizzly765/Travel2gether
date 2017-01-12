package de.traveltogether.tasks.newtask;

import de.traveltogether.IInteractor;
import de.traveltogether.model.Task;

/**
 * Interface for interactor of NewTaskActivity
 */
public interface INewTaskInteractor extends IInteractor{
    void createTask(Task task, INewTaskPresenter _listener);
    void updateTask(Task task, INewTaskPresenter _listener);
    void getDetailsForTask(long featureId, INewTaskPresenter _listener);
}
