package de.traveltogether.activity;

import de.traveltogether.model.Activity;

/**
 * Interface for presenter of ActivityActivity
 */
interface IActivityPresenter {
    void onGetFormerActivities(long tripId);
    void onSuccess(Activity[] activities);
    void onError(String message);
}
