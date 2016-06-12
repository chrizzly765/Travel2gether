package www.traveltogether.de.traveltogether.mainmenu;

import www.traveltogether.de.traveltogether.IInteractor;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface IMainMenuInteractor extends IInteractor {
    public void deleteTrip(IMainMenuPresenter listener, Long tripId);
}
