package de.traveltogether.activity;

import de.traveltogether.IInteractor;

/**
 * Interface for interactor of ActivityActivity
 */

interface IActivityInteractor extends IInteractor {
    void getFormerActivities(long tripId, IActivityPresenter listener);
}
