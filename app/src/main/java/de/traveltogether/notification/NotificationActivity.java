package de.traveltogether.notification;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import de.traveltogether.DataType;
import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.activity.detailactivity.ActivityDetailActivity;
import de.traveltogether.chat.ChatActivity;
import de.traveltogether.expense.detailexpense.ExpenseDetailActivity;
import de.traveltogether.info.InfoActivity;
import de.traveltogether.login.LoginActivity;
import de.traveltogether.model.Notification;
import de.traveltogether.packinglist.packingdetail.PackingDetailActivity;
import de.traveltogether.tasks.detail.TaskDetailActivity;

/**
 * Activity for viewing notification list
 */
public class NotificationActivity extends AppCompatActivity {

    INotificationPresenter presenter;
    private Notification[] notifications;
    private ProgressDialog progressDialog;
    private NotificationFragment fragment;
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
        if(StaticData.getUserId()<=0){
            SharedPreferences sharedPref = getSharedPreferences("TravelTogetherPrefs", Context.MODE_PRIVATE );
            int userId;
            userId = sharedPref.getInt(getString(R.string.saved_user_id), -1);
            if(userId!=-1){
                StaticData.setUserId(userId);
            }
            else{
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
        else {
            presenter.onGetNotificationList();
            progressDialog = ProgressDialog.show(this, "",
                    getString(R.string.loading_notifications), true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(getString(R.string.notifications));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        if(n == null){
            return;
        }
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
            Intent intent = new Intent(this, ActivityDetailActivity.class);
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
