package de.traveltogether.activity;

import de.traveltogether.model.Activity;
import de.traveltogether.model.Trip;
/**
 * Created by Anna-Lena on 28.07.2016.
 */
public interface IActivityPresenter {
    public void onGetFormerActivities(long tripId);
    public void onSuccess(Activity[] activities);
    public void onError(String message);
}
