package de.traveltogether.activity;

import de.traveltogether.model.Activity;

/**
 * Created by Anna-Lena on 28.07.2016.
 */
public class ActivityPresenter implements IActivityPresenter {
    private ActivitiesActivity view;
    private IActivityInteractor interactor;

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
