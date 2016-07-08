package www.traveltogether.de.traveltogether.triplist;

import android.util.Log;

import www.traveltogether.de.traveltogether.model.Trip;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class TripListPresenter implements ITripListPresenter {
    TripListActivity view;
    ITripListInteractor interactor;

    public TripListPresenter(TripListActivity _view){
        view = _view;
        interactor = new TripListInteractor();
    }

    @Override
    public void onGetTrips() {
        interactor.getTrips(this);
    }

    @Override
    public void onSuccess(Trip[] trips) {

        Log.d("Success in preseneter","");
        view.onViewTrips(trips);
    }

    @Override
    public void onError(String message) {
        view.onViewError(message);
    }
}
