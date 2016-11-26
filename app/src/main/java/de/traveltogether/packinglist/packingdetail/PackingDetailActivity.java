package de.traveltogether.packinglist.packingdetail;

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
import android.widget.TextView;

import java.util.List;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.model.PackingItem;
import de.traveltogether.model.PackingObject;
import de.traveltogether.packinglist.PackingListActivity;
import de.traveltogether.packinglist.newpackingitem.NewPackingItemActivity;
import de.traveltogether.triplist.TripListActivity;

public class PackingDetailActivity extends AppCompatActivity implements DialogInterface.OnClickListener{

    long featureId =-1;
    long tripId =-1;
    TextView title;
    TextView description;
    TextView count;
    IPackingDetailPresenter presenter;
    ProgressDialog progressDialog;
    PackingDetailFragment fragment;
    PackingObject packingObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        presenter = new PackingDetailPresenter(this);
        setContentView(R.layout.activity_packing_detail);
        featureId = getIntent().getLongExtra("featureId", -1);
        tripId = getIntent().getLongExtra("tripId", -1);
        if(tripId!=-1){
            StaticTripData.setCurrentTripId(tripId);
        }

        title = (TextView) findViewById(R.id.activity_packing_detail_title);
        description = (TextView) findViewById(R.id.activity_packing_detail_description);
        count = (TextView)findViewById(R.id.activity_packing_detail_count);
        //amount = (TextView) findViewById(R.id.activity_expense_detail_amount);
        //paidBy = (TextView)findViewById(R.id.activity_expense_detail_paidby);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.detach(fragment);
        fragmentTransaction.commit();
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart(){
        super.onStart();
        progressDialog = ProgressDialog.show(this, "",
                "Bitte warten...", true);
        if(featureId!=-1){
            presenter.onGetDetailsForPackingObject(featureId);
        }
    }

    public void onViewError(String message){
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

    public void onViewDetails(PackingObject _packingobject){
        packingObject = _packingobject;
        tripId=packingObject.getTripId();
        getSupportActionBar().setTitle(packingObject.getTitle());
        title.setText(packingObject.getTitle());
        count.setText(String.valueOf(packingObject.getPackingItemsNumber()));
        description.setText(packingObject.getDescription());
        onViewPackingItems(packingObject.getItems());
    }

    public void onViewPackingItems(List<PackingItem> _packingItemList){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = PackingDetailFragment.newInstance(_packingItemList);
        fragmentTransaction.add(R.id.activity_packing_detail_list_container, fragment);
        fragmentTransaction.commit();

        progressDialog.cancel();
    }

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
                if(packingObject.getAuthor() == StaticData.getUserId()){
                    presenter.onDeletePackingObject(packingObject.getId());
                }
                else{
                    onViewError("Nur der Ersteller dieses Packelements darf das Packelement löschen.");
                }
                break;
            case R.id.edit:
                Intent intent = new Intent(this, NewPackingItemActivity.class);
                Bundle b = new Bundle();
                b.putLong("featureId", featureId);
                b.putLong("tripId", tripId);
                intent.putExtras(b);
                startActivity(intent);
                finish();
                break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return  true;
    }

    public void onSuccessDelete(){
        //TODO: toast
        if(tripId!=-1) {
            Intent intent = new Intent(this, PackingListActivity.class);
            intent.putExtra("tripId", tripId);
            startActivity(intent);
        }
        finish();
    }

    public void onCloseActivity(){
        progressDialog.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Auf dieses Packelement kann nicht mehr zugegriffen werden.");
        builder.setTitle(getString(R.string.error));
        builder.setNegativeButton(getString(R.string.ok), this);

        AlertDialog dialog = builder.create();
        dialog.show();
        //finish();
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();

        finish();
    }
}
