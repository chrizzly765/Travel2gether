package de.traveltogether.triplist.newtrip;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.Objects;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.datepicker.DatePickerFragment;
import de.traveltogether.invitation.InvitationActivity;
import de.traveltogether.model.Trip;

/**
 * Activity for creating a new trip
 */
public class NewTripActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private INewTripPresenter presenter;
    private DatePickerFragment datePicker;
    private ImageButton clickedDatePickerBtn;
    private EditText title;
    private EditText description;
    private EditText startDate;
    private EditText endDate;
    private EditText place;
    private long tripId;
    private Trip trip;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tripId = getIntent().getLongExtra("tripId", -1);

        if(tripId != -1) {
            getSupportActionBar().setTitle(getString(R.string.edit_trip));
        }
        else {
            getSupportActionBar().setTitle(getString(R.string.new_trip));
        }
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        presenter = new NewTripPresenter(this);
        datePicker =new DatePickerFragment();
        setContentView(R.layout.activity_new_trip);

        title = (EditText) findViewById(R.id.newTrip_title);
        description = (EditText)findViewById(R.id.newTrip_description);
        startDate = (EditText) findViewById(R.id.newTrip_startDate);
        endDate = (EditText) findViewById(R.id.newTrip_endDate);
        place = (EditText) findViewById(R.id.newTrip_place);

        ImageButton datePickerStartBtn = (ImageButton) findViewById(R.id.button_datepicker_start);
        datePickerStartBtn.setOnClickListener(this);
        EditText datePickerStartText = (EditText) findViewById(R.id.newTrip_startDate);
        datePickerStartText.setOnClickListener(this);

        ImageButton datePickerEndBtn = (ImageButton) findViewById(R.id.button_datepicker_end);
        datePickerEndBtn.setOnClickListener(this);
        EditText datePickerEndText = (EditText) findViewById(R.id.newTrip_endDate);
        datePickerEndText.setOnClickListener(this);

    }

    @Override
    protected void onStart(){
        super.onStart();
        if(tripId != -1){

            progressDialog = ProgressDialog.show(this, "",
                    getString(R.string.trip_load), true);
            presenter.onGetDetailsForTrip(tripId);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_datepicker_start  || v.getId()==R.id.newTrip_startDate){
            clickedDatePickerBtn = (ImageButton) findViewById(R.id.button_datepicker_start);
            datePicker.show(getFragmentManager(), DatePickerFragment.TAG);
        }
        else if (v.getId() == R.id.button_datepicker_end || v.getId()==R.id.newTrip_endDate){
            clickedDatePickerBtn = (ImageButton) findViewById(R.id.button_datepicker_end);
            datePicker.show(getFragmentManager(), DatePickerFragment.TAG);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if(!StringEscapeUtils.escapeJava(title.getText().toString()).equals("")){
                    progressDialog = ProgressDialog.show(this, "",
                            getString(R.string.please_wait), true);
                    if(StringEscapeUtils.escapeJava(description.getText().toString()).equals("")){
                        description.setText(getString(R.string.no_description));
                    }
                    if(StringEscapeUtils.escapeJava(place.getText().toString()).equals("")){
                        place.setText(getString(R.string.no_destination));
                    }

                    if(tripId!=-1){
                        presenter.onUpdateTrip(
                                new Trip(
                                        trip.getTripId(),
                                        StringEscapeUtils.escapeJava(title.getText().toString()),
                                        StringEscapeUtils.escapeJava(description.getText().toString()),
                                        StringEscapeUtils.escapeJava(place.getText().toString()),
                                        startDate.getText().toString(),
                                        endDate.getText().toString(),
                                        trip.getAuthorId(),
                                        trip.getAdminId(),
                                        StaticData.getUserId(),
                                        trip.getLastUpdate()));
                    }
                    else {
                        presenter.onCreateTrip(
                            new Trip(
                                    StringEscapeUtils.escapeJava(title.getText().toString()),
                                    StringEscapeUtils.escapeJava(description.getText().toString()),
                                    StringEscapeUtils.escapeJava(place.getText().toString()),
                                    startDate.getText().toString(),
                                    endDate.getText().toString()
                            ));
                    }
                }
                else{
                    onViewErrorMessage(getString(R.string.missing_title, getString(R.string.trip)), getString(R.string.mandatory));
                }
                break;
            case android.R.id.home:
                finish();
                return true;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return  true;
    }

    public void onViewErrorMessage(String message, String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onSuccess(String message, long tripId){
        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.newtrip_success), Toast.LENGTH_SHORT);
        toast.show();

        Intent invite = new Intent(this, InvitationActivity.class);
        Bundle b = new Bundle();
        b.putLong("tripId", tripId);
        invite.putExtras(b);
        startActivity(invite);
        progressDialog.cancel();
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

    public void onFillFields(Trip _trip){
        trip = _trip;

        title.setText(StringEscapeUtils.unescapeJava(trip.getTitle()));
        description.setText(StringEscapeUtils.unescapeJava(trip.getDescription()));
        place.setText(StringEscapeUtils.unescapeJava(trip.getDestination()));
        startDate.setText(trip.getStartDate());
        endDate.setText(trip.getEndDate());
        progressDialog.cancel();
    }

    public void onSuccessUpdateTrip(String message){
        progressDialog.cancel();
        finish();
    }
}
