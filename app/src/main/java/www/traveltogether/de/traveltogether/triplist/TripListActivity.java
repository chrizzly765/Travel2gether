package www.traveltogether.de.traveltogether.triplist;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import java.util.ArrayList;

import www.traveltogether.de.traveltogether.R;
import www.traveltogether.de.traveltogether.StaticData;
import www.traveltogether.de.traveltogether.invitation.InvitationFragment;
import www.traveltogether.de.traveltogether.mainmenu.MainActivity;
import www.traveltogether.de.traveltogether.model.Trip;
import www.traveltogether.de.traveltogether.settings.SettingsActivity;
import www.traveltogether.de.traveltogether.triplist.newtrip.NewTripActivity;

public class TripListActivity extends AppCompatActivity implements View.OnClickListener {
    ITripListPresenter presenter;
    private Trip[] trips;
    TripListFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        Button optionsBtn = (Button)findViewById(R.id.options_button);
        optionsBtn.setOnClickListener(this);
        Button newTripBtn = (Button)findViewById(R.id.new_trip_button);
        newTripBtn.setOnClickListener(this);
        presenter = new TripListPresenter(this);

    }

    @Override
    protected void onStart(){
        super.onStart();
        presenter.onGetTrips();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.options_button){
            Intent set = new Intent(this, SettingsActivity.class);
            startActivity(set);
        }
        else if (v.getId() == R.id.new_trip_button){
            Intent set = new Intent(this, NewTripActivity.class);
            startActivity(set);
        }
    }

    public void onViewTrips(Trip[] _trips){
        Log.d("TripListActivity", "got trips: "+_trips.length);
        trips= _trips;

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TripListFragment fragment = TripListFragment.newInstance(_trips);
        fragmentTransaction.add(R.id.fragment_trip_list_container, fragment);
        fragmentTransaction.commit();
    }

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
