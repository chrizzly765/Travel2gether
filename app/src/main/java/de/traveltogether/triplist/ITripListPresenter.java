package de.traveltogether.triplist;

import de.traveltogether.model.Trip;


 interface ITripListPresenter {
     void onGetTrips();
     void onSuccess(Trip[] trips);
     void onError(String message);

    void onGetNotiCount();

    void onSuccessNotiCount(int notiCount);
}
