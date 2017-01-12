package de.traveltogether.activity;

import de.traveltogether.model.Activity;

interface IActivityPresenter {
    void onGetFormerActivities(long tripId);
    void onSuccess(Activity[] activities);
    void onError(String message);
}
