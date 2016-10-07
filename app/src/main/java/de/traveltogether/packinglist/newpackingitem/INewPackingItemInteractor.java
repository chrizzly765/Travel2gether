package de.traveltogether.packinglist.newpackingitem;

import de.traveltogether.IInteractor;
import de.traveltogether.model.PackingObject;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface INewPackingItemInteractor extends IInteractor {
    public void createPackingObject(PackingObject packingObject, INewPackingItemPresenter listener);
    public void updatePackingObject(PackingObject packingObject, INewPackingItemPresenter listener);
    public void getDetailForPackingObject(long featureId, INewPackingItemPresenter listener);
}