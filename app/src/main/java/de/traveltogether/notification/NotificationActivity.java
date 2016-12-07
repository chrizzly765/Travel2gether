package de.traveltogether.notification;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.activity.detailactivity.DetailActivityActivity;
import de.traveltogether.chat.ChatActivity;
import de.traveltogether.expense.detailexpense.ExpenseDetailActivity;
import de.traveltogether.info.InfoActivity;
import de.traveltogether.model.Notification;
import de.traveltogether.model.Response;
import de.traveltogether.packinglist.packingdetail.PackingDetailActivity;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.tasks.detail.TaskDetailActivity;
import de.traveltogether.triplist.TripListActivity;

public class NotificationActivity extends AppCompatActivity {

    INotificationPresenter presenter;
    Notification[] notifications;
    ProgressDialog progressDialog;
    NotificationFragment fragment;
    public Notification currentNotification;

    @Override
    protected void onSaveInstanceState(Bundle outState){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(fragment != null)
            fragmentTransaction.detach(fragment);
        fragmentTransaction.commit();
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart(){
        super.onStart();
        progressDialog = ProgressDialog.show(this, "",
                "Benachrichtigungen werden geladen...", true);
        presenter.onGetNotificationList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Benachrichtigungen");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.mipmap.logo_ohne_schrift);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        presenter= new NotificationPresenter(this);
        setContentView(R.layout.activity_notification);

    }

    void onViewNotificationList(Notification[] _notifications){
        this.notifications = _notifications;

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = NotificationFragment.newInstance(_notifications, this);
        fragmentTransaction.add(R.id.activity_notification_fragment_container, fragment);
        fragmentTransaction.commit();
        progressDialog.cancel();
    }

    void onViewErrorMessage(String message){
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

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                super.onOptionsItemSelected(item);
        }
        return  true;
    }

    public void onSuccessSetNotificationRead(){
        Notification n = currentNotification;
        if (n.getType().equals(DataType.TRIP.toString())){
            Intent intent = new Intent(this, InfoActivity.class);
            intent.putExtra("tripId", n.getFeatureOrTripId());
            startActivity(intent);
        }
        else if(n.getType().equals(DataType.EXPENSE.toString())){
            Intent intent = new Intent(this, ExpenseDetailActivity.class);
            intent.putExtra("featureId", n.getFeatureOrTripId());
            startActivity(intent);
        }
        else if (n.getType().equals(DataType.ACTIVITY.toString())){
            Intent intent = new Intent(this, DetailActivityActivity.class);
            intent.putExtra("featureId", n.getFeatureOrTripId());
            startActivity(intent);
        }
        else if(n.getType().equals(DataType.TASK.toString())){
            Intent intent = new Intent(this, TaskDetailActivity.class);
            intent.putExtra("featureId", n.getFeatureOrTripId());
            startActivity(intent);
        }
        else if(n.getType().equals(DataType.CHAT.toString())){
            StaticTripData.setCurrentTripId(n.getFeatureOrTripId());
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("tripId", n.getFeatureOrTripId());
            startActivity(intent);
        }
        else if(n.getType().equals(DataType.PACKINGOBJECT.toString())){
            Intent intent = new Intent(this, PackingDetailActivity.class);
            intent.putExtra("featureId", n.getFeatureOrTripId());
            startActivity(intent);
        }

    }

}
