package de.traveltogether.triplist;

import de.traveltogether.IInteractor;

/**
 * Interface for interactor of TripListActivity
 */
 interface ITripListInteractor extends IInteractor {
    void getTrips(ITripListPresenter listener);
    void getNotiCount(ITripListPresenter _listener);
}
