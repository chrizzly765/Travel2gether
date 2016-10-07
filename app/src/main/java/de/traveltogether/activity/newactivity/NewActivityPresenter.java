package de.traveltogether.activity.newactivity;

import android.widget.ImageView;

import java.text.SimpleDateFormat;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
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
    public void onCreateActivity(String title, int id, long tripId, String description, int participant, int icon, String destination, String time, String date) {
        interactor.createActivity(title, id, tripId, description, participant, icon, destination, time, date,this);
    }

    @Override
    public void onError(String message) {
        view.onViewErrorMessage(message);
    }

    @Override
    public void onSuccess(String message) {
        view.onSuccess(message);
    }
}
