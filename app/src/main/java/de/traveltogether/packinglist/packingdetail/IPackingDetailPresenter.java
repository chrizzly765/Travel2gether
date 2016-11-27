package de.traveltogether.packinglist.packingdetail;

import de.traveltogether.model.PackingItem;
import de.traveltogether.model.PackingObject;

/**
 * Created by Maria Dreher on 07.10.2016.
 */
public interface IPackingDetailPresenter {
    public void onGetDetailsForPackingObject(long featureId);
    public void onSuccessGetDetails(PackingObject packingObject);
    public void onError(String message, String title);
    public void onDeletePackingObject(long id);
    public void onSuccessDelete();
    public void onUpdatePackingItem(PackingItem item);

    void onCloseActivity();
}
