package de.traveltogether.mainmenu;

import de.traveltogether.model.Participant;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface IMainMenuPresenter {
    public void onDeleteTrip(Long tripId);

    void onError(String message);

    void onSuccessDeletingTrip();

    void onLeaveTrip(long tripId, String userId);

    void onSuccessLeavingTrip();

    void onGetParticipantsForTrip(long tripId);

    void onSuccessGetParticipants();

}
