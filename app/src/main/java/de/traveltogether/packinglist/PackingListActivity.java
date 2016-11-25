package de.traveltogether.packinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.traveltogether.R;
import de.traveltogether.StaticTripData;
import de.traveltogether.mainmenu.MainActivity;
import de.traveltogether.model.PackingObject;

import android.app.ProgressDialog;
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
import de.traveltogether.packinglist.newpackingitem.NewPackingItemActivity;

import de.traveltogether.R;
import de.traveltogether.notification.NotificationActivity;
import de.traveltogether.settings.SettingsActivity;



public class PackingListActivity extends AppCompatActivity implements View.OnClickListener{

    IPackingListPresenter presenter;
    private PackingObject[] packingobjects;
    PackingListFragment fragment;
    private Menu menu;
    ProgressDialog progressDialog;
    long tripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packing_list);
        tripId = getIntent().getLongExtra("tripId", -1);
        if(tripId!=-1){
            StaticTripData.setCurrentTripId(tripId);
        }
        getSupportActionBar().setTitle("Packliste");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo_ohne_schrift);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        ImageButton button = (ImageButton)findViewById(R.id.activity_packing_list_button_add);
        button.setOnClickListener(this);
        //MenuItem optionsBtn = (MenuItem) findViewById(R.id.open_options);
        //optionsBtn.setOnClickListener(this);
        //ImageButton newTripBtn = (ImageButton) findViewById(R.id.fab_button);
        //newTripBtn.setOnClickListener(this);
        presenter = new PackingListPresenter(this);

        presenter.onGetPackingObjects(tripId);

        progressDialog = ProgressDialog.show(this, "",
                "Packliste wird geladen...", true);
    }

    public void onViewPackingObjects(PackingObject[] _packingobjects){
        Log.d("PackingListActivity", "got packingobjects: "+_packingobjects.length);
        packingobjects = _packingobjects;
        if(packingobjects.length == 0){
            findViewById(R.id.activity_packing_list_empty_text).setVisibility(View.VISIBLE);
        }
        else {

            //Fragment in Activity einbetten
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            PackingListFragment fragment = PackingListFragment.newInstance(_packingobjects);
            fragmentTransaction.add(R.id.fragment_packing_list_container, fragment);
            fragmentTransaction.commit();
        }
        progressDialog.cancel();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_packing_list_button_add){
            Intent set = new Intent(this, NewPackingItemActivity.class);
            set.putExtra("tripId", tripId);
            startActivity(set);
            finish();
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("tripId", tripId);
        startActivity(intent);
        finish();
    }

}
