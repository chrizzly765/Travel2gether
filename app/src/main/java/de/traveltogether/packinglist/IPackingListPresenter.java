package de.traveltogether.packinglist;

import de.traveltogether.model.PackingObject;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface IPackingListPresenter {
    public void onGetPackingObjects(long tripId);
    public void onSuccess(PackingObject[] packingobjects);
    public void onError(String message);
}
