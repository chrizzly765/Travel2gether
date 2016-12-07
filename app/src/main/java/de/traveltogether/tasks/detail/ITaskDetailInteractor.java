package de.traveltogether.tasks.detail;

import de.traveltogether.IInteractor;
import de.traveltogether.model.Task;
import de.traveltogether.packinglist.packingdetail.IPackingDetailPresenter;

/**
 * Created by chris on 18.08.2016.
 */
public interface ITaskDetailInteractor extends IInteractor {

    public void getDetailsForTask(long featureId, ITaskDetailPresenter _listener);
    //public void deleteTask(long id, ITaskDetailPresenter _listener);
    public void deleteTask(Task task, ITaskDetailPresenter _listener);
    public void getParticipantsForTrip(long tripId, ITaskDetailPresenter _listener);

}
