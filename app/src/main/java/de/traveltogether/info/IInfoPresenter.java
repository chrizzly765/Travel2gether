package de.traveltogether.info;

import de.traveltogether.model.Participant;
import de.traveltogether.model.Trip;

/**
 * Interface for Presenter of InfoActivity
 */
interface IInfoPresenter {
     void onGetInfoForTrip(long tripId);
     void onError(String message, String Title);
     void onSuccessGetDetail(Trip trip);
     void onGetParticipantsForTrip(long tripId);
     void onSuccessGetParticipants(Participant[] participants);
     void onDeleteTrip(long tripId);
     void onSuccessDeleteTrip();
     void onLeaveTrip(long tripId);
}
