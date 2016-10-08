package de.traveltogether.activity.detailactivity;

import de.traveltogether.model.Activity;

/**
 * Created by Isa on 05.10.2016.
 */
public interface IDetailActivityPresenter {
    public void onGetDetailsForActivity(long featureId);
    public void onSuccessGetDetails(Activity activity);
    public void onError(String message);
    public void onDeleteActivity(long id);
    public void onSuccessDelete();
}
