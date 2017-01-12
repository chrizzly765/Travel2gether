package de.traveltogether.tasks.detail;

import de.traveltogether.IInteractor;
import de.traveltogether.model.Task;

/**
 * Interface for TaskDetailActiviy
 */
public interface ITaskDetailInteractor extends IInteractor {
    void getDetailsForTask(long featureId, ITaskDetailPresenter _listener);
    void deleteTask(Task task, ITaskDetailPresenter _listener);
    void getParticipantsForTrip(long tripId, ITaskDetailPresenter _listener);
}
