package de.traveltogether.triplist.newtrip;

import de.traveltogether.model.Trip;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface INewTripPresenter {
    public void onCreateTrip(String title, String description, String startdate, String enddate, String place);
    public void onError(String message);
    public void onSuccess(String message, long tripId);
    public void onGetDetailsForTrip(long tripId);
    public void onSuccessGetDetails(Trip trip);
    public void onSuccessUpdateTrip(String message);
    public void onUpdateTrip(Trip trip);
}
