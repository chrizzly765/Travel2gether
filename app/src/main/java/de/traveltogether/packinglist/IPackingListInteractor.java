package de.traveltogether.packinglist;

import de.traveltogether.IInteractor;
/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface IPackingListInteractor extends IInteractor{
    public void getPackingObjects(long tripId, IPackingListPresenter listener);
}
