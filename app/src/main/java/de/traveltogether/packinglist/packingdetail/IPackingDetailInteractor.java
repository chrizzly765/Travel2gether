package de.traveltogether.packinglist.packingdetail;

import de.traveltogether.IInteractor;
import de.traveltogether.model.PackingItem;

/**
 * Created by Maria Dreher on 07.10.2016.
 */
public interface IPackingDetailInteractor extends IInteractor {
    public void getDetailsForPackingObject(long featureId, IPackingDetailPresenter _listener);
    public void deletePackingObject(long id, IPackingDetailPresenter _listener);
    public void updatePackingItem(PackingItem item, IPackingDetailPresenter _listener);
    public void getParticipantsForTrip(long tripId, IPackingDetailPresenter _listener);

}
