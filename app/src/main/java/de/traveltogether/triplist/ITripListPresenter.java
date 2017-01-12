package de.traveltogether.triplist;

import de.traveltogether.model.Trip;

/**
 * Interface for presenter of TripListActivity
 */
 interface ITripListPresenter {
    void onGetTrips();
    void onSuccess(Trip[] trips);
    void onError(String message);
    void onGetNotiCount();
    void onSuccessNotiCount(int notiCount);
}
