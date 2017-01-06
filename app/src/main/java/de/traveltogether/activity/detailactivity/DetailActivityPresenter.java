package de.traveltogether.activity.detailactivity;

import de.traveltogether.model.Activity;

public class DetailActivityPresenter implements IDetailActivityPresenter {
    private DetailActivityActivity view;
    private IDetailActivityInteractor interactor;

    public DetailActivityPresenter(DetailActivityActivity _view){
        view= _view;
        interactor= new DetailActivityInteractor();
    }

    @Override
    public void onGetDetailsForActivity(long featureId) {
        interactor.getDetailsForActivity(featureId, this);
    }

    @Override
    public void onSuccessGetDetails(Activity activity) {
        view.onViewDetails(activity);
    }

    @Override
    public void onError(String message, String title) {
        view.onViewError(message, title);
    }


    @Override
    public void onDeleteActivity(long id) { interactor.deleteActivity(id, this);}

    @Override
    public void onSuccessDelete() {
        view.onSuccessDelete();
    }

    public void onCloseActivity(){
        view.onCloseActivity();
    }

    @Override
    public void onGetParticipantsForTrip(long tripId) {
        interactor.getParticipantsForTrip(tripId, this);
    }

}
