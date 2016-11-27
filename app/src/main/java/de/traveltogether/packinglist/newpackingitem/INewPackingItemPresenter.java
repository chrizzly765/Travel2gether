package de.traveltogether.packinglist.newpackingitem;

import de.traveltogether.model.PackingObject;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface INewPackingItemPresenter {
    public void onUpdatePackingObject(PackingObject packingobject);
    public void onCreatePackingObject(PackingObject packingobject);
    public void onError(String message, String title);
    public void onSuccessGetDetail(PackingObject packingObject);
    public void onSuccessUpdatePackingObject();
    public void onSuccessAddingPackingObject();
    public void onGetDetailForPackingObject(long featureId);

}
