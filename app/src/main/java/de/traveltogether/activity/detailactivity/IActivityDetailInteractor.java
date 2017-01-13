package de.traveltogether.activity.detailactivity;

import de.traveltogether.IInteractor;


interface IActivityDetailInteractor extends IInteractor {
    void getDetailsForActivity(long featureId, IActivityDetailPresenter _listener);
    void deleteActivity(long id, IActivityDetailPresenter _listener);
    void getParticipantsForTrip(long tripId, IActivityDetailPresenter _listener);

}


