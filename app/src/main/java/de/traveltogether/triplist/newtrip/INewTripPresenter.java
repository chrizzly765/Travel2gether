package de.traveltogether.triplist.newtrip;

import de.traveltogether.model.Trip;

/**
 * Interface for presenter of NewTripActivity
 */
public interface INewTripPresenter {
    void onCreateTrip(Trip trip);
    void onUpdateTrip(Trip trip);
    void onGetDetailsForTrip(long tripId);
    void onError(String message, String title);
    void onSuccess(String message, long tripId);
    void onSuccessGetDetails(Trip trip);
    void onSuccessUpdateTrip(String message);
}
