package de.traveltogether.info;

import de.traveltogether.IInteractor;

interface IInfoInteractor extends IInteractor{
    void getInfoForTripId(long tripId, IInfoPresenter listener);
    void getParticipantsForTrip(long tripId, IInfoPresenter listener);
    void deleteTrip(long tripId, IInfoPresenter listener);
    void leaveTrip(long tripId, IInfoPresenter listener);
}
