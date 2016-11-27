package de.traveltogether.tasks.newtask;

import de.traveltogether.model.Task;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface INewTaskPresenter {
    void onGetDetailsForTask(long featureId);

    void onSuccessGetDetails(Task task);

    public void onCreateTask(Task task);

    void onUpdateTask(Task task);

    public void onError(String message, String title);
    public void onSuccessCreate(String message);

    void onSuccessUpdate(String message);
}
