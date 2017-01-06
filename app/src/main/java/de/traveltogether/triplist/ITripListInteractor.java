package de.traveltogether.triplist;

import de.traveltogether.IInteractor;

 interface ITripListInteractor extends IInteractor {
     void getTrips(ITripListPresenter listener);

    void getNotiCount(ITripListPresenter _listener);
}
