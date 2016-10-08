package de.traveltogether.info;

import de.traveltogether.model.Participant;
import de.traveltogether.model.Trip;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface IInfoPresenter {
    public void onGetInfoForTrip(long tripId);
    public void onError(String message);
    public void onSuccessGetDetail(Trip trip);
    public void onGetParticipantsForTrip(long tripId);
    public void onSuccessGetParticipants(Participant[] participants);
    public void onDeleteTrip(long tripId);
    public void onSuccessDeleteTrip();
}
