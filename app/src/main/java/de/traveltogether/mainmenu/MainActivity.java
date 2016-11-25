package de.traveltogether.mainmenu;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.activity.ActivitiesActivity;
import de.traveltogether.chat.ChatActivity;
import de.traveltogether.date.DateFormat;
import de.traveltogether.expense.ExpenseActivity;
import de.traveltogether.info.InfoActivity;
import de.traveltogether.model.Statistic;
import de.traveltogether.model.Trip;
import de.traveltogether.packinglist.PackingListActivity;
import de.traveltogether.tasks.TaskListActivity;
import de.traveltogether.triplist.TripListActivity;
import 	java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    long tripId;
    String title;
    //TextView groupText;
    TextView personalText;
    TextView startDateText;
    int adminId;
    IMainMenuPresenter presenter;
    ProgressDialog progressDialog;
    AnimationDrawable groupStatisticAnimation;
    AnimationDrawable personalStatisticAnimation;
    Statistic statistic;
    Trip trip;
    ImageView groupAnimationContainer;
    ImageView personalAnimationContainer;
    static int SECONDS_IN_A_DAY = 24 * 60 * 60;
    //TextView startDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainMenuPresenter(this);
        setContentView(R.layout.activity_main_menu);
        Bundle b = getIntent().getExtras();
        tripId = -1; // or other values
        if (b != null) {
            tripId = b.getLong("tripId",-1);
            title = b.getString("title", "");
            adminId = b.getInt("adminId");
                setActionBar(title);
        }
        if(title==""){
            presenter.onGetTitleForTrip(tripId);
        }

        StaticTripData.setCurrentTripId(tripId);
        groupAnimationContainer = (ImageView) findViewById(R.id.main_menu_group_animation);
        groupAnimationContainer.setBackgroundResource(R.drawable.group_animation);
        groupStatisticAnimation = (AnimationDrawable) groupAnimationContainer.getBackground();

        personalAnimationContainer = (ImageView) findViewById(R.id.main_menu_personal_animation);
        personalAnimationContainer.setBackgroundResource(R.drawable.personal_animation);
        personalStatisticAnimation = (AnimationDrawable) personalAnimationContainer.getBackground();


        presenter.onGetParticipantsForTrip(tripId);

        //groupText = (TextView) findViewById(R.id.main_menu_group_text);
        //startDateText = (TextView) findViewById(R.id.main_menu_countdown);

        ImageButton info = (ImageButton)findViewById(R.id.main_menu_info);
        info.setOnClickListener(this);
        ImageButton expences = (ImageButton)findViewById(R.id.main_menu_expences);
        expences.setOnClickListener(this);
        ImageButton tasks = (ImageButton)findViewById(R.id.main_menu_tasks);
        tasks.setOnClickListener(this);
        ImageButton packing = (ImageButton)findViewById(R.id.main_menu_packing);
        packing.setOnClickListener(this);
        ImageButton activities = (ImageButton)findViewById(R.id.main_menu_activities);
        activities.setOnClickListener(this);
        ImageButton chat = (ImageButton)findViewById(R.id.main_menu_chat);
        chat.setOnClickListener(this);
    }

    @Override
    protected void onStart(){
        super.onStart();

        progressDialog = ProgressDialog.show(this, "",
                "Bitte warten...", true);

        presenter.onGetStatistics(tripId,StaticData.getUserId());
    }

    public void setActionBar(String heading) {
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(heading);
        actionBar.show();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo_ohne_schrift);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }

    public void onSuccessGetParticipants(){
        //progressDialog.cancel();
        //groupStatisticAnimation.start();
        //personalStatisticAnimation.start();
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenu_trip, menu);

        if(StaticData.getUserId().equals(adminId)){
            menu.getItem(0).setEnabled(true);
            menu.getItem(1).setEnabled(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.delete_trip:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getString(R.string.trip_delete_warning));
                builder.setTitle(getString(R.string.trip_delete));
                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        presenter.onDeleteTrip(tripId);
                        Intent tripList = new Intent(getApplicationContext(), TripListActivity.class);
                        startActivity(tripList);
                        finish();

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            case R.id.change_admin:
                return true;
            case R.id.leave_trip:
                AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
                adBuilder.setMessage(getString(R.string.leave_trip_warning));
                adBuilder.setTitle(getString(R.string.leave_trip));
                adBuilder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                adBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        presenter.onLeaveTrip(tripId,StaticData.getUserId());
                        Intent tripList = new Intent(getApplicationContext(), TripListActivity.class);
                        startActivity(tripList);
                        finish();

                    }
                });

                AlertDialog alertDialog = adBuilder.create();
                alertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }
*/
    public void onViewError(String message){
        progressDialog.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(getString(R.string.error));
        builder.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
    }

    public void onSuccessDeletingTrip(){
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.delete_trip_success);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void onSuccessLeavingTrip() {
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.leave_trip_success);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void onSuccessGetStatistics(Statistic _statistic){
        statistic = _statistic;

        String stringGroup = Long.toString(Math.round(statistic.getGroup() * 100)) + "%";
        String stringPersonal = Long.toString(Math.round(statistic.getPersonal() * 100)) + "%";

/*

        TextView groupText = (TextView) findViewById(R.id.main_menu_group_text);
        groupText.setText(stringGroup);
        TextView personalText = (TextView) findViewById(R.id.main_menu_personal_text);
        personalText.setText(stringPersonal);
        */
        TextView startDateText = (TextView) findViewById(R.id.main_menu_countdown);
        startDateText.setText(getCountdown(statistic.getStartDate()));
        //startDateText.setText(DateFormat.getInstance().getDateAsCountdown(statistic.getStartDate()));

        Log.d("startDateLog","statsitcLog1: " + statistic.getStartDate());
        Log.d("startDateLog","statsitcLog2: " + statistic.getGroup());
        Log.d("startDateLog","statsitcLog3: " + statistic.getPersonal());

        long timeoutGroup = Math.round(statistic.getGroup() * 2100) ;
        long timeoutPersonal = Math.round(statistic.getPersonal() * 2100);

        Log.d("startDateLog","statsitcLog4: " + timeoutGroup);

        groupStatisticAnimation.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                groupStatisticAnimation.stop();
            }
        }, timeoutGroup);

        personalStatisticAnimation.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                personalStatisticAnimation.stop();
            }
        }, timeoutPersonal);
        progressDialog.cancel();
    }

    public String getCountdown (String startDate) {
        String minutesString;
        String hoursString;
        String daysString;

        String[] s = startDate.split("\\.", -1);
        int _day = Integer.parseInt(s[0]);
        int _month = Integer.parseInt(s[1]);
        int _year = Integer.parseInt(s[2]);

        String countdown = "";

        Calendar thatDay = Calendar.getInstance();
        thatDay.setTime(new Date(0));
        thatDay.set(Calendar.DAY_OF_MONTH,_day);
        thatDay.set(Calendar.MONTH, _month-1); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, _year);

        Log.d("CountdownTest", "Countdown: " + thatDay);

        Calendar today = Calendar.getInstance();
        long diff =  thatDay.getTimeInMillis() - today.getTimeInMillis();
        long diffSec = diff / 1000;

        long days = diffSec / SECONDS_IN_A_DAY;
        long secondsDay = diffSec % SECONDS_IN_A_DAY;
        long seconds = secondsDay % 60;
        long minutes = (secondsDay / 60) % 60;
        long hours = (secondsDay / 3600); // % 24 not needed

        if (minutes >= 0 && minutes <= 9 ){
            minutesString = "0" + String.valueOf(minutes);
        }
        else {
            minutesString = String.valueOf(minutes);
        }
        Log.d("hoursTest", "hours: " + hours);
        if (hours-1 >= 0 && hours-1 <= 9 ){
            hoursString = "0" + String.valueOf(hours-1);
        }
        else {
            hoursString = String.valueOf(hours-1);
        }

        if (days >= 0 && days <= 9 ){
            daysString = "0" + String.valueOf(days);
        }
        else {
            daysString = String.valueOf(days);
        }

        if (days < 0){
            TextView countdownText = (TextView) findViewById(R.id.main_menu_countdown);
            countdownText.setTextColor(0xFFFF0000);
        }

        //System.out.printf("%d days, %d hours, %d minutes and %d seconds\n", days, hours, minutes, seconds);
        countdown = "    " + daysString + "    :    " + hoursString + "    :    " + minutesString + "    ";
        return countdown;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.main_menu_info){
            Intent intent = new Intent(this, InfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("tripId", tripId);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if(v.getId()==R.id.main_menu_expences){
            Intent intent = new Intent(this, ExpenseActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("tripId", tripId);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if(v.getId()==R.id.main_menu_activities){
            Intent intent = new Intent(this, ActivitiesActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("tripId", tripId);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if(v.getId()==R.id.main_menu_chat){
            Intent intent = new Intent(this, ChatActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("tripId", tripId);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if(v.getId()==R.id.main_menu_tasks){
            Intent intent = new Intent(this, TaskListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("tripId", tripId);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if(v.getId()==R.id.main_menu_packing){
            Intent intent = new Intent(this, PackingListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("tripId", tripId);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void onSuccessGetTitle(String _title){
        title = _title;
        getSupportActionBar().setTitle(title);
        progressDialog.cancel();
    }
}
