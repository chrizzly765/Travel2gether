package de.traveltogether.packinglist.packingdetail;

import de.traveltogether.model.PackingItem;
import de.traveltogether.model.PackingObject;

/**
 * Presenter for PackingDetailActivity
 * Implements IPackingDetailPresenter
 */
public class PackingDetailPresenter implements IPackingDetailPresenter{
    private PackingDetailActivity view;
    private IPackingDetailInteractor interactor;

    public PackingDetailPresenter(PackingDetailActivity _view){
        view = _view;
        interactor = new PackingDetailInteractor();
    }

    @Override
    public void onGetDetailsForPackingObject(long featureId) {
        interactor.getDetailsForPackingObject(featureId, this);
    }

    @Override
    public void onSuccessGetDetails(PackingObject packingObject) {
        view.onViewDetails(packingObject);
    }

    @Override
    public void onError(String message, String title) {
        view.onViewError(message, title);
    }

    @Override
    public void onDeletePackingObject(long id) {
        interactor.deletePackingObject(id, this);
    }

    @Override
    public void onSuccessDelete() {
        view.onSuccessDelete();
    }

    @Override
    public void onCloseActivity(){
        view.onCloseActivity();
    }

    public void onUpdatePackingItem(PackingItem item){
        interactor.updatePackingItem(item, this);
    }

    @Override
    public void onGetParticipantsForTrip(long tripId) {
        interactor.getParticipantsForTrip(tripId, this);
    }

    @Override
    public void onSuccessGetParticipants() {
        view.onSuccessGetParticipants();
    }

}
