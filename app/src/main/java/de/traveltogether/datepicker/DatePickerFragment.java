package de.traveltogether.datepicker;

import android.app.Activity;
import android.os.Bundle;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import java.util.Calendar;

/**
 * A DialogFragment Subclass
 * Activities that contain this fragment must
 * implement the DatPickerDialog.OnDateSetListener
 * to handle interaction events.
 * Caution!!! datepicker counts month from ZERO!!!
 */
public class DatePickerFragment extends DialogFragment {
    public static final String TAG = DatePickerFragment.class.getSimpleName();
    private DatePickerDialog.OnDateSetListener listener;
    private Calendar date = Calendar.getInstance();

    public DatePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DatePickerDialog.OnDateSetListener) {
            listener = (DatePickerDialog.OnDateSetListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement DatePickerDialog.OnDateSetListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        //current Date is default
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        //create DatePicker and return it
        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }

    public void setDate(int year, int month, int day){
        date.set(year,month,day);
    }
}
