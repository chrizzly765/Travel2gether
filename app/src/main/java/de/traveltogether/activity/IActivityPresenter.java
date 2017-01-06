package de.traveltogether.activity;

import de.traveltogether.model.Activity;

interface IActivityPresenter {
    public void onGetFormerActivities(long tripId);
    public void onSuccess(Activity[] activities);
    public void onError(String message);
}
