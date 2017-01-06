package de.traveltogether.packinglist;

import de.traveltogether.model.PackingObject;
import android.util.Log;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class PackingListPresenter implements IPackingListPresenter {

    private PackingListActivity view;
    private IPackingListInteractor interactor;

    public PackingListPresenter(PackingListActivity _view){
        view = _view;
        interactor = new PackingListInteractor();
    }

    @Override
    public void onGetPackingObjects(long tripId) {
        interactor.getPackingObjects(tripId, this);
    }

    @Override
    public void onSuccess(PackingObject[] packingobjects) {

        Log.d("Success in preseneter","");
        view.onViewPackingObjects(packingobjects);
    }

    @Override
    public void onError(String message) {
        view.onViewError(message);
    }

}
