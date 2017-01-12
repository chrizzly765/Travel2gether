package de.traveltogether.packinglist;

import de.traveltogether.StaticTripData;
import de.traveltogether.model.PackingObject;

import android.app.ProgressDialog;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import de.traveltogether.packinglist.newpackingitem.NewPackingItemActivity;

import de.traveltogether.R;

/**
 * Activity for list of viewing packing items
 */
public class PackingListActivity extends AppCompatActivity implements View.OnClickListener{

    private IPackingListPresenter presenter;
    private PackingObject[] packingobjects;
    private PackingListFragment fragment;
    private Menu menu;
    private ProgressDialog progressDialog;
    private long tripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packing_list);
        tripId = getIntent().getLongExtra("tripId", -1);
        if(tripId!=-1){
            StaticTripData.setCurrentTripId(tripId);
        }
        getSupportActionBar().setTitle(getString(R.string.packinglist));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ImageButton button = (ImageButton)findViewById(R.id.activity_packing_list_button_add);
        button.setOnClickListener(this);
        presenter = new PackingListPresenter(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        progressDialog = ProgressDialog.show(this, "",
                getString(R.string.packinglist)+ getString(R.string.is_loading), true);

        presenter.onGetPackingObjects(tripId);

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

    public void onViewPackingObjects(PackingObject[] _packingobjects){
        packingobjects = _packingobjects;
        if(packingobjects.length == 0){
            findViewById(R.id.activity_packing_list_empty_text).setVisibility(View.VISIBLE);
        }
        else {
            //Fragment in Activity einbetten
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragment = PackingListFragment.newInstance(_packingobjects);
            fragmentTransaction.add(R.id.fragment_packing_list_container, fragment);
            fragmentTransaction.commit();
            findViewById(R.id.activity_packing_list_empty_text).setVisibility(View.INVISIBLE);
        }
        progressDialog.cancel();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_packing_list_button_add){
            Intent set = new Intent(this, NewPackingItemActivity.class);
            set.putExtra("tripId", tripId);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return  true;
    }
}
