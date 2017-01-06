package de.traveltogether.packinglist.packingdetail;

import de.traveltogether.IInteractor;
import de.traveltogether.model.PackingItem;


interface IPackingDetailInteractor extends IInteractor {
     void getDetailsForPackingObject(long featureId, IPackingDetailPresenter _listener);
     void deletePackingObject(long id, IPackingDetailPresenter _listener);
     void updatePackingItem(PackingItem item, IPackingDetailPresenter _listener);
     void getParticipantsForTrip(long tripId, IPackingDetailPresenter _listener);

}
