package de.traveltogether.activity.detailactivity;

import de.traveltogether.IInteractor;


interface IDetailActivityInteractor extends IInteractor {
    public void getDetailsForActivity(long featureId, IDetailActivityPresenter _listener);
    public void deleteActivity(long id, IDetailActivityPresenter _listener);
    public void getParticipantsForTrip(long tripId, IDetailActivityPresenter _listener);

}


