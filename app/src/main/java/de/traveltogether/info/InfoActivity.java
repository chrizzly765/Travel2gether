package de.traveltogether.info;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
        ((TextView)findViewById(R.id.activity_info_description)).setText(trip.getDescription());
        ((TextView)findViewById(R.id.activity_info_startdate)).setText(trip.getStartDate());
        ((TextView)findViewById(R.id.activity_info_enddate)).setText(trip.getEndDate());
        ((TextView)findViewById(R.id.activity_info_destination)).setText(trip.getDestination());
    }

    public void onViewParticipants(Participant[] participants){

        Participant[] activeParts = StaticData.getActiveParticipants();
        Participant[] invitedParts = StaticData.getInvitedParticipants();
        Participant[] resignedParts = StaticData.getResignedParticipants();

        if(activeParts.length>0) {
            findViewById(R.id.activity_info_devider_active).setVisibility(View.VISIBLE);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            InfoListFragment fragment = InfoListFragment.newInstance(activeParts);
            fragmentTransaction.add(R.id.activity_info_fragment_container_active, fragment);
            fragmentTransaction.commit();
            progressDialog.cancel();
        }
        if(invitedParts.length>0) {
            findViewById(R.id.activity_info_devider_invited).setVisibility(View.VISIBLE);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            InfoListFragment fragment = InfoListFragment.newInstance(invitedParts);
            fragmentTransaction.add(R.id.activity_info_fragment_container_invited, fragment);
            fragmentTransaction.commit();
            progressDialog.cancel();
        }
        if(resignedParts.length>0) {
            findViewById(R.id.activity_info_devider_resigned).setVisibility(View.VISIBLE);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            InfoListFragment fragment = InfoListFragment.newInstance(resignedParts);
            fragmentTransaction.add(R.id.activity_info_fragment_container_resigned, fragment);
            fragmentTransaction.commit();
            progressDialog.cancel();
        }
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
                    presenter.onDeleteTrip(tripId);
                }
                else{
                    onViewError("Nur der Ersteller dieser Ausgabe darf die Ausgabe löschen.");

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

    public void onSuccessDeleteTrip(){
        Context context = getApplicationContext();
        CharSequence text = "Reise gelöscht.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        finish();
    }
}
