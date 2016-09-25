package de.traveltogether.info;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.invitation.InvitationActivity;
import de.traveltogether.model.Participant;
import de.traveltogether.model.Trip;
import de.traveltogether.triplist.TripListFragment;
import de.traveltogether.triplist.newtrip.NewTripActivity;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {
    IInfoPresenter presenter;
    long tripId;
    Trip trip;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        presenter = new InfoPresenter(this);
        tripId = this.getIntent().getLongExtra("tripId", -1);
        if(tripId==-1){
            onViewError("TripId fehlt!");
        }
        presenter.onGetInfoForTrip(tripId);
        presenter.onGetParticipantsForTrip(tripId);
        progressDialog = ProgressDialog.show(this, "",
                "Info wird geladen...", true);
        getSupportActionBar().setTitle("Info");

        findViewById(R.id.activity_info_button_add).setOnClickListener(this);
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
        finish();
    }

    public void onViewDetail(Trip _trip){
        trip = _trip;
        ((TextView)findViewById(R.id.activity_info_title)).setText(trip.getTitle());
        ((TextView)findViewById(R.id.activity_info_description)).setText(trip.getDestination());
        ((TextView)findViewById(R.id.activity_info_startdate)).setText(trip.getStartDate());
        ((TextView)findViewById(R.id.activity_info_enddate)).setText(trip.getEndDate());
        ((TextView)findViewById(R.id.activity_info_destination)).setText(trip.getDestination());
    }

    public void onViewParticipants(Participant[] participants){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        InfoListFragment fragment = InfoListFragment.newInstance(participants);
        fragmentTransaction.add(R.id.activity_info_fragment_container, fragment);
        fragmentTransaction.commit();
        progressDialog.cancel();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.activity_info_button_add){
            Intent intent = new Intent (this, InvitationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("tripId", tripId);
            bundle.putString("formerActivity", "info");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenu_detail, menu);
        if(trip!=null) {
            if (trip.getAdminId() == StaticData.getUserId()) {
                menu.getItem(R.id.delete).setEnabled(true);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                if (trip.getAdminId() == StaticData.getUserId()) {
                    //TODO: DELETE
                }
                break;
            case R.id.edit:
                Intent intent = new Intent(this, NewTripActivity.class);
                intent.putExtra("tripId", tripId);
                startActivity(intent);
                break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return  true;
    }
}
