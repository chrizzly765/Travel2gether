package de.traveltogether.mainmenu;

import de.traveltogether.model.Statistic;

/**
 * Presenter for MainActivity
 * Implements IMainMenuPresenter
 */
public class MainMenuPresenter implements IMainMenuPresenter {
    private MainActivity view;
    private IMainMenuInteractor interactor;

    public MainMenuPresenter(MainActivity _view){
        view=_view;
        interactor= new MainMenuInteractor();
    }

    @Override
    public void onDeleteTrip(Long tripId) {
        interactor.deleteTrip(this, tripId);
    }

    @Override
    public void onError(String message) {
        view.onViewError(message);
    }

    @Override
    public void onSuccessDeletingTrip() {
        view.onSuccessDeletingTrip();
    }

    @Override
    public void onLeaveTrip(long tripId, String userId) {
        interactor.leaveTrip(tripId,userId, this);
    }

    @Override
    public void onSuccessLeavingTrip() {
        view.onSuccessLeavingTrip();
    }

    @Override
    public void onGetParticipantsForTrip(long tripId) {
        interactor.getParticipantsForTrip(tripId, this);
    }

    @Override
    public void onSuccessGetParticipants() {
        view.onSuccessGetParticipants();
    }

    @Override
    public void onGetStatistics(long tripId, int userId) {
        interactor.getStatisticsForTrip(tripId, userId, this);
    }


    @Override
    public void onSuccessGetStatistics(Statistic statistic) {
        view.onSuccessGetStatistics(statistic);
    }

    @Override
    public void onGetTitleForTrip(long tripId) {
        interactor.getTitleForTrip(tripId, this);
    }

    @Override
    public void onSuccessGetTitle(String title){
        view.onSuccessGetTitle(title);
    }
}
