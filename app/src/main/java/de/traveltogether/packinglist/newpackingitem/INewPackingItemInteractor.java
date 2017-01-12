package de.traveltogether.packinglist.newpackingitem;

import de.traveltogether.IInteractor;
import de.traveltogether.model.PackingObject;

/**
 * Interface for Interactor of NewPackingItemActivity
 */
public interface INewPackingItemInteractor extends IInteractor {
    void createPackingObject(PackingObject packingObject, INewPackingItemPresenter listener);
    void updatePackingObject(PackingObject packingObject, INewPackingItemPresenter listener);
    void getDetailForPackingObject(long featureId, INewPackingItemPresenter listener);
}