package de.traveltogether.activity.detailactivity;

import de.traveltogether.model.Activity;


public interface IActivityDetailPresenter {
    void onGetDetailsForActivity(long featureId);
    void onSuccessGetDetails(Activity activity);
    void onError(String message, String title);
    void onDeleteActivity(long id);
    void onSuccessDelete();
    void onCloseActivity();
    void onGetParticipantsForTrip(long tripId);

}
