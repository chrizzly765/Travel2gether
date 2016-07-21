package de.traveltogether.triplist;

import de.traveltogether.IInteractor;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface ITripListInteractor extends IInteractor {
    public void getTrips(ITripListPresenter listener);
}
