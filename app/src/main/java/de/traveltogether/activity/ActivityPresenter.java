package de.traveltogether.activity;

import android.util.Log;

import de.traveltogether.model.Activity;
import de.traveltogether.model.Trip;

/**
 * Created by Anna-Lena on 28.07.2016.
 */
public class ActivityPresenter implements IActivityPresenter {
    ActivitiesActivity view;
    IActivityInteractor interactor;

    public ActivityPresenter(ActivitiesActivity _view){
        view = _view;
        interactor = new ActivityInteractor();
    }

    @Override
    public void onGetFormerActivities(long tripId) {
        interactor.getFormerActivities(tripId, this);
    }

    @Override
    public void onSuccess(Activity[] activities) { view.onViewFormerActivities(activities);}

    @Override
    public void onError(String message) {
        view.onViewError(message);
    }
}
