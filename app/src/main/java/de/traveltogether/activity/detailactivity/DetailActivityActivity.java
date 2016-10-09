package de.traveltogether.activity.detailactivity;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
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
import android.widget.TextView;

import java.util.List;

import de.traveltogether.R;
//import de.traveltogether.activity.newactivity.NewActivityActivity;
import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.activity.ActivityFragment;
import de.traveltogether.activity.ActivitiesActivity;
import de.traveltogether.activity.detailactivity.DetailActivityFragment;
import de.traveltogether.activity.detailactivity.DetailActivityPresenter;
//import de.traveltogether.expense.detailexpense.IExpenseDetailPresenter;
import de.traveltogether.activity.newactivity.NewActivityActivity;
import de.traveltogether.mainmenu.MainActivity;
import de.traveltogether.model.Expense;
import de.traveltogether.model.Payer;
import de.traveltogether.model.Trip;
import de.traveltogether.model.Activity;
import de.traveltogether.settings.SettingsActivity;
//import de.traveltogether.triplist.newtrip.NewTripActivity;

public class DetailActivityActivity extends AppCompatActivity  {
    long featureId =-1;
    long tripId =-1;
    IDetailActivityPresenter presenter;
    TextView title;
    TextView description;
    TextView date;
    TextView time;
    TextView place;
    ProgressDialog progressDialog;


    Activity detailActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DetailActivityPresenter(this);
        setContentView(R.layout.activity_detail_activity);
        featureId = getIntent().getLongExtra("featureId", -1);
        tripId = getIntent().getLongExtra("tripId", -1);
        if(tripId!=-1){
            StaticTripData.setCurrentTripId(tripId);
        }
        if(featureId!=-1){
            presenter.onGetDetailsForActivity(featureId);
        }
        title = (TextView) findViewById(R.id.detailActivity_title);
        description = (TextView) findViewById(R.id.detailActivity_description);
        date = (TextView) findViewById(R.id.detailActivity_date);
        time = (TextView)findViewById(R.id.detailActivity_time);
        place = (TextView)findViewById(R.id.detailActivity_place);
        progressDialog = ProgressDialog.show(this, "",
                "Bitte warten...", true);
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

    public void onViewDetails(Activity _activity){
        progressDialog.cancel();
        detailActivity = _activity;
        getSupportActionBar().setTitle(detailActivity.getTitle());
        title.setText(detailActivity.getTitle());
        description.setText(detailActivity.getDescription());
        date.setText(detailActivity.getDate());
        time.setText(detailActivity.getTime());
        place.setText(detailActivity.getDestination());

       // onViewPayers(detailActivity.getAssignedPayers());

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
                if(detailActivity.getAuthor() == StaticData.getUserId()){
                    presenter.onDeleteActivity(featureId);
                }
                else{
                    onViewError("Nur der Ersteller dieser Ausgabe darf die Ausgabe löschen.");
                }
                break;
            case R.id.edit:
                Intent intent = new Intent(this, NewActivityActivity.class);
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

    /*
    public void onViewPayers(List<Payer> payerList){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ExpenseDetailFragment fragment = ExpenseDetailFragment.newInstance(payerList);
        fragmentTransaction.add(R.id.activity_expense_detail_list_container, fragment);
        fragmentTransaction.commit();

    }
*/

    /*
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
                //TODO: DELETE
                break;
            case R.id.edit:
                Intent intent = new Intent(this, NewActivityActivity.class);
                intent.putExtra("tripId", tripId);
                startActivity(intent);
                break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return  true;
    }
*/

    public void onSuccessDelete(){
        //TODO: toast
        if(tripId!=-1) {
            Intent intent = new Intent(this, ActivitiesActivity.class);
            intent.putExtra("tripId", tripId);
            startActivity(intent);
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivitiesActivity.class);
        intent.putExtra("tripId", tripId);
        startActivity(intent);
        finish();
    }


}
