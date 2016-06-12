package www.traveltogether.de.traveltogether.triplist.newtrip;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import www.traveltogether.de.traveltogether.R;
import www.traveltogether.de.traveltogether.datepicker.DatePickerFragment;
import www.traveltogether.de.traveltogether.invitation.InvitationActivity;

public class NewTripActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    INewTripPresenter presenter;
    DatePickerFragment datePicker;
    ImageButton clickedDatePickerBtn;
    EditText title;
    EditText description;
    EditText startDate;
    EditText endDate;
    EditText place;
    Button cancel;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NewTripPresenter(this);
        datePicker =new DatePickerFragment();
        setContentView(R.layout.activity_new_trip);
        title = (EditText) findViewById(R.id.newTrip_title);
        description = (EditText)findViewById(R.id.newTrip_description);
        startDate = (EditText) findViewById(R.id.newTrip_startDate);
        endDate = (EditText) findViewById(R.id.newTrip_endDate);
        place = (EditText) findViewById(R.id.newTrip_place);


        cancel = (Button)findViewById(R.id.newTrip_button_cancel);
        cancel.setOnClickListener(this);
        save = (Button)findViewById(R.id.newTrip_button_save);
        save.setOnClickListener(this);

        ImageButton datePickerStartBtn = (ImageButton) findViewById(R.id.button_datepicker_start);
        datePickerStartBtn.setOnClickListener(this);

        ImageButton datePickerEndBtn = (ImageButton) findViewById(R.id.button_datepicker_end);
        datePickerEndBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.newTrip_button_cancel){
            finish();
        }
        else if (v.getId()==R.id.newTrip_button_save){
            presenter.onCreateTrip(
                    title.getText().toString(),
                    description.getText().toString(),
                    startDate.getText().toString(),
                    endDate.getText().toString(),
                    place.getText().toString());
        }
        else if (v.getId() == R.id.button_datepicker_start){
            clickedDatePickerBtn = (ImageButton) v;
            datePicker.show(getFragmentManager(), DatePickerFragment.TAG);
        }
        else if (v.getId() == R.id.button_datepicker_end){
            clickedDatePickerBtn = (ImageButton) v;
            datePicker.show(getFragmentManager(), DatePickerFragment.TAG);
        }
    }

    public void onViewErrorMessage(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(getString(R.string.error));
        builder.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onSuccess(String message){
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.newtrip_success);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Intent invite = new Intent(this, InvitationActivity.class);
        startActivity(invite);
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if(clickedDatePickerBtn.getId() == R.id.button_datepicker_start){
            int month = monthOfYear + 1;
            startDate.setText(dayOfMonth + "." + month+"." + year);
            datePicker.setDate(year, monthOfYear, dayOfMonth+1);
        }
        else if(clickedDatePickerBtn.getId() == R.id.button_datepicker_end){
            int month = monthOfYear + 1;
            endDate.setText(dayOfMonth + "." + month +"." + year);
        }
    }
}
