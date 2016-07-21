package de.traveltogether.triplist.newtrip;

import de.traveltogether.IInteractor;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface INewTripInteractor extends IInteractor{
    public void createTrip(String title, String description, String startdate, String enddate, String place, INewTripPresenter listener);
}
