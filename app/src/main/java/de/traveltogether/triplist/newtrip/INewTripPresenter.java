package de.traveltogether.triplist.newtrip;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface INewTripPresenter {
    public void onCreateTrip(String title, String description, String startdate, String enddate, String place);
    public void onError(String message);
    public void onSuccess(String message, long tripId);
}
