package de.traveltogether.triplist.newtrip;

import de.traveltogether.model.Trip;

/**
 * Presenter for NewTripActivity
 * Implements INewTripPresenter
 */
public class NewTripPresenter implements INewTripPresenter {
    private NewTripActivity view;
    private INewTripInteractor interactor;
    public NewTripPresenter(NewTripActivity _view){
        view = _view;
        interactor = new NewTripInteractor();
    }

    @Override
    public void onCreateTrip( Trip trip) {
        interactor.createTrip(trip, this);
    }

    @Override
    public void onError(String message, String title) {
        view.onViewErrorMessage(message, title);
    }

    @Override
    public void onSuccess(String message, long tripId) {
        view.onSuccess(message, tripId);
    }

    @Override
    public void onGetDetailsForTrip(long tripId) {
        interactor.getDetailsForTripId(tripId, this);
    }

    @Override
    public void onSuccessGetDetails(Trip trip) {
        view.onFillFields(trip);
    }

    @Override
    public void onSuccessUpdateTrip(String message) {
        view.onSuccessUpdateTrip(message);
    }

    @Override
    public void onUpdateTrip(Trip trip) {
        interactor.updateTrip(trip, this);
    }


}
