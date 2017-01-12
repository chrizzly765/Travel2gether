package de.traveltogether.activity.newactivity;

import de.traveltogether.IInteractor;
import de.traveltogether.model.Activity;

/**
 * Created by Isa on 13.08.2016.
 */
public interface INewActivityInteractor extends IInteractor {
    //public void getParticipantsForTrip(long tripId, INewExpensePresenter listener);
    void createActivity(long tripId, Activity activity, INewActivityPresenter listener);
    void getDetailsForActivity(long featureId, INewActivityPresenter listener);
    void updateActivity(Activity activity, INewActivityPresenter listener);
}
