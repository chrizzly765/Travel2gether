package de.traveltogether.packinglist.newpackingitem;

import de.traveltogether.model.PackingObject;

/**
 * Presenter for NewPackingActivity
 * Implements INewPackingItemPresenter
 */
public class NewPackingItemPresenter implements INewPackingItemPresenter {
    private NewPackingItemActivity view;
    private INewPackingItemInteractor interactor;

    public NewPackingItemPresenter(NewPackingItemActivity _view){
        view = _view;
        interactor = new NewPackingItemInteractor();
    }
    @Override
    public void onUpdatePackingObject(PackingObject packingobject) {
        interactor.updatePackingObject(packingobject, this);
    }

    @Override
    public void onCreatePackingObject(PackingObject packingobject) {
        interactor.createPackingObject(packingobject, this);
    }

    @Override
    public void onError(String message, String title) {
        view.onViewError(message, title);
    }

    @Override
    public void onSuccessGetDetail(PackingObject packingObject) {
        view.onSuccessGetDetail(packingObject);
    }

    @Override
    public void onSuccessUpdatePackingObject() {
        view.onSuccessUpdatePackingObject();
    }

    @Override
    public void onSuccessAddingPackingObject() {
        view.onSuccessAddingPackingObject();
    }

    @Override
    public void onGetDetailForPackingObject(long featureId) {
        interactor.getDetailForPackingObject(featureId, this);
    }
}
