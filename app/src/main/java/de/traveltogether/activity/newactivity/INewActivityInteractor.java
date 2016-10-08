package de.traveltogether.activity.newactivity;

import android.widget.ImageView;

import java.text.SimpleDateFormat;

import de.traveltogether.IInteractor;
import de.traveltogether.activity.detailactivity.IDetailActivityPresenter;
import de.traveltogether.model.Activity;
import de.traveltogether.model.Participant;

/**
 * Created by Isa on 13.08.2016.
 */
public interface INewActivityInteractor extends IInteractor {
    //public void getParticipantsForTrip(long tripId, INewExpensePresenter listener);
    public void createActivity(long tripId, Activity activity, INewActivityPresenter listener);
    public void getDetailsForActivity(long featureId, INewActivityPresenter listener);
    public void updateActivity(Activity activity, INewActivityPresenter listener);
}
