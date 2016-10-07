package de.traveltogether.packinglist.packingdetail;

import de.traveltogether.IInteractor;

/**
 * Created by Maria Dreher on 07.10.2016.
 */
public interface IPackingDetailInteractor extends IInteractor {
    public void getDetailsForPackingObject(long featureId, IPackingDetailPresenter _listener);
    public void deletePackingObject(long id, IPackingDetailPresenter _listener);
}
