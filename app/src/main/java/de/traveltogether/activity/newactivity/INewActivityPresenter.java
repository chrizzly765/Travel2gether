package de.traveltogether.activity.newactivity;

import de.traveltogether.model.Activity;

/**
 * Created by Isa on 13.08.2016.
 */
public interface INewActivityPresenter {
    //public void onGetParticipantsForTrip(long tripId);
    //public void onSuccessGetParticipants(Participant[] participants);
    void onError(String message, String title);
    void onCreateActivity(long tripId, Activity activity);
    void onSuccessAddingActivity();
    void onUpdateActivity(Activity activity);
    void onGetDetailForActivity(long featureId);
    void onSuccessGetDetail(Activity activity);
    void onSuccessUpdateActivity();

    //public void onSuccess(String message);
}
