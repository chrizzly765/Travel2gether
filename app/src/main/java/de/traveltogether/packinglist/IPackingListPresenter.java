package de.traveltogether.packinglist;

import de.traveltogether.model.PackingObject;

/**
 * Interface for presenter of PackingListActivity
 */
interface IPackingListPresenter {
     void onGetPackingObjects(long tripId);
     void onSuccess(PackingObject[] packingobjects);
     void onError(String message);
}
