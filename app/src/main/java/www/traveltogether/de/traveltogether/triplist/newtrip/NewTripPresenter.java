package www.traveltogether.de.traveltogether.triplist.newtrip;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class NewTripPresenter implements INewTripPresenter {
    private NewTripActivity view;
    INewTripInteractor interactor;
    public NewTripPresenter(NewTripActivity _view){
        view = _view;
        interactor = new NewTripInteractor();
    }

    @Override
    public void onCreateTrip(String title, String description, String startdate, String enddate, String place) {
        interactor.createTrip(title,description,startdate,enddate,place,this);
    }

    @Override
    public void onError(String message) {
        view.onViewErrorMessage(message);
    }

    @Override
    public void onSuccess(String message) {
        view.onSuccess(message);
    }


}
