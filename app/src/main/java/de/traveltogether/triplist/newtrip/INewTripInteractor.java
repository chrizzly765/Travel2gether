package de.traveltogether.triplist.newtrip;

import de.traveltogether.IInteractor;
import de.traveltogether.model.Trip;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface INewTripInteractor extends IInteractor{
    //public void createTrip(String title, String description, String startdate, String enddate, String place, INewTripPresenter listener);
    void createTrip(Trip trip, INewTripPresenter _listener);
    void updateTrip(Trip trip, INewTripPresenter _listener);

    public void getDetailsForTripId(long tripId, INewTripPresenter _listener);
}
