package www.traveltogether.de.traveltogether.mainmenu;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface IMainMenuPresenter {
    public void onDeleteTrip(Long tripId);

    void onError(String message);

    void onSuccessDeletingTrip();
}
