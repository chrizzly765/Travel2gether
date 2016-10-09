package de.traveltogether.activity;

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
import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.activity.newactivity.NewActivityActivity;
import de.traveltogether.mainmenu.MainActivity;
import de.traveltogether.model.Trip;
import de.traveltogether.model.Activity;
import de.traveltogether.settings.SettingsActivity;
import de.traveltogether.triplist.newtrip.NewTripActivity;

public class ActivitiesActivity extends AppCompatActivity implements View.OnClickListener{
    IActivityPresenter presenter;
    Activity[] formerActivities;
    ActivityFragment fragment;
    public long tripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Aktivitäten und Orte");
        Bundle b = getIntent().getExtras();
        tripId = -1; // or other values
        if (b != null) {
            tripId = b.getLong("tripId", -1);
        }
        if(tripId!=-1){
            StaticTripData.setCurrentTripId(tripId);
        }

        setContentView(R.layout.activity_activities);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo_ohne_schrift);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        ImageButton newTripBtn = (ImageButton) findViewById(R.id.fab_button);
        newTripBtn.setOnClickListener(this);

        presenter = new ActivityPresenter(this);
        presenter.onGetFormerActivities(tripId);
/*
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ActivityFragment fragment = ActivityFragment.newInstance(formerActivities, presenter, tripId);
        fragmentTransaction.add(R.id.fragment_activity_list_container, fragment);
        fragmentTransaction.commit();
*/
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_button){
            Intent set = new Intent(this, NewActivityActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("tripId", tripId);
            set.putExtras(bundle);
            startActivity(set);
            finish();
        }
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                //TODO: DELETE
                break;
            case R.id.edit:
                Intent intent = new Intent(this, NewActivityActivity.class);
                intent.putExtra("tripId", tripId);
                startActivity(intent);
                break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return  true;
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

    public void onViewFormerActivities(Activity[] activities){

        formerActivities = activities;
        /*
        if(formerActivities== null){
            formerActivities= new Activity[0];
        }
        */
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ActivityFragment fragment = ActivityFragment.newInstance(activities, presenter, tripId);
        fragmentTransaction.add(R.id.fragment_activity_list_container, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("tripId", tripId);
        startActivity(intent);
        finish();
    }
}
