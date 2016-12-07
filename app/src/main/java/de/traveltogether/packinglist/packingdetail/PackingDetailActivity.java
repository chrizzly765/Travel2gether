package de.traveltogether.packinglist.packingdetail;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.List;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.dialog.DeleteActivity;
import de.traveltogether.model.PackingItem;
import de.traveltogether.model.PackingObject;
import de.traveltogether.packinglist.PackingListActivity;
import de.traveltogether.packinglist.newpackingitem.NewPackingItemActivity;
import de.traveltogether.triplist.TripListActivity;

public class PackingDetailActivity extends DeleteActivity implements DialogInterface.OnClickListener{

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

        getSupportActionBar().setHomeButtonEnabled(true);

        presenter = new PackingDetailPresenter(this);
        setContentView(R.layout.activity_packing_detail);
        featureId = getIntent().getLongExtra("featureId", -1);
        tripId = getIntent().getLongExtra("tripId", -1);
        if(tripId!=-1){
            StaticTripData.setCurrentTripId(tripId);
        }
        if(StaticTripData.getActiveParticipants().length == 0){
            presenter.onGetParticipantsForTrip(tripId);
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

    public void onViewError(String message, String title){
        progressDialog.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onViewDetails(PackingObject _packingobject){
        packingObject = _packingobject;
        tripId=packingObject.getTripId();
        getSupportActionBar().setTitle(StringEscapeUtils.unescapeJava(packingObject.getTitle()));
        title.setText(StringEscapeUtils.unescapeJava(packingObject.getTitle()));
        count.setText(String.valueOf(packingObject.getPackingItemsNumber()));
        description.setText(StringEscapeUtils.unescapeJava(packingObject.getDescription()));
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
                createDeleteDialog();
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
            case android.R.id.home:
                finish();
                return true;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return  true;
    }

    public void onSuccessDelete(){
        progressDialog.cancel();
        Context context = getApplicationContext();
        CharSequence text = "Packelement erfolgreich gelöscht.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        finish();
    }

    public void onCloseActivity(){
        progressDialog.cancel();
        Context context = getApplicationContext();
        CharSequence text = "Dieses Packelement wurde gelöscht.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        finish();
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();

        finish();
    }

    public void delete(){
        if(packingObject.getAuthor() == StaticData.getUserId()){
            presenter.onDeletePackingObject(packingObject.getId());
        }
        else{
            onViewError("Nur der Ersteller dieses Packelements darf das Packelement löschen.", "Sorry.");
        }
    }

    public void setPackingItemClicked(int pos){
        Log.d("packingdetail", "setItemclicked");
        PackingItem item = packingObject.getItems().get(pos);
        item.toggleStatus();
        presenter.onUpdatePackingItem(packingObject.getItems().get(pos));
    }
}
