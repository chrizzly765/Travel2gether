package de.traveltogether.activity.detailactivity;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringEscapeUtils;

import de.traveltogether.dialog.DeleteActivity;
import de.traveltogether.time.TimeFormat;
import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.activity.newactivity.NewActivityActivity;
import de.traveltogether.model.Activity;

/**
 * Activity viewing details for an activity
 */
public class ActivityDetailActivity extends DeleteActivity {
    private long featureId =-1;
    private long tripId =-1;
    private IActivityDetailPresenter presenter;
    private TextView title;
    private TextView description;
    private TextView date;
    private TextView time;
    private TextView place;
    private ProgressDialog progressDialog;

    Activity detailActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);

        getSupportActionBar().setTitle(getString(R.string.activity));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        presenter = new ActivityDetailPresenter(this);
        featureId = getIntent().getLongExtra("featureId", -1);
        tripId = getIntent().getLongExtra("tripId", -1);
        if(tripId>0){
            StaticTripData.setCurrentTripId(tripId);
        }
        if(StaticTripData.getActiveParticipants().length == 0){
            presenter.onGetParticipantsForTrip(tripId);
        }
        title = (TextView) findViewById(R.id.detailActivity_title);
        description = (TextView) findViewById(R.id.detailActivity_description);
        date = (TextView) findViewById(R.id.detailActivity_date);
        time = (TextView)findViewById(R.id.detailActivity_time);
        place = (TextView)findViewById(R.id.detailActivity_place);

    }

    @Override
    protected void onStart(){
        super.onStart();
        progressDialog = ProgressDialog.show(this, "",
                getString(R.string.please_wait), true);
        if(featureId!=-1){
            presenter.onGetDetailsForActivity(featureId);
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
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        tripId=detailActivity.getTripId();
    }

    public void onViewDetails(Activity _activity){
        progressDialog.cancel();
        detailActivity = _activity;
        tripId=_activity.getTripId();
        getSupportActionBar().setTitle(StringEscapeUtils.unescapeJava(detailActivity.getTitle()));
        title.setText(StringEscapeUtils.unescapeJava(detailActivity.getTitle()));
        description.setText(StringEscapeUtils.unescapeJava(detailActivity.getDescription()));
        date.setText(detailActivity.getDate());
        time.setText(TimeFormat.getInstance().getTimeWithoutSecondsWithWord(detailActivity.getTime()));
        place.setText(StringEscapeUtils.unescapeJava(detailActivity.getDestination()));
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
            case android.R.id.home:
                finish();
                return true;
            case R.id.delete:
                createDeleteDialog();
                break;
            case R.id.edit:
                Intent intent = new Intent(this,NewActivityActivity.class);
                Bundle b = new Bundle();
                b.putLong("featureId", featureId);
                b.putLong("tripId", tripId);
                intent.putExtras(b);
                startActivity(intent);
                break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return  true;
    }

    public void onSuccessDelete(){
        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.deleted_activity_success), Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }

    public void onCloseActivity(){
        progressDialog.cancel();
        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.deleted_activity), Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }

    public void delete(){
        if(detailActivity.getAuthor() == StaticData.getUserId()){
            presenter.onDeleteActivity(featureId);
        }
        else{
            onViewError(getString(R.string.deleting_not_allowed, getString(R.string.activity)), getString(R.string.sorry));
        }
    }
}
