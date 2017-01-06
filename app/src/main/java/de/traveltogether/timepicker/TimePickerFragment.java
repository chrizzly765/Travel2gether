package de.traveltogether.timepicker;

import android.app.Activity;
import android.os.Bundle;

import android.app.TimePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {
    public static final String ZEIT = TimePickerFragment.class.getSimpleName();
    private TimePickerDialog.OnTimeSetListener listener;
    private Calendar date = Calendar.getInstance();

    public TimePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof TimePickerDialog.OnTimeSetListener) {
            listener = (TimePickerDialog.OnTimeSetListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement TimePickerDialog.OnTimeSetListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), listener, hour, minute, true);
    }

    public void setTime (int hour, int minute){
       // date.set(hour, minute);
    }
}
