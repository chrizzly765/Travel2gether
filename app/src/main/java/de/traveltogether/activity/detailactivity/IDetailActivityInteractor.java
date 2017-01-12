package de.traveltogether.activity.detailactivity;

import de.traveltogether.IInteractor;


interface IDetailActivityInteractor extends IInteractor {
    void getDetailsForActivity(long featureId, IDetailActivityPresenter _listener);
    void deleteActivity(long id, IDetailActivityPresenter _listener);
    void getParticipantsForTrip(long tripId, IDetailActivityPresenter _listener);

}


