package de.traveltogether.activity.newactivity;

import de.traveltogether.model.Activity;

/**
 * Interface for Presenter of NewActivityActivity
 */
public interface INewActivityPresenter {
    void onError(String message, String title);
    void onCreateActivity(long tripId, Activity activity);
    void onSuccessAddingActivity();
    void onUpdateActivity(Activity activity);
    void onGetDetailForActivity(long featureId);
    void onSuccessGetDetail(Activity activity);
    void onSuccessUpdateActivity();
}
