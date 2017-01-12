package de.traveltogether.tasks;

import de.traveltogether.IInteractor;

/**
 * Interface for interactor of TaskListActivity
 */
 interface ITaskListInteractor extends IInteractor {
     void getTasks(Long tripId, ITaskListPresenter listener);
}
