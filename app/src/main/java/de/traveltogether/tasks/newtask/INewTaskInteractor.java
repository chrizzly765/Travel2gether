package de.traveltogether.tasks.newtask;

import de.traveltogether.IInteractor;
import de.traveltogether.model.Task;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface INewTaskInteractor extends IInteractor{
    public void createTask(Task task, INewTaskPresenter _listener);

    void updateTask(Task task, INewTaskPresenter _listener);

    void getDetailsForTask(long featureId, INewTaskPresenter _listener);
}
