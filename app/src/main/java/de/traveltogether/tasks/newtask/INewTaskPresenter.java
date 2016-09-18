package de.traveltogether.tasks.newtask;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface INewTaskPresenter {
    public void onCreateTask(String _title, int _id, String _description, int _author);
    public void onError(String message);
    public void onSuccess(String message, long tripId);
}
