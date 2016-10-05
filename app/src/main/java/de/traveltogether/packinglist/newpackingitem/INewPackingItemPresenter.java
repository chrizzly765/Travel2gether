package de.traveltogether.packinglist.newpackingitem;

import de.traveltogether.model.PackingObject;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface INewPackingItemPresenter {
    public void onUpdatePackingObject(PackingObject packingobject);
    public void onCreatePackingObject(long tripId, PackingObject packingobject);
}
