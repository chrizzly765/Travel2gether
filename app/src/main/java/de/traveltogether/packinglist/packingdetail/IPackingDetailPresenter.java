package de.traveltogether.packinglist.packingdetail;

import de.traveltogether.model.PackingItem;
import de.traveltogether.model.PackingObject;


public interface IPackingDetailPresenter {
     void onGetDetailsForPackingObject(long featureId);
     void onSuccessGetDetails(PackingObject packingObject);
     void onError(String message, String title);
     void onDeletePackingObject(long id);
     void onSuccessDelete();
     void onUpdatePackingItem(PackingItem item);
    void onGetParticipantsForTrip(long tripId);
     void onSuccessGetParticipants();
    void onCloseActivity();
}
