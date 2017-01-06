package de.traveltogether.activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import de.traveltogether.R;
import de.traveltogether.StaticTripData;
import de.traveltogether.activity.newactivity.NewActivityActivity;
import de.traveltogether.model.Activity;

public class ActivitiesActivity extends AppCompatActivity implements View.OnClickListener{
    private IActivityPresenter presenter;
    private Activity[] formerActivities;
    private ActivityFragment fragment;
    private long tripId;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        tripId = -1; // or other values
        if (b != null) {
            tripId = b.getLong("tripId", -1);
        }
        if(tripId!=-1){
            StaticTripData.setCurrentTripId(tripId);
        }

        setContentView(R.layout.activity_activities);

        getSupportActionBar().setTitle("Aktivitäten");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.mipmap.logo_ohne_schrift);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ImageButton newTripBtn = (ImageButton) findViewById(R.id.fab_button);
        newTripBtn.setOnClickListener(this);


        presenter = new ActivityPresenter(this);


/*
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ActivityFragment fragment = ActivityFragment.newInstance(formerActivities, presenter, tripId);
        fragmentTransaction.add(R.id.fragment_activity_list_container, fragment);
        fragmentTransaction.commit();
*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(fragment!=null)
            fragmentTransaction.detach(fragment);
        fragmentTransaction.commit();
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart(){
        super.onStart();
        progressDialog = ProgressDialog.show(this, "",
                "Aktivitäten werden geladen...", true);
        presenter.onGetFormerActivities(tripId);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_button){
            Intent set = new Intent(this, NewActivityActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("tripId", tripId);
            set.putExtras(bundle);
            startActivity(set);
        }
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                super.onOptionsItemSelected(item);
        }
        return  true;
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

    public void onViewFormerActivities(Activity[] activities){

        formerActivities = activities;
        /*
        if(formerActivities== null){
            formerActivities= new Activity[0];
        }
        */
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = ActivityFragment.newInstance(activities, presenter, tripId);
        fragmentTransaction.add(R.id.fragment_activity_list_container, fragment);
        fragmentTransaction.commit();
        progressDialog.cancel();

    }

}
