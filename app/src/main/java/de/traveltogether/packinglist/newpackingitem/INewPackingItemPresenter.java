package de.traveltogether.packinglist.newpackingitem;

import de.traveltogether.model.PackingObject;

/**
 * Interface for presenter of NewPackingActivity
 */
public interface INewPackingItemPresenter {
    void onUpdatePackingObject(PackingObject packingobject);
    void onCreatePackingObject(PackingObject packingobject);
    void onError(String message, String title);
    void onSuccessGetDetail(PackingObject packingObject);
    void onSuccessUpdatePackingObject();
    void onSuccessAddingPackingObject();
    void onGetDetailForPackingObject(long featureId);
}
