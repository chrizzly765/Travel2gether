package de.traveltogether.tasks.newtask;

import de.traveltogether.IInteractor;
/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface INewTaskInteractor extends IInteractor{
    public void createTask(String _title, int _id, String _description, int _author, INewTaskPresenter _listener);
}
