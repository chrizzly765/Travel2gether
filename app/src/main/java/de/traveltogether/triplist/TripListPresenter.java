package de.traveltogether.triplist;

import android.util.Log;

import de.traveltogether.model.Trip;


public class TripListPresenter implements ITripListPresenter {
    private TripListActivity view;
    private  ITripListInteractor interactor;

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

    @Override
    public void onGetNotiCount(){
        interactor.getNotiCount(this);
    }

    @Override
    public void onSuccessNotiCount(int notiCount){
        view.onSuccessGetNotiCount(notiCount);
    }
}
