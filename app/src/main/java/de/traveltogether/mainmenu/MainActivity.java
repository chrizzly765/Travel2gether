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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;

import org.apache.commons.lang3.StringEscapeUtils;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.activity.ActivityActivity;
import de.traveltogether.chat.ChatActivity;
import de.traveltogether.expense.ExpenseActivity;
import de.traveltogether.info.InfoActivity;
import de.traveltogether.model.Statistic;
import de.traveltogether.model.Trip;
import de.traveltogether.packinglist.PackingListActivity;
import de.traveltogether.tasks.TaskListActivity;

import 	java.util.Calendar;
import java.util.Date;

/**
 * Activity showing the countdown, progress indicator and all features
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private long tripId;
    private String title;
    private int adminId;
    private IMainMenuPresenter presenter;
    private ProgressDialog progressDialog;
    private AnimationDrawable groupStatisticAnimation;
    private AnimationDrawable personalStatisticAnimation;
    private Statistic statistic;
    private ImageView groupAnimationContainer;
    private ImageView personalAnimationContainer;
    private static int SECONDS_IN_A_DAY = 24 * 60 * 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainMenuPresenter(this);
        setContentView(R.layout.activity_main_menu);

        Bundle b = getIntent().getExtras();
        tripId = -1;
        if (b != null) {
            tripId = b.getLong("tripId",-1);
            title = b.getString("title", "");
            adminId = b.getInt("adminId");
                setActionBar(title);
        }
        if(title.equals("")){
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

        LinearLayout progressInfo = (LinearLayout)findViewById(R.id.main_menu_progress_info);
        progressInfo.setOnClickListener(this);
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

        progressDialog = ProgressDialog.show(this, "",
                "Bitte warten...", true);
    }

    @Override
    protected void onStart(){
        super.onStart();
        presenter.onGetStatistics(tripId,StaticData.getUserId());
    }

    private  void setActionBar(String heading) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(StringEscapeUtils.unescapeJava(heading));
        actionBar.show();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void onSuccessGetParticipants(){}

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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

        TextView groupText = (TextView) findViewById(R.id.main_menu_group_text);
        groupText.setText(stringGroup);
        TextView personalText = (TextView) findViewById(R.id.main_menu_personal_text);
        personalText.setText(stringPersonal);

        TextView startDateDays = (TextView) findViewById(R.id.main_menu_countdown_days);
        startDateDays.setText(getCountdownDays(getCountdown(statistic.getStartDate())));
        TextView startDateHours = (TextView) findViewById(R.id.main_menu_countdown_hours);
        startDateHours.setText(getCountdownHours(getCountdown(statistic.getStartDate())));
        TextView startDateMinutes = (TextView) findViewById(R.id.main_menu_countdown_minutes);
        startDateMinutes.setText(getCountdownMinutes(getCountdown(statistic.getStartDate())));

        long timeoutGroup = Math.round(statistic.getGroup() * 2100) ;
        long timeoutPersonal = Math.round(statistic.getPersonal() * 2100);

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

    private  String getCountdown (String startDate) {
        String minutesString;
        String hoursString;
        String daysString;

        String[] s = startDate.split("\\.", -1);
        int _day = Integer.parseInt(s[0]);
        int _month = Integer.parseInt(s[1]);
        int _year = Integer.parseInt(s[2]);

        String countdown = "";
        countdown = "00:00:00";

        if (_year != 0) {

            Calendar thatDay = Calendar.getInstance();
            thatDay.setTime(new Date(0));
            thatDay.set(Calendar.DAY_OF_MONTH, _day);
            thatDay.set(Calendar.MONTH, _month - 1); // 0-11 so 1 less
            thatDay.set(Calendar.YEAR, _year);

            Calendar today = Calendar.getInstance();
            long diff = thatDay.getTimeInMillis() - today.getTimeInMillis();
            long diffSec = diff / 1000;

            long days = diffSec / SECONDS_IN_A_DAY;
            long secondsDay = diffSec % SECONDS_IN_A_DAY;
            long seconds = secondsDay % 60;
            long minutes = (secondsDay / 60) % 60;
            long hours = (secondsDay / 3600); // % 24 not needed

            if (minutes < 0) {
                if (Math.abs(minutes) >= 0 && Math.abs(minutes) <= 9) {
                    minutesString = "0" + String.valueOf(Math.abs(minutes));
                } else {
                    minutesString = String.valueOf(Math.abs(minutes));
                }
            } else if (minutes >= 0 && minutes <= 9) {
                minutesString = "0" + String.valueOf(minutes);
            } else {
                minutesString = String.valueOf(minutes);
            }

            if (hours - 1 < 0) {
                if (Math.abs(hours - 1) >= 0 && Math.abs(hours - 1) <= 9) {
                    hoursString = "0" + String.valueOf(Math.abs(hours - 1));
                } else {
                    hoursString = String.valueOf(Math.abs(hours - 1));
                }
            } else if (hours - 1 >= 0 && hours - 1 <= 9) {
                hoursString = "0" + String.valueOf(hours - 1);
            } else {
                hoursString = String.valueOf(hours - 1);
            }

            if (days < 0) {
                if (Math.abs(days) >= 0 && Math.abs(days) <= 9) {
                    daysString = "-   " + "0" + String.valueOf(Math.abs(days));
                } else {
                    daysString = "-   " + String.valueOf(Math.abs(days));
                }

            } else if (days >= 0 && days <= 9) {
                daysString = "0" + String.valueOf(days);
            } else {
                daysString = String.valueOf(days);
            }

            if (days < 0) {
                TextView daysText = (TextView) findViewById(R.id.main_menu_text_days);
                daysText.setTextColor(0xFFFF0000);
                TextView hourseText = (TextView) findViewById(R.id.main_menu_text_hours);
                hourseText.setTextColor(0xFFFF0000);
                TextView minutesText = (TextView) findViewById(R.id.main_menu_text_minutes);
                minutesText.setTextColor(0xFFFF0000);
                TextView daysCountdown = (TextView) findViewById(R.id.main_menu_countdown_days);
                daysCountdown.setTextColor(0xFFFF0000);
                TextView hourseCountdown = (TextView) findViewById(R.id.main_menu_countdown_hours);
                hourseCountdown.setTextColor(0xFFFF0000);
                TextView minutesCountdown = (TextView) findViewById(R.id.main_menu_countdown_minutes);
                minutesCountdown.setTextColor(0xFFFF0000);
            }
            countdown = daysString + ":" + hoursString + ":" + minutesString;
        }
        return countdown;
    }

    private  String getCountdownDays (String countdown) {
        String[] parts = countdown.split(":");
        String days = parts[0];
        return days;
    }

    private  String getCountdownHours (String countdown) {
        String[] parts = countdown.split(":");
        String hours = parts[1];
        return ":   " + hours + "   :";
    }

    private  String getCountdownMinutes (String countdown) {
        String[] parts = countdown.split(":");
        String minutes = parts[2];
        return minutes;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.main_menu_info){
            Intent intent = new Intent(this, InfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("tripId", tripId);
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
        }
        else if(v.getId()==R.id.main_menu_expences){
            Intent intent = new Intent(this, ExpenseActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("tripId", tripId);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if(v.getId()==R.id.main_menu_activities){
            Intent intent = new Intent(this, ActivityActivity.class);
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
        else if(v.getId()==R.id.main_menu_progress_info){
            Intent intent = new Intent(this, TaskListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("tripId", tripId);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        progressDialog.cancel();
        if(resultCode==RESULT_OK) {
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onSuccessGetTitle(String _title){
        title = _title;
        getSupportActionBar().setTitle(StringEscapeUtils.unescapeJava(title));
        progressDialog.cancel();
    }
}
