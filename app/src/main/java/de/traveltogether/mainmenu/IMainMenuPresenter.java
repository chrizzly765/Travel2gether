package de.traveltogether.mainmenu;

import de.traveltogether.model.Statistic;

/**
 * Interface for presenter of MainActivity
 */
interface IMainMenuPresenter {
    void onDeleteTrip(Long tripId);
    void onError(String message);
    void onSuccessDeletingTrip();
    void onLeaveTrip(long tripId, String userId);
    void onSuccessLeavingTrip();
    void onGetParticipantsForTrip(long tripId);
    void onSuccessGetParticipants();
    void onGetStatistics(long tripId, int userId);
    void onSuccessGetStatistics(Statistic statistic);
    void onGetTitleForTrip(long tripId);
    void onSuccessGetTitle(String title);
}
