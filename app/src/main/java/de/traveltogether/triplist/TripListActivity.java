package de.traveltogether.triplist;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

import de.traveltogether.R;
import de.traveltogether.model.Trip;
import de.traveltogether.settings.SettingsActivity;
import de.traveltogether.triplist.newtrip.NewTripActivity;

public class TripListActivity extends AppCompatActivity implements View.OnClickListener {
    ITripListPresenter presenter;
    private Trip[] trips;
    TripListFragment fragment;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        getSupportActionBar().setTitle("Meine Reisen");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo_ohne_schrift);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //MenuItem optionsBtn = (MenuItem) findViewById(R.id.open_options);
        //optionsBtn.setOnClickListener(this);
        ImageButton newTripBtn = (ImageButton) findViewById(R.id.fab_button);
        newTripBtn.setOnClickListener(this);
        presenter = new TripListPresenter(this);

        presenter.onGetTrips();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onPause(){
        super.onPause();
        /*FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();*/

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_button){
            Intent set = new Intent(this, NewTripActivity.class);
            startActivity(set);
        }
    }

    public void onViewTrips(Trip[] _trips){
        Log.d("TripListActivity", "got trips: "+_trips.length);
        trips= _trips;

        //Fragment in Activity einbetten
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TripListFragment fragment = TripListFragment.newInstance(_trips);
        fragmentTransaction.add(R.id.fragment_trip_list_container, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_notification:
                return true;
            case R.id.action_settings:
                Intent options = new Intent(this, SettingsActivity.class);
                startActivity(options);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenu_triplist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.open_options:
                Intent options = new Intent(this, SettingsActivity.class);
                startActivity(options);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
*/
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
}
