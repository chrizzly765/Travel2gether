package de.traveltogether.tasks;

import de.traveltogether.IInteractor;


 interface ITaskListInteractor extends IInteractor {

     void getTasks(Long tripId, ITaskListPresenter listener);
}
