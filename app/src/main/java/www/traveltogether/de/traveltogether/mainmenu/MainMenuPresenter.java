package www.traveltogether.de.traveltogether.mainmenu;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class MainMenuPresenter implements IMainMenuPresenter {
    MainActivity view;
    IMainMenuInteractor interactor;

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
}
