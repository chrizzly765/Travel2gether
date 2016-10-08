package de.traveltogether.activity.newactivity;

import android.widget.ImageView;

import java.text.SimpleDateFormat;

import de.traveltogether.model.Activity;
import de.traveltogether.model.Participant;

/**
 * Created by Isa on 13.08.2016.
 */
public interface INewActivityPresenter {
    //public void onGetParticipantsForTrip(long tripId);
    //public void onSuccessGetParticipants(Participant[] participants);
    public void onError(String message);
    public void onCreateActivity(long tripId, Activity activity);
    public void onSuccessAddingActivity();
    public void onUpdateActivity(Activity activity);
    public void onGetDetailForActivity(long featureId);
    public void onSuccessGetDetail(Activity activity);
    public void onSuccessUpdateActivity();

    //public void onSuccess(String message);
}
