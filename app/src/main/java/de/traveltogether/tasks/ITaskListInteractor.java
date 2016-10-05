package de.traveltogether.tasks;

import de.traveltogether.IInteractor;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface ITaskListInteractor extends IInteractor {

    public void getTasks(Long tripId, ITaskListPresenter listener);
}
