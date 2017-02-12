package de.traveltogether.activity.newactivity;

import de.traveltogether.IInteractor;
import de.traveltogether.model.Activity;

/**
 * Interface for interactor of NewActivityActivity
 */
public interface INewActivityInteractor extends IInteractor {
    void createActivity(long tripId, Activity activity, INewActivityPresenter listener);
    void getDetailsForActivity(long featureId, INewActivityPresenter listener);
    void updateActivity(Activity activity, INewActivityPresenter listener);
}
