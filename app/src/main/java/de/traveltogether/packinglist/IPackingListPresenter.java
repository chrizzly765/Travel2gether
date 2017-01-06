package de.traveltogether.packinglist;

import de.traveltogether.model.PackingObject;


interface IPackingListPresenter {
     void onGetPackingObjects(long tripId);
     void onSuccess(PackingObject[] packingobjects);
     void onError(String message);
}
