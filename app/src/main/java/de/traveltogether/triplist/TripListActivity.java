package de.traveltogether.triplist;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.traveltogether.R;
import de.traveltogether.date.DateFormat;
import de.traveltogether.model.Trip;
import de.traveltogether.notification.NotificationActivity;
import de.traveltogether.settings.SettingsActivity;
import de.traveltogether.triplist.newtrip.NewTripActivity;

public class TripListActivity extends AppCompatActivity implements View.OnClickListener {
    ITripListPresenter presenter;
    private Trip[] trips;
    TripListFragment fragmentUpcoming;
    TripListFragment fragmentFormer;

    private Menu menu;
    ProgressDialog progressDialog;
    MenuItem notiItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onCreate", "create");

        setContentView(R.layout.activity_trip_list);
        getSupportActionBar().setTitle("Meine Reisen");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo_ohne_schrift);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //MenuItem optionsBtn = (MenuItem) findViewById(R.id.open_options);
        //optionsBtn.setOnClickListener(this);
        ImageButton newTripBtn = (ImageButton) findViewById(R.id.fab_button);
        newTripBtn.setOnClickListener(this);
    }

    @Override
    protected void onStart(){
        Log.e("onStart", "start");
        super.onStart();
        progressDialog = ProgressDialog.show(this, "",
                "Reisen werden geladen...", true);
        presenter = new TripListPresenter(this);
        presenter.onGetTrips();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(fragmentFormer!=null)
            fragmentTransaction.detach(fragmentFormer);
        if(fragmentUpcoming!=null)
            fragmentTransaction.detach(fragmentUpcoming);
        fragmentTransaction.commit();
        super.onSaveInstanceState(outState);
    }

    public void onSuccessGetNotiCount(int count){
        switch (count){
            case 0:  notiItem.setIcon(R.drawable.ic_noti_0);
                break;
            case 1: notiItem.setIcon(R.drawable.ic_noti_1);
                break;
            case 2: notiItem.setIcon(R.drawable.ic_noti_2);
                break;
            case 3: notiItem.setIcon(R.drawable.ic_noti_3);
                break;
            case 4: notiItem.setIcon(R.drawable.ic_noti_4);
                break;
            case 5: notiItem.setIcon(R.drawable.ic_noti_5);
                break;
            case 6: notiItem.setIcon(R.drawable.ic_noti_6);
                break;
            case 7: notiItem.setIcon(R.drawable.ic_noti_7);
                break;
            case 8: notiItem.setIcon(R.drawable.ic_noti_8);
                break;
            case 9: notiItem.setIcon(R.drawable.ic_noti_9);
                break;
            default: notiItem.setIcon(R.drawable.ic_noti_higher);
                break;
        }
        progressDialog.cancel();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_button){
            Intent set = new Intent(this, NewTripActivity.class);
            startActivity(set);
            //finish();
        }
    }

    public void onViewTrips(Trip[] _trips){
        Log.e("TripListActivity", "got trips: "+_trips.length);
        trips= _trips;
        List<Trip> formerTrips = new ArrayList<Trip>();
        List<Trip> upcomingTrips = new ArrayList<Trip>();
        Calendar cal = Calendar.getInstance();
        String currentDate = new SimpleDateFormat("dd.MM.yyyy").format(cal.getTime());

        for(Trip t : _trips){
            int comparison= DateFormat.getInstance().compareDates(t.getEndDate(), currentDate);
            if(comparison>=0){
                upcomingTrips.add(t);
            }
            else{
                formerTrips.add(t);
            }
        }

        //Fragment in Activity einbetten
        if(upcomingTrips.size()>0) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Trip[] arr = new Trip[upcomingTrips.size()];
            upcomingTrips.toArray(arr);
            TripListFragment fragment = TripListFragment.newInstance(arr);
            fragmentTransaction.add(R.id.fragment_trip_list_container_upcoming, fragment);
            fragmentTransaction.commit();
            fragmentUpcoming = fragment;
            findViewById(R.id.activity_trip_list_upcoming_empty).setVisibility(View.INVISIBLE);
        }
        else{
            findViewById(R.id.activity_trip_list_upcoming_empty).setVisibility(View.VISIBLE);
            RelativeLayout devider = (RelativeLayout)findViewById(R.id.activity_trip_list_devider_former);

            RelativeLayout.LayoutParams lp =(RelativeLayout.LayoutParams)devider.getLayoutParams();
            lp.addRule(RelativeLayout.BELOW, R.id.activity_trip_list_upcoming_empty);
            devider.setLayoutParams(lp);
        }
        if(formerTrips.size()>0) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Trip[] arr = new Trip[formerTrips.size()];
            formerTrips.toArray(arr);
            TripListFragment fragment = TripListFragment.newInstance(arr);
            fragmentTransaction.add(R.id.fragment_trip_list_container_former, fragment);
            //fragmentTransaction.attach(fragment);
            fragmentTransaction.commit();
            fragmentFormer = fragment;
            findViewById(R.id.activity_trip_list_former_empty).setVisibility(View.INVISIBLE);

        }
        else{
            findViewById(R.id.activity_trip_list_former_empty).setVisibility(View.VISIBLE);
        }
        progressDialog.cancel();
        presenter.onGetNotiCount();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        notiItem = menu.getItem(0);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_notification:
                Intent noti = new Intent(this, NotificationActivity.class);
                startActivity(noti);
                //finish();
                return true;
            case R.id.action_settings:
                Intent options = new Intent(this, SettingsActivity.class);
                startActivity(options);
                //finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onViewError(String message) {
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
}
