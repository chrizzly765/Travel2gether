package de.traveltogether.notification;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.app.DialogFragment;
import android.util.Log;

import de.traveltogether.ActionType;
import de.traveltogether.R;
import de.traveltogether.model.Notification;

/**
 * A DialogFragment Subclass
 * Activities that contain this fragment must
 * implement the DatPickerDialog.OnDateSetListener
 * to handle interaction events.
 * Caution!!! datepicker counts month from ZERO!!!
 */
public class InvitationDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{
    public static final String TAG = InvitationDialogFragment.class.getSimpleName();
    private Notification notification;
    private NotificationActivity activity;

    public InvitationDialogFragment() {
        // Required empty public constructor
    }

    public static void createDialog( NotificationActivity _activity, Notification _notification ){
        InvitationDialogFragment dialog = new InvitationDialogFragment();
        dialog.activity = _activity;
        dialog.notification=_notification;

        AlertDialog.Builder builder = new AlertDialog.Builder(dialog.activity);
        builder.setMessage(dialog.notification.getMessage());
        builder.setTitle(R.string.invitation);
        builder.setNegativeButton(dialog.activity.getString(R.string.decline), dialog);
        builder.setPositiveButton(dialog.activity.getString(R.string.join), dialog);
        AlertDialog ad = builder.create();
        ad.show();
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.d("onclick", Integer.toString(which));
        if(which == -2)//id for BUTTON_NEGATIVE
        {
            Log.d("invitation", "decline");
            activity.presenter.onAnswerInvitation(notification.getFeatureOrTripId(), ActionType.DECLINE);
            activity.currentNotification = notification;
            activity.presenter.onSetNotificationRead(notification.getId());
            dialog.cancel();
        }
        if(which == -1)//id for BUTTON_POSITIVE
        {
            Log.d("invitation", "accept");
            activity.presenter.onAnswerInvitation(notification.getFeatureOrTripId(), ActionType.ACCEPT);
            activity.currentNotification = notification;
            activity.presenter.onSetNotificationRead(notification.getId());
            dialog.cancel();
        }
    }
}
