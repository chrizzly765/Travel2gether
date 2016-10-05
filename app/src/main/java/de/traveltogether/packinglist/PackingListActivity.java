package de.traveltogether.packinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.traveltogether.R;
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
    PackingFragment fragment;
    private Menu menu;
    ProgressDialog progressDialog;
    long tripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packing_list);
        tripId = getIntent().getLongExtra("tripId", -1);
        getSupportActionBar().setTitle("Packliste");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo_ohne_schrift);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
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

        //Fragment in Activity einbetten
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PackingFragment fragment = PackingFragment.newInstance(_packingobjects);
        fragmentTransaction.add(R.id.fragment_packing_list_container, fragment);
        fragmentTransaction.commit();
        progressDialog.cancel();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_button){
            Intent set = new Intent(this, NewPackingItemActivity.class);
            startActivity(set);
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
