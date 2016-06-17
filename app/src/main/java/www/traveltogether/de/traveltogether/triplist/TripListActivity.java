package www.traveltogether.de.traveltogether.triplist;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import java.util.ArrayList;

import www.traveltogether.de.traveltogether.R;
import www.traveltogether.de.traveltogether.mainmenu.MainActivity;
import www.traveltogether.de.traveltogether.model.Trip;
import www.traveltogether.de.traveltogether.settings.SettingsActivity;
import www.traveltogether.de.traveltogether.triplist.newtrip.NewTripActivity;

public class TripListActivity extends ListActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    ITripListPresenter presenter;
    private TripAdapter adapter;
    private Trip[] trips;
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
        if(trips.length==0 ||trips==null){
            //TODO: show new trip listitem
        }
        else {
            adapter = new TripAdapter(this, trips);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Trip trip = (Trip) adapter.getItem(position);
        Intent mainMenu = new Intent(this, MainActivity.class);
        Bundle b = new Bundle();
        b.putLong("tripId", trips[position].getTripId()); //Your id
        b.putString("title", trips[position].getTitle());
        b.putString("adminId", trips[position].getAdminId());
        mainMenu.putExtras(b); //Put your id to your next Intent
        startActivity(mainMenu);
    }
}
