package de.traveltogether.activity.newactivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.Objects;

import de.traveltogether.StaticData;
import de.traveltogether.model.Activity;
import de.traveltogether.R;
import de.traveltogether.datepicker.DatePickerFragment;
import de.traveltogether.time.TimeFormat;
import de.traveltogether.timepicker.TimePickerFragment;

/**
 * Created by Isa on 13.08.2016.
 */
public class NewActivityActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private INewActivityPresenter presenter;
    private DatePickerFragment datePicker;
    private TimePickerFragment timePicker;
    private ImageView iconBtn;
    private EditText title;
    private int id;
    private long tripId = -1;
    private EditText description;
    private EditText destination;
    private int iconTag;
    private ImageView icon;
    private EditText startDate;
    private EditText time;
    private int participant;
    private Button cancel;
    private Button save;
    private Activity activity;
    private long featureId = -1;
    private Spinner currencySpinner;
    private ProgressDialog progressDialog;
    private ImageButton currentImgBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_activity);

        getSupportActionBar().setTitle("Neue Aktivität");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.mipmap.logo_ohne_schrift);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

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


        ImageButton iconBtnEmpty = (ImageButton) findViewById(R.id.icon_empty);
        iconBtnEmpty.setOnClickListener(this);
        ImageButton iconBtnPlane = (ImageButton) findViewById(R.id.icon_plane);
        iconBtnPlane.setOnClickListener(this);
        ImageButton iconBtnBeach = (ImageButton) findViewById(R.id.icon_beach);
        iconBtnBeach.setOnClickListener(this);
        ImageButton iconBtnCocktail = (ImageButton) findViewById(R.id.icon_cocktail);
        iconBtnCocktail.setOnClickListener(this);
        ImageButton iconBtnBall = (ImageButton) findViewById(R.id.icon_ball);
        iconBtnBall.setOnClickListener(this);
        ImageButton iconBtnFilm = (ImageButton) findViewById(R.id.icon_film);
        iconBtnFilm.setOnClickListener(this);
        ImageButton iconBtnCoffee = (ImageButton) findViewById(R.id.icon_coffee);
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

    @Override
    protected void onStart(){
        super.onStart();

        if(featureId!=-1) {
            presenter.onGetDetailForActivity(featureId);
            progressDialog = ProgressDialog.show(this, "",
                    "Bitte warten...", true);
        }
        currentImgBtn = (ImageButton) findViewById(R.id.icon_empty);
        iconTag = R.mipmap.ic_leer;
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_save:
                if(!StringEscapeUtils.escapeJava(title.getText().toString()).equals("")){
                    progressDialog = ProgressDialog.show(this, "",
                            "Bitte warten...", true);
                    // DEFAULT TEXT IF FIELDS ARE EMPTY
                    if(StringEscapeUtils.escapeJava(description.getText().toString()).equals("")){
                        description.setText("Keine Beschreibung");
                    }
                    if(StringEscapeUtils.escapeJava(destination.getText().toString()).equals("")){
                        destination.setText("Kein Ort");
                    }
                    if(featureId!=-1){
                        activity.setTitle(StringEscapeUtils.escapeJava(title.getText().toString()));
                        activity.setDescription(StringEscapeUtils.escapeJava(description.getText().toString()));
                        activity.setDestination(StringEscapeUtils.escapeJava(destination.getText().toString()));
                        activity.setTime(time.getText().toString());
                        activity.setDate(startDate.getText().toString());
                        activity.setIcon(iconTag);
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
                                StringEscapeUtils.escapeJava(title.getText().toString()),
                                id,//Integer.parseInt(id.getText().toString()),
                                tripId,
                                StringEscapeUtils.escapeJava(description.getText().toString()),
                                StaticData.getUserId(),
                                iconTag,
                                StringEscapeUtils.escapeJava(destination.getText().toString()),
                                time.getText().toString(),
                                startDate.getText().toString() );

                        presenter.onCreateActivity(tripId, activity);
                    }
                    return true;
                }
                else {
                    onViewError("Bitte gib einen Titel für deine Aktivität ein.", "Pflichtfeld");
                    return false;
                }

            case android.R.id.home:
                finish();
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
        progressDialog.cancel();
        finish();
    }

    public void onViewError(String message, String title) {
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


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button_datepicker_start || v.getId()==R.id.newActivity_startDate){
            datePicker.show(getFragmentManager(), DatePickerFragment.TAG);
        }

        else if (v.getId() == R.id.button_timepicker || v.getId() == R.id.newActivity_time){
            timePicker.show(getFragmentManager(), TimePickerFragment.ZEIT);
        }
        else {
            final ImageButton imgBtn = (ImageButton) v;

            if (currentImgBtn != null) {
                currentImgBtn.setSelected(false);
            }

            if (v.getId() == R.id.icon_empty){
                iconTag = R.mipmap.ic_leer;
            }
            else if (v.getId() == R.id.icon_plane){
                v.setSelected(!v.isSelected());
                iconTag = R.mipmap.plane;
            }
            else if (v.getId() == R.id.icon_beach){
                v.setSelected(!v.isSelected());
                iconTag = R.mipmap.beach;
            }
            else if (v.getId() == R.id.icon_cocktail){
                v.setSelected(!v.isSelected());
                iconTag = R.mipmap.cocktail;
            }
            else if (v.getId() == R.id.icon_ball){
                v.setSelected(!v.isSelected());
                iconTag = R.mipmap.ball;
            }
            else if (v.getId() == R.id.icon_film){
                v.setSelected(!v.isSelected());
                iconTag = R.mipmap.film;
            }
            else if (v.getId() == R.id.icon_coffee){
                v.setSelected(!v.isSelected());
                iconTag = R.mipmap.coffee;
            }
            else if (v.getId() == R.id.icon_food){
                v.setSelected(!v.isSelected());
                iconTag = R.mipmap.food;
            }
            else if (v.getId() == R.id.icon_gift){
                v.setSelected(!v.isSelected());
                iconTag = R.mipmap.gift;
            }
            else if (v.getId() == R.id.icon_sight){
                v.setSelected(!v.isSelected());
                iconTag = R.mipmap.sight;
            }
            else if (v.getId() == R.id.icon_casino){
                v.setSelected(!v.isSelected());
                iconTag = R.mipmap.casino;
            }
            else if (v.getId() == R.id.icon_sport){
                v.setSelected(!v.isSelected());
                iconTag = R.mipmap.sport;
            }
            else if (v.getId() == R.id.icon_landscape){
                v.setSelected(!v.isSelected());
                iconTag = R.mipmap.landscape;
            }
            else if (v.getId() == R.id.icon_bus){
                v.setSelected(!v.isSelected());
                iconTag = R.mipmap.bus;
            }

            imgBtn.setSelected(true);
            currentImgBtn = imgBtn;
        }

    }

    public void onViewErrorMessage(String message, String title){
        progressDialog.cancel();
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

    public void onSuccessUpdateActivity(){
        Context context = getApplicationContext();
        CharSequence text = "Aktivität aktualisiert.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        progressDialog.cancel();
        finish();
    }

    public void setValues(Activity _activity){
        activity =_activity;
        tripId = activity.getTripId();
        title.setText(StringEscapeUtils.unescapeJava(activity.getTitle()));
        description.setText(StringEscapeUtils.unescapeJava(activity.getDescription()));
        startDate.setText(activity.getDate());
        time.setText(TimeFormat.getInstance().getTimeWithoutSecondsWithoutWord(activity.getTime()));
        try {
            currentImgBtn = (ImageButton) findViewById(activity.getIcon());
            currentImgBtn.setSelected(true);
        }
        catch(Exception e){
            //do nothing
        }
        iconTag = activity.getIcon();
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
        } else {
            stringHour = Integer.toString(hour);
        }
        if (minute < 10) {
            stringMinute = "0" + Integer.toString(minute);
        } else {
            stringMinute = Integer.toString(minute);
        }

        time.setText(stringHour + ":" + stringMinute);
        Log.d("TimeTest", "Test: " + hour + " " + minute);
        timePicker.setTime(hour, minute);
    }
}
