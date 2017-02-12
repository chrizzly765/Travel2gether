package de.traveltogether.activity;

import de.traveltogether.model.Activity;

/**
 * Presenter for ActivityActivity
 * Implements IActivityPresenter
 */
public class ActivityPresenter implements IActivityPresenter {
    private ActivityActivity view;
    private IActivityInteractor interactor;

    public ActivityPresenter(ActivityActivity _view){
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
