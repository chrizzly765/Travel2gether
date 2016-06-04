package www.traveltogether.de.traveltogether.triplist;

import www.traveltogether.de.traveltogether.model.Trip;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface ITripListPresenter {
    public void onGetTrips();
    public void onSuccess(Trip[] trips);
    public void onError(String message);
}
