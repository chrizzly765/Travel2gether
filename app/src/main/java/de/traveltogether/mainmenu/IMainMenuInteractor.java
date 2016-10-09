package de.traveltogether.mainmenu;

import de.traveltogether.IInteractor;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface IMainMenuInteractor extends IInteractor {
    public void deleteTrip(IMainMenuPresenter listener, Long tripId);
    public void leaveTrip(long tripId, String userId, IMainMenuPresenter listener);
    public void getParticipantsForTrip(long tripId, IMainMenuPresenter listener);
    public void getStatisticsForTrip (long tripId, int userId, IMainMenuPresenter listener);
    public void getTitleForTrip(long tripId, IMainMenuPresenter listener);

}