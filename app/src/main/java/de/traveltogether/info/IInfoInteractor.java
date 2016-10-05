package de.traveltogether.info;

import de.traveltogether.IInteractor;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface IInfoInteractor extends IInteractor{
    public void getInfoForTripId(long tripId, IInfoPresenter listener);
    public void getParticipantsForTrip(long tripId, IInfoPresenter listener);
}
