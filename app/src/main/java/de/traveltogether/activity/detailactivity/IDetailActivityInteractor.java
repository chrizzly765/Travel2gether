package de.traveltogether.activity.detailactivity;

import de.traveltogether.IInteractor;

/**
 * Created by Isa on 05.10.2016.
 */
public interface IDetailActivityInteractor extends IInteractor {
    public void getDetailsForActivity(long featureId, IDetailActivityPresenter _listener);
    public void deleteActivity(long id, IDetailActivityPresenter _listener);
}


