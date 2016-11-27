package de.traveltogether.mainmenu;

import de.traveltogether.model.Participant;
import de.traveltogether.model.Statistic;
import de.traveltogether.model.Trip;

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
    void onGetStatistics(long tripId, int userId);
    void onSuccessGetStatistics(Statistic statistic);
    void onGetTitleForTrip(long tripId);
}
