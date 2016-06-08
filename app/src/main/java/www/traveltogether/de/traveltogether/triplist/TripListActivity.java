package www.traveltogether.de.traveltogether.triplist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import www.traveltogether.de.traveltogether.R;
import www.traveltogether.de.traveltogether.model.Trip;
import www.traveltogether.de.traveltogether.settings.SettingsActivity;
import www.traveltogether.de.traveltogether.triplist.newtrip.NewTripActivity;

public class TripListActivity extends AppCompatActivity implements View.OnClickListener {
    ITripListPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        Button optionsBtn = (Button)findViewById(R.id.options_button);
        optionsBtn.setOnClickListener(this);
        Button newTripBtn = (Button)findViewById(R.id.new_trip_button);
        newTripBtn.setOnClickListener(this);
        presenter = new TripListPresenter(this);
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

    public void onViewTrips(Trip[] trips){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anzahl Trips: " + trips.length);
        builder.setTitle("Error");
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
