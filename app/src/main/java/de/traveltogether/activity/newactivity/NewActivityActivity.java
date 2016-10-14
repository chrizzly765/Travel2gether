package de.traveltogether.activity.newactivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import de.traveltogether.StaticData;
import de.traveltogether.activity.ActivitiesActivity;
import de.traveltogether.activity.detailactivity.DetailActivityActivity;
import de.traveltogether.model.Activity;
import de.traveltogether.model.Participant;
import de.traveltogether.R;
import de.traveltogether.datepicker.DatePickerFragment;
import de.traveltogether.invitation.InvitationActivity;
import de.traveltogether.timepicker.TimePickerFragment;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;

/**
 * Created by Isa on 13.08.2016.
 */
public class NewActivityActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    INewActivityPresenter presenter;
    DatePickerFragment datePicker;
    TimePickerFragment timePicker;
    ImageView iconBtn;
    EditText title;
    int id;
    long tripId = -1;
    EditText description;
    EditText destination;
    int iconTag;
    ImageView icon;
    EditText startDate;
    EditText time;
    int participant;
    Button cancel;
    Button save;
    Activity activity;
    long featureId = -1;
    Spinner currencySpinner;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_activity);

        getSupportActionBar().setTitle("Neue Aktivität");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo_ohne_schrift);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            tripId = b.getLong("tripId", -1);
            featureId = b.getLong("featureId", -1);
        }
        presenter = new NewActivityPresenter(this);

        datePicker =new DatePickerFragment();
        timePicker =new TimePickerFragment();

        title = (EditText) findViewById(R.id.newActivity_title);
        id = 0;
        description = (EditText)findViewById(R.id.newActivity_description);
        destination = (EditText) findViewById(R.id.newActivity_destination);
        startDate = (EditText) findViewById(R.id.newActivity_startDate);
        time = (EditText) findViewById(R.id.newActivity_time);

        if(featureId!=-1){
            getSupportActionBar().setTitle("Aktivität bearbeiten");
            presenter.onGetDetailForActivity(featureId);
            progressDialog = ProgressDialog.show(this, "",
                    "Bitte warten...", true);
        }
        else {
            getSupportActionBar().setTitle("Neue Aktivität");
        }

        ImageButton datePickerStartBtn = (ImageButton) findViewById(R.id.button_datepicker_start);
        datePickerStartBtn.setOnClickListener(this);
        EditText datePickerStartText = (EditText) findViewById(R.id.newActivity_startDate);
        datePickerStartText.setOnClickListener(this);

        ImageButton clickedTimePickerBtn = (ImageButton) findViewById(R.id.button_timepicker);
        clickedTimePickerBtn.setOnClickListener(this);
        EditText datePickerTimeText = (EditText) findViewById(R.id.newActivity_time);
        datePickerTimeText.setOnClickListener(this);

        ImageView iconBtnPlane = (ImageView) findViewById(R.id.icon_plane);
        iconBtnPlane.setOnClickListener(this);
        ImageView iconBtnBeach = (ImageView) findViewById(R.id.icon_beach);
        iconBtnBeach.setOnClickListener(this);
        ImageView iconBtnCocktail = (ImageView) findViewById(R.id.icon_cocktail);
        iconBtnCocktail.setOnClickListener(this);
        ImageView iconBtnBall = (ImageView) findViewById(R.id.icon_ball);
        iconBtnBall.setOnClickListener(this);
        ImageView iconBtnFilm = (ImageView) findViewById(R.id.icon_film);
        iconBtnFilm.setOnClickListener(this);
        ImageView iconBtnCoffee = (ImageView) findViewById(R.id.icon_coffee);
        iconBtnCoffee.setOnClickListener(this);
        ImageView iconBtnFood = (ImageView) findViewById(R.id.icon_food);
        iconBtnFood.setOnClickListener(this);
        ImageView iconBtnGift = (ImageView) findViewById(R.id.icon_gift);
        iconBtnGift.setOnClickListener(this);
        ImageView iconBtnSight = (ImageView) findViewById(R.id.icon_sight);
        iconBtnSight.setOnClickListener(this);
        ImageView iconBtnCasino = (ImageView) findViewById(R.id.icon_casino);
        iconBtnCasino.setOnClickListener(this);
        ImageView iconBtnSport = (ImageView) findViewById(R.id.icon_sport);
        iconBtnSport.setOnClickListener(this);
        ImageView iconBtnLandscape = (ImageView) findViewById(R.id.icon_landscape);
        iconBtnLandscape.setOnClickListener(this);
        ImageView iconBtnBus = (ImageView) findViewById(R.id.icon_bus);
        iconBtnBus.setOnClickListener(this);
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_save:

                if(title.getText().toString()==""){
                    onViewError("Bitte gib die Daten vollständig an");
                    return false;
                }
                if(featureId!=-1){
                    activity.setTitle(title.getText().toString());
                    activity.setDescription(description.getText().toString());
                    activity.setLastUpdateBy(StaticData.getUserId());

                    presenter.onUpdateActivity(activity);
                }
                else {

                    /*
                    int currency = currencySpinner.getSelectedItemPosition();
                    if(currencySpinner.getSelectedItem() == null){
                        currencySpinner.getSelectedItem().toString();
                    }
                    */
                    Activity activity = new Activity(
                            title.getText().toString(),
                            id,//Integer.parseInt(id.getText().toString()),
                            tripId,
                            description.getText().toString(),
                            StaticData.getUserId(),
                            iconTag,
                            destination.getText().toString(),
                            time.getText().toString(),
                            startDate.getText().toString() );

                    presenter.onCreateActivity(tripId, activity);
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onSuccessAddingActivity(){
        Context context = getApplicationContext();
        CharSequence text = "Aktivität hinzugefügt.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Intent intent = new Intent(this, ActivitiesActivity.class);
        intent.putExtra("tripId", tripId);
        startActivity(intent);
        finish();
    }

    public void onViewError(String message) {
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

    @Override
    public void onClick(View v) {
/*
        if(v.getId()==R.id.newActivity_button_cancel){
            finish();
        }
        else if (v.getId()==R.id.newActivity_button_save){

            presenter.onCreateActivity(
                    title.getText().toString(),
                    id,//Integer.parseInt(id.getText().toString()),
                    tripId,
                    description.getText().toString(),
                    participant,
                    iconTag,
                    destination.getText().toString(),
                    time.getText().toString(),
                    startDate.getText().toString());

        }
*/      if (v.getId() == R.id.button_datepicker_start || v.getId()==R.id.newActivity_startDate){
            datePicker.show(getFragmentManager(), DatePickerFragment.TAG);
        }

        else if (v.getId() == R.id.button_timepicker || v.getId() == R.id.newActivity_time){
            timePicker.show(getFragmentManager(), TimePickerFragment.ZEIT);
        }

        else if (v.getId() == R.id.icon_plane){
            iconTag = R.mipmap.plane;
        }
        else if (v.getId() == R.id.icon_beach){
            iconTag = R.mipmap.beach;
        }
        else if (v.getId() == R.id.icon_cocktail){
            iconTag = R.mipmap.cocktail;
        }
        else if (v.getId() == R.id.icon_ball){
            iconTag = R.mipmap.ball;
        }
        else if (v.getId() == R.id.icon_film){
            iconTag = R.mipmap.film;
        }
        else if (v.getId() == R.id.icon_coffee){
            iconTag = R.mipmap.coffee;
        }
        else if (v.getId() == R.id.icon_food){
            iconTag = R.mipmap.food;
        }
        else if (v.getId() == R.id.icon_gift){
            iconTag = R.mipmap.gift;
        }
        else if (v.getId() == R.id.icon_sight){
            iconTag = R.mipmap.sight;
        }
        else if (v.getId() == R.id.icon_casino){
            iconTag = R.mipmap.casino;
        }
        else if (v.getId() == R.id.icon_sport){
            iconTag = R.mipmap.sport;
        }
        else if (v.getId() == R.id.icon_landscape){
            iconTag = R.mipmap.landscape;
        }
        else if (v.getId() == R.id.icon_bus){
            iconTag = R.mipmap.bus;
        }

    }

    public void onViewErrorMessage(String message){
        progressDialog.cancel();
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

    public void onSuccessUpdateActivity(){
        Context context = getApplicationContext();
        CharSequence text = "Aktivität aktualisiert.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Intent intent = new Intent(this, DetailActivityActivity.class);
        Bundle b = new Bundle();
        b.putLong("featureId", featureId);
        b.putLong("tripId", tripId);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    public void setValues(Activity _activity){
        activity =_activity;
        tripId = activity.getTripId();
        title.setText(activity.getTitle());
        description.setText(activity.getDescription());
        startDate.setText(activity.getDate());
        time.setText(activity.getTime());

        progressDialog.cancel();
    }



/*
    public void onSuccess(String message){
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.newtrip_success);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Intent activity = new Intent(this, ActivitiesActivity.class);
        Bundle b = new Bundle();
        b.putLong("tripId", tripId); //Your id
        activity.putExtras(b); //Put your id to your next Intent
        startActivity(activity);
        finish();
    }
*/
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int month = monthOfYear + 1;
            startDate.setText(dayOfMonth + "." + month+"." + year);
            datePicker.setDate(year, monthOfYear, dayOfMonth+1);

        /*
        else if(clickedDatePickerBtn.getId() == R.id.button_datepicker_end){
            int month = monthOfYear + 1;
            endDate.setText(dayOfMonth + "." + month +"." + year);
        }
        */
    }
    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
            String stringHour = "";
            String stringMinute = "";
            if (hour < 10) {
                stringHour = "0" + Integer.toString(hour);
            }
            else {
                stringHour = Integer.toString(hour);
            }
            if (minute < 10) {
                 stringMinute = "0" + Integer.toString(minute);
            }
            else {
                stringMinute = Integer.toString(minute);
            }

            time.setText(stringHour + ":" + stringMinute);
            Log.d("TimeTest", "Test: "  + hour + " " +  minute);
            timePicker.setTime(hour, minute);
    }
    @Override
    public void onBackPressed() {
        if(featureId!=-1){
            Intent intent = new Intent(this, DetailActivityActivity.class);
            Bundle b = new Bundle();
            b.putLong("featureId", featureId);
            b.putLong("tripId", tripId);
            intent.putExtras(b);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, ActivitiesActivity.class);
            intent.putExtra("tripId", tripId);
            startActivity(intent);
        }
        finish();
    }




}
