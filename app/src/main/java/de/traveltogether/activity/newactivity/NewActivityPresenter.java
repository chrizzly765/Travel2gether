package de.traveltogether.activity.newactivity;

import android.widget.ImageView;

import java.text.SimpleDateFormat;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.model.Activity;
import de.traveltogether.model.Participant;
import de.traveltogether.model.Response;

/**
 * Created by Isa on 13.08.2016.
 */
public class NewActivityPresenter implements INewActivityPresenter {
    private NewActivityActivity view;
    INewActivityInteractor interactor;

    public NewActivityPresenter(NewActivityActivity _view){
        view = _view;
        interactor = new NewActivityInteractor();
    }

    @Override
    public void onError(String message) {
        view.onViewErrorMessage(message);
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
