package de.traveltogether.mainmenu;

import de.traveltogether.IInteractor;

/**
 * Interface for interactor of MainActivity
 */
 interface IMainMenuInteractor extends IInteractor {
     void deleteTrip(IMainMenuPresenter listener, Long tripId);
     void leaveTrip(long tripId, String userId, IMainMenuPresenter listener);
     void getParticipantsForTrip(long tripId, IMainMenuPresenter listener);
     void getStatisticsForTrip (long tripId, int userId, IMainMenuPresenter listener);
     void getTitleForTrip(long tripId, IMainMenuPresenter listener);

}