package de.traveltogether.activity;

import de.traveltogether.IInteractor;

interface IActivityInteractor extends IInteractor {
    public void getFormerActivities(long tripId, IActivityPresenter listener);
}
