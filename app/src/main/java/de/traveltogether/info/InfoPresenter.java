package de.traveltogether.info;

import de.traveltogether.model.Participant;
import de.traveltogether.model.Trip;


class InfoPresenter implements  IInfoPresenter {
    private InfoActivity view;
    private IInfoInteractor interactor;

    InfoPresenter(InfoActivity activity){
        view= activity;
        interactor = new InfoInteractor();
    }


    @Override
    public void onGetInfoForTrip(long tripId) {
        interactor.getInfoForTripId(tripId, this);
    }

    @Override
    public void onError(String message, String title) {
        view.onViewError(message, title);
    }

    @Override
    public void onSuccessGetDetail(Trip trip) {
        view.onViewDetail(trip);
    }

    @Override
    public void onGetParticipantsForTrip(long tripId) {
        interactor.getParticipantsForTrip(tripId, this);
    }

    @Override
    public void onSuccessGetParticipants(Participant[] participants) {
        view.onViewParticipants(participants);
    }

    @Override
    public void onDeleteTrip(long tripId) {
        interactor.deleteTrip(tripId, this);
    }

    @Override
    public void onSuccessDeleteTrip() {
        view.onSuccessDeleteTrip();
    }

    @Override
    public void onLeaveTrip(long tripId) {
        interactor.leaveTrip(tripId, this);
    }
}
