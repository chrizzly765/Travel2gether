package de.traveltogether.tasks.newtask;

import de.traveltogether.model.Task;

/**
 * Interface for presenter of NewTaskActvity
 */
public interface INewTaskPresenter {
    void onGetDetailsForTask(long featureId);
    void onSuccessGetDetails(Task task);
    void onCreateTask(Task task);
    void onUpdateTask(Task task);
    void onError(String message, String title);
    void onSuccessCreate(String message);
    void onSuccessUpdate(String message);
}
