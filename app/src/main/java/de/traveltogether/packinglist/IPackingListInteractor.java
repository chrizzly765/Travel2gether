package de.traveltogether.packinglist;

import de.traveltogether.IInteractor;

/**
 * Interface for Interactor of PackingListActivity
 */
public interface IPackingListInteractor extends IInteractor{
     void getPackingObjects(long tripId, IPackingListPresenter listener);
}
