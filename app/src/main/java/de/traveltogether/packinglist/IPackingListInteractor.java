package de.traveltogether.packinglist;

import de.traveltogether.IInteractor;

public interface IPackingListInteractor extends IInteractor{
     void getPackingObjects(long tripId, IPackingListPresenter listener);
}
