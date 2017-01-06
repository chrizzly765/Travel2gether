package de.traveltogether.tasks.detail;

import de.traveltogether.model.Task;

 interface ITaskDetailPresenter {

     void onGetDetailsForTask(long featureId);
     void onSuccessGetDetails(Task task);
     void onError(String message, String title);
     void onDeleteTask(Task task);
     void onSuccessDelete();
     void onCloseActivity();
     void onGetParticipantsForTrip(long tripId);
     void onSuccessGetParticipants();

}
