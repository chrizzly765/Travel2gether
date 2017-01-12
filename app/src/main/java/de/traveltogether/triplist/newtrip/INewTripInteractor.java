package de.traveltogether.triplist.newtrip;

import de.traveltogether.IInteractor;
import de.traveltogether.model.Trip;

/**
 * Interface for interactor of NewTripActivity
 */
public interface INewTripInteractor extends IInteractor{
    void createTrip(Trip trip, INewTripPresenter _listener);
    void updateTrip(Trip trip, INewTripPresenter _listener);
    void getDetailsForTripId(long tripId, INewTripPresenter _listener);
}
