package de.traveltogether.activity.newactivity;

import android.widget.ImageView;

import java.text.SimpleDateFormat;

import de.traveltogether.model.Participant;

/**
 * Created by Isa on 13.08.2016.
 */
public interface INewActivityPresenter {
    public void onCreateActivity(String title, int id, long tripId, String description, int participant, int icon, String destination, String time, String date);
    public void onError(String message);
    public void onSuccess(String message);
}
