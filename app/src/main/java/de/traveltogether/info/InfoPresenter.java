package de.traveltogether.info;

import de.traveltogether.model.Participant;
import de.traveltogether.model.Trip;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class InfoPresenter implements  IInfoPresenter {
    InfoActivity view;
    IInfoInteractor interactor;

    public InfoPresenter(InfoActivity activity){
        view= activity;
        interactor = new InfoInteractor();
    }


    @Override
    public void onGetInfoForTrip(long tripId) {
        interactor.getInfoForTripId(tripId, this);
    }

    @Override
    public void onError(String message) {
        view.onViewError(message);
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
}
