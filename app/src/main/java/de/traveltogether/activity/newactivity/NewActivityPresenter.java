package de.traveltogether.activity.newactivity;

import de.traveltogether.model.Activity;

/**
 * Created by Isa on 13.08.2016.
 */
public class NewActivityPresenter implements INewActivityPresenter {
    private NewActivityActivity view;
    private INewActivityInteractor interactor;

    public NewActivityPresenter(NewActivityActivity _view){
        view = _view;
        interactor = new NewActivityInteractor();
    }

    @Override
    public void onError(String message, String title) {
        view.onViewErrorMessage(message, title);
    }

    @Override
    public void onCreateActivity(long tripId, Activity activity) {
        interactor.createActivity(tripId, activity, this);
    }

    @Override
    public void onSuccessAddingActivity() {
        view.onSuccessAddingActivity();
    }

    @Override
    public void onGetDetailForActivity(long featureId) {
        interactor.getDetailsForActivity(featureId, this);
    }

    @Override
    public void onUpdateActivity(Activity activity){
        interactor.updateActivity(activity, this);
    }

    @Override
    public void onSuccessGetDetail(Activity activity) {
        view.setValues(activity);
    }

    @Override
    public void onSuccessUpdateActivity() {
        view.onSuccessUpdateActivity();
    }
}
