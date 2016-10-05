package de.traveltogether.activity.newactivity;

import android.widget.ImageView;

import java.text.SimpleDateFormat;

import de.traveltogether.IInteractor;
import de.traveltogether.model.Participant;

/**
 * Created by Isa on 13.08.2016.
 */
public interface INewActivityInteractor extends IInteractor {
    public void createActivity(String title, int id, long tripId, String description, int participant, int icon, String destination, String time, String date, INewActivityPresenter listener);
}
